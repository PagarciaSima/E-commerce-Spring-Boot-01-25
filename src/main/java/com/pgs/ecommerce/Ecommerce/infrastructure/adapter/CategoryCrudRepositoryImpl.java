package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import com.pgs.ecommerce.Ecommerce.domain.port.ICategoryRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.ICategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class CategoryCrudRepositoryImpl implements ICategoryRepository {

    private final ICategoryCrudRepository iCategoryCrudRepository;
    private final ICategoryMapper ICategoryMapper;

    @Override
    public Category save(Category category) {
        return this.ICategoryMapper.toCategory(iCategoryCrudRepository.save(ICategoryMapper.toCategoryEntity(category)));
    }

    @Override
    public Iterable<Category> findAll() {
        return this.ICategoryMapper.toCategoryList(iCategoryCrudRepository.findAll());
    }

    @Override
    public Category findById(Integer id) {
        return ICategoryMapper.toCategory(iCategoryCrudRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Category ID " + id + " not found")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        Category category = this.findById(id);
        if( null != category)
            iCategoryCrudRepository.deleteById(id);
    }
}
