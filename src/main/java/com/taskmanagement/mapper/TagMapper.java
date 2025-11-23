package com.taskmanagement.mapper;

import com.taskmanagement.dtos.tag.CreateTagRequest;
import com.taskmanagement.dtos.tag.TagDTO;
import com.taskmanagement.dtos.tag.UpdateTagRequest;
import com.taskmanagement.entity.task.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagDTO toDto(Tag tag);
    List<TagDTO> toDtoList(List<Tag> tags);
    Tag toEntity(CreateTagRequest request);
    void update(UpdateTagRequest request, @MappingTarget Tag tag);
}
