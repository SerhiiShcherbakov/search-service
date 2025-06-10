package com.serhiishcherbakov.searchservice.controller;

import com.serhiishcherbakov.searchservice.model.request.SearchFilterRequest;
import com.serhiishcherbakov.searchservice.model.response.NotebooksResponse;
import com.serhiishcherbakov.searchservice.model.response.Paginator;
import com.serhiishcherbakov.searchservice.security.UserDetails;
import com.serhiishcherbakov.searchservice.service.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import static com.serhiishcherbakov.searchservice.security.UserInterceptor.USER_DETAILS_ATTRIBUTE;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final NotebookService notebookService;

    @GetMapping("/notebooks")
    public NotebooksResponse searchNotebooks(SearchFilterRequest filter,
                                             @RequestAttribute(USER_DETAILS_ATTRIBUTE) UserDetails user) {
        var searchResult = notebookService.searchNotebooks(filter, user);
        return new NotebooksResponse(searchResult.getNotebooks(),
                new Paginator(searchResult.getPage(), searchResult.getSize(), searchResult.getTotal()));
    }
}
