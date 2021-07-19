package com.internship.sep.web.controllers;

import com.internship.sep.services.EventCategoryServiceImpl;
import com.internship.sep.web.EventCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addCategory(@RequestBody EventCategoryDTO dto) {
            eventCategoryService.addCategory(dto);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") Long categoryId,
                               @RequestBody EventCategoryDTO dto) {

        dto.setId(categoryId);
        eventCategoryService.updateCategory(dto);
        return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        eventCategoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
