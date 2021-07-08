package com.internship.sep.web.controllers;

import com.internship.sep.services.EventCategoryService;
import com.internship.sep.web.EventCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class EventCategoryController {
    private final EventCategoryService eventCategoryService;

    @GetMapping
    public List<String> getCategories() {
        return eventCategoryService.getCategories();
    }

    @PostMapping
    public void addCategory(@RequestBody EventCategoryDTO dto) {
        eventCategoryService.addCategory(dto);
    }

    @PutMapping(path = "/{categoryId}")
    public void updateCategory(@PathVariable("categoryId") Long categoryId,
                               @RequestParam EventCategoryDTO dto) {

        dto.setId(categoryId);
        eventCategoryService.updateCategory(dto);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        eventCategoryService.deleteCategory(categoryId);
    }
}
