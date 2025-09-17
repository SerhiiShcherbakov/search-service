package com.serhiishcherbakov.searchservice.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.serhiishcherbakov.searchservice.model.Notebook;
import com.serhiishcherbakov.searchservice.model.SearchNotebooksResult;
import com.serhiishcherbakov.searchservice.model.Tag;
import com.serhiishcherbakov.searchservice.model.request.SearchFilterRequest;
import com.serhiishcherbakov.searchservice.security.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ScriptType;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomNotebookRepository {
    private static final String INDEX_NAME = "notebooks";

    private final ElasticsearchOperations elasticsearchOperations;
    private final NotebookRepository notebookRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneOffset.UTC);

    public SearchNotebooksResult searchNotebooks(SearchFilterRequest filter, UserDetails user) {
        BoolQuery.Builder queryBuilder = QueryBuilders.bool();

        queryBuilder.filter(QueryBuilders.term(q -> q.field("userId").value(user.getId())));

        if (filter.hasQuery()) {
            queryBuilder.must(QueryBuilders.multiMatch(q -> q.fields("title", "body").query(filter.getQuery())));
        }

        if (filter.hasTags()) {
            filter.getTagIds().forEach(tagId -> queryBuilder.must(
                    QueryBuilders.nested(q -> q
                            .path("tags")
                            .query(n -> n.term(t -> t.field("tags.id").value(tagId))))));
        }

        if (filter.getDeleted() != null) {
            if (filter.getDeleted()) {
                queryBuilder.must(QueryBuilders.exists(q -> q.field("deletedAt")));
            } else {
                queryBuilder.mustNot(QueryBuilders.exists(q -> q.field("deletedAt")));
            }
        }

        var sort = Sort.by(filter.getOrder().getField());
        sort = filter.getOrder().isAscending() ? sort.ascending() : sort.descending();

        var pageable = PageRequest.of(filter.getPage(), filter.getSize());

        var query = NativeQuery.builder()
                .withQuery(queryBuilder.build()._toQuery())
                .withSort(sort)
                .withPageable(pageable)
                .build();

        var searchResponse = elasticsearchOperations.search(query, Notebook.class);

        return SearchNotebooksResult.builder()
                .notebooks(searchResponse.stream().map(SearchHit::getContent).toList())
                .page(filter.getPage())
                .size(filter.getSize())
                .total(searchResponse.getTotalHits())
                .build();
    }

    public void saveNotebook(Notebook notebook) {
        notebookRepository.save(notebook);
    }

    public void deleteNotebook(Notebook notebook) {
        notebookRepository.delete(notebook);
    }

    public void saveTag(Tag tag) {
        var script = """
                for (int i = 0; i < ctx._source.tags.length; i++) {
                    if (ctx._source.tags[i].id == params.tagId) {
                        ctx._source.tags[i].title = params.title;
                        ctx._source.tags[i].color = params.color;
                        ctx._source.tags[i].createdAt = params.createdAt;
                        ctx._source.tags[i].updatedAt = params.updatedAt;
                        break;
                    }
                }
                """;

        Map<String, Object> params = Map.of(
                "tagId", tag.getId(),
                "title", tag.getTitle(),
                "color", tag.getColor(),
                "createdAt", formatter.format(tag.getCreatedAt()),
                "updatedAt", formatter.format(tag.getUpdatedAt())
        );

        elasticsearchOperations.updateByQuery(createTagUpdateQuery(tag, script, params), IndexCoordinates.of(INDEX_NAME));
    }

    public void deleteTag(Tag tag) {
        var script = """
                ctx._source.tags.removeIf(tag -> tag.id == params.tagId);
                """;

        Map<String, Object> params = Map.of("tagId", tag.getId());

        elasticsearchOperations.updateByQuery(createTagUpdateQuery(tag, script, params), IndexCoordinates.of(INDEX_NAME));
    }

    private UpdateQuery createTagUpdateQuery(Tag tag, String script, Map<String, Object> params) {
        return UpdateQuery.builder(queryForTagUpdate(tag))
                .withScript(script)
                .withScriptType(ScriptType.INLINE)
                .withParams(params)
                .build();
    }

    private NativeQuery queryForTagUpdate(Tag tag) {
        return new NativeQuery(new NativeQueryBuilder()
                .withQuery(QueryBuilders.nested(q -> q
                        .path("tags")
                        .query(n -> n.term(t -> t.field("tags.id").value(tag.getId()))))));
    }
}
