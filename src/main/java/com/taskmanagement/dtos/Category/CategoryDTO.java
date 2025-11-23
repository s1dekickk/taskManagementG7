package com.taskmanagement.dtos.Category;


import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDTO {
    private Integer categoryId;
    private String name;
    private String color;
    private LocalDateTime createdAt;
}

