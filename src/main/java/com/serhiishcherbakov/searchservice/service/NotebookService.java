package com.serhiishcherbakov.searchservice.service;

import com.serhiishcherbakov.searchservice.model.Notebook;
import com.serhiishcherbakov.searchservice.model.SearchNotebooksResult;
import com.serhiishcherbakov.searchservice.model.Tag;
import com.serhiishcherbakov.searchservice.model.request.SearchFilterRequest;
import com.serhiishcherbakov.searchservice.repository.CustomNotebookRepository;
import com.serhiishcherbakov.searchservice.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotebookService {
    private final CustomNotebookRepository customNotebookRepository;

    public SearchNotebooksResult searchNotebooks(SearchFilterRequest filter, UserDetails userDetails) {
        return customNotebookRepository.searchNotebooks(filter, userDetails);
    }

    public void saveNotebook(Notebook notebook) {
        customNotebookRepository.saveNotebook(notebook);
    }

    public void deleteNotebook(Notebook notebook) {
        customNotebookRepository.deleteNotebook(notebook);
    }

    public void saveTag(Tag tag) {
        customNotebookRepository.saveTag(tag);
    }

    public void deleteTag(Tag tag) {
        customNotebookRepository.deleteTag(tag);
    }
}
