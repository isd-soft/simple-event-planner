package com.internship.sep.services;

import com.internship.sep.web.EventCategoryDTO;

import java.util.List;

public interface EventCategoryService {
    List<String> getCategories();

    void addCategory(EventCategoryDTO dto);

    void updateCategory(EventCategoryDTO dto);

    void deleteCategory(Long id);
}
