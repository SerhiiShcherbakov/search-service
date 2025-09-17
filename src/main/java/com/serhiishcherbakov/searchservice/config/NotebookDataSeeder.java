package com.serhiishcherbakov.searchservice.config;

import com.serhiishcherbakov.searchservice.model.Notebook;
import com.serhiishcherbakov.searchservice.model.Tag;
import com.serhiishcherbakov.searchservice.repository.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Profile("local")
@RequiredArgsConstructor
public class NotebookDataSeeder implements CommandLineRunner {
    private final NotebookRepository notebookRepository;

    @Override
    public void run(String... args) {
        long notebooksCount = notebookRepository.count();
        if (notebooksCount != 0) {
            return;
        }

        notebookRepository.save(createFirstNotebook());
        notebookRepository.save(createSecondNotebook());
        notebookRepository.save(createThirdNotebook());
    }

    private Notebook createFirstNotebook() {
        return Notebook.builder()
                .id(1L)
                .title("Technology Database Systems")
                .body("""
                        javascript python sql database mongodb postgresql redis elasticsearch docker kubernetes
                        microservices api rest graphql json xml http https authentication authorization jwt oauth
                        token session cache memory storage disk ssd nvme raid backup recovery replication sharding
                        """)
                .createdAt(Instant.parse("2025-06-08T10:25:00.00Z"))
                .updatedAt(Instant.parse("2025-06-08T10:25:00.00Z"))
                .userId("1")
                .tags(List.of(createFirstTag()))
                .deletedAt(null)
                .build();
    }

    private Notebook createSecondNotebook() {
        return Notebook.builder()
                .id(2L)
                .title("Business Marketing Finance")
                .body("""
                        revenue profit margin cost expenses budget forecast quarterly annual growth marketing campaign
                        advertising social media facebook instagram twitter linkedin youtube google ads seo sem content
                        strategy brand awareness customer acquisition retention churn rate conversion funnel analytics
                        roi kpi metrics dashboard reporting crm sales pipeline leads prospects customers segments
                        demographics psychographics
                        """)
                .createdAt(Instant.parse("2025-06-08T11:25:00.00Z"))
                .updatedAt(Instant.parse("2025-06-09T10:25:00.00Z"))
                .userId("1")
                .tags(List.of(createFirstTag(), createSecondTag()))
                .deletedAt(null)
                .build();
    }

    private Notebook createThirdNotebook() {
        return Notebook.builder()
                .id(3L)
                .title("Project Management Operations")
                .body("""
                        agile scrum kanban sprint backlog user story epic feature requirement specification
                        documentation testing qa automation ci cd deployment infrastructure cloud aws azure gcp
                        devops terraform ansible chef puppet monitoring alerting incident response sla uptime
                        availability reliability security
                        """)
                .createdAt(Instant.parse("2025-06-08T13:25:00.00Z"))
                .updatedAt(Instant.parse("2025-06-10T10:25:00.00Z"))
                .userId("1")
                .tags(List.of())
                .deletedAt(Instant.parse("2025-06-10T10:25:00.00Z"))
                .build();
    }

    private Tag createFirstTag() {
        return Tag.builder()
                .id(1L)
                .title("tag-title-1")
                .color("#aabbcc")
                .createdAt(Instant.parse("2025-06-01T18:00:00.00Z"))
                .updatedAt(Instant.parse("2025-06-05T20:00:00.00Z"))
                .userId("1")
                .build();
    }

    private Tag createSecondTag() {
        return Tag.builder()
                .id(2L)
                .title("tag-title-2")
                .color("#ee1122")
                .createdAt(Instant.parse("2025-06-07T14:25:00.00Z"))
                .updatedAt(Instant.parse("2025-06-07T14:25:00.00Z"))
                .userId("1")
                .build();
    }
}
