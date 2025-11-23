package com.taskmanagement.dtos.tag;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
public class TagDTO {
    private Integer tagId;
    private String name;
    private String color;
    private LocalDateTime createdAt;
}
