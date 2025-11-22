package com.taskmanagement.dtos.task;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class AttachmentDTO {
    @JsonProperty("attachment_id")
    private Integer attachmentId;
    @JsonProperty("task_id")
    private Integer taskId;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("file_type")
    private String mimeType;
    @JsonProperty("file_size")
    private Long fileSize;
    @JsonProperty("uploaded_by_id")
    private Integer uploadedById;
    @JsonProperty("uploaded_at")
    private LocalDateTime uploadedAt;
    // This is used by the frontend to download the file
    @JsonProperty("file_url")
    private String fileUrl;
}
