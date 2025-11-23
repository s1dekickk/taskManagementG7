package com.taskmanagement.service;

import com.taskmanagement.dtos.tag.CreateTagRequest;
import com.taskmanagement.dtos.tag.TagDTO;
import com.taskmanagement.dtos.tag.UpdateTagRequest;
import com.taskmanagement.entity.task.Tag;
import com.taskmanagement.mapper.TagMapper;
import com.taskmanagement.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    public TagDTO createTag(CreateTagRequest request) {
        Tag tag = tagMapper.toEntity(request);
        try {
            Tag savedTag = tagRepository.save(tag);
            return tagMapper.toDto(savedTag);
        } catch (DataIntegrityViolationException e) {
            // catches unique constraint violation on the 'name' column
            throw new IllegalArgumentException("Tag name '" + request.getName() + "' already exists.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tagMapper.toDtoList(tags);
    }

    @Transactional(readOnly = true)
    public TagDTO getTagById(Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with ID: " + tagId));
        return tagMapper.toDto(tag);
    }

    @Transactional
    public TagDTO updateTag(Integer tagId, UpdateTagRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with ID: " + tagId));

        tagMapper.update(request, tag);

        try {
            Tag updatedTag = tagRepository.save(tag);
            return tagMapper.toDto(updatedTag);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Tag name '" + request.getName() + "' already exists.", e);
        }
    }

    @Transactional
    public void deleteTag(Integer tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("Tag not found with ID: " + tagId);
        }
        tagRepository.deleteById(tagId);
    }
}
