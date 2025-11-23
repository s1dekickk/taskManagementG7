package com.taskmanagement.dtos.Category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateCategoryRequest {
    @NotBlank(message = "Category name is required.")
    @Size(max = 50, message = "Name must be under 50 characters.")
    private String name;
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Color must be a valid 6-digit hex code, starting with #.")
    @JsonProperty("color")
    private String color;
}
