package com.taskmanagement.mapper;
import com.taskmanagement.dtos.Category.CategoryDTO;
import com.taskmanagement.dtos.Category.CreateCategoryRequest;
import com.taskmanagement.dtos.Category.UpdateCategoryRequest;
import com.taskmanagement.entity.task.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    List<CategoryDTO> toDtoList(List<Category> categories);
    Category toEntity(CreateCategoryRequest request);
    void update(UpdateCategoryRequest request, @MappingTarget Category category);
}
