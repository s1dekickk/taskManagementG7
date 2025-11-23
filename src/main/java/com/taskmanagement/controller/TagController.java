package com.taskmanagement.controller;

import com.taskmanagement.dtos.tag.CreateTagRequest;
import com.taskmanagement.dtos.tag.TagDTO;
import com.taskmanagement.dtos.tag.UpdateTagRequest;
import com.taskmanagement.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    // POST /tags
    @PostMapping
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody CreateTagRequest request) {
        try {
            TagDTO tag = tagService.createTag(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(tag);
        } catch (IllegalArgumentException e) {
            // Handles unique name conflict
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    // GET /tags
    @GetMapping
    public List<TagDTO> getAllTags() {
        return tagService.getAllTags();
    }
    // GET /tags/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Integer id) {
        try {
            TagDTO tag = tagService.getTagById(id);
            return ResponseEntity.ok(tag);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // PUT /tags/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Integer id, @Valid @RequestBody UpdateTagRequest request) {
        try {
            TagDTO updatedTag = tagService.updateTag(id, request);
            return ResponseEntity.ok(updatedTag);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            // Handles unique name conflict
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DELETE /tags/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        try {
            tagService.deleteTag(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}