package com.serhiishcherbakov.searchservice.repository;

import com.serhiishcherbakov.searchservice.model.Notebook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NotebookRepository extends ElasticsearchRepository<Notebook, Long> {
}
