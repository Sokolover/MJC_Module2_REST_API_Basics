package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.service.tag.TagService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class MainController {

    private TagService tagService;

    public MainController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> greet() {

        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable String id) {

        return tagService.findById(Long.parseLong(id));
    }
}
