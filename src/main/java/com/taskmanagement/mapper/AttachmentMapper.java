package com.taskmanagement.mapper;
import com.taskmanagement.dtos.task.AttachmentDTO;
import com.taskmanagement.entity.task.TaskAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    default String createDownloadUrl(Integer attachmentId) {
        return "/tasks/attachments/" + attachmentId + "/download";
    }
    @Mapping(target = "taskId", source = "task.taskId")
    @Mapping(target = "uploadedById", source = "uploadedBy.userId")
    @Mapping(target = "fileUrl", expression = "java(createDownloadUrl(attachment.getAttachmentId()))")
    AttachmentDTO toDto(TaskAttachment attachment);
}
