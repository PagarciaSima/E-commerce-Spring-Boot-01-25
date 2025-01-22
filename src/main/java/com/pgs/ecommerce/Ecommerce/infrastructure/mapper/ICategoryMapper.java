package com.pgs.ecommerce.Ecommerce.infrastructure.mapper;

import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.CategoryEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

// Instantiated by spring
@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "name", target = "name"),
                    @Mapping(source = "dateCreated", target = "dateCreated"),
                    @Mapping(source = "dateUpdated", target = "dateUpdated")
            }
    )
    Category toCategory (CategoryEntity categoryEntity);

    Iterable<Category> toCategoryList (Iterable<CategoryEntity> categoryEntities);

    @InheritInverseConfiguration
    CategoryEntity toCategoryEntity (Category category);
}
