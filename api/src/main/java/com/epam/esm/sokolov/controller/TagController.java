package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.service.tag.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@Api(value = "TagControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Gets the tag with specific id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Tag.class)})
    public Tag findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @PostMapping
    public void createTag(@RequestBody Tag tag) {
        tagService.create(tag);
    }

    @DeleteMapping("/delete")
    public void deleteTag(@RequestBody Tag tag) {
        tagService.delete(tag);
    }

    @PutMapping("/update")
    public void updateTag(@RequestBody Tag tag) {
        tagService.update(tag);
    }
}
