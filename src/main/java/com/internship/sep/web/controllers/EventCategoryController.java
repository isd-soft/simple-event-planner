package com.internship.sep.web.controllers;

import com.internship.sep.services.EventCategoryService;
import com.internship.sep.services.EventCategoryServiceImpl;
import com.internship.sep.web.EventCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class EventCategoryController {
    private final EventCategoryServiceImpl eventCategoryService;

    @GetMapping
    public List<EventCategoryDTO> getCategories() {
        return eventCategoryService.getCategories();
    }

    @PostMapping
    public void addCategory(@RequestBody EventCategoryDTO dto) {
        try {
            eventCategoryService.addCategory(dto);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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
