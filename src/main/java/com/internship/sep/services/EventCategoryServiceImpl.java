package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.EventCategory;
import com.internship.sep.repositories.EventCategoryRepository;
import com.internship.sep.web.EventCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventCategoryServiceImpl implements EventCategoryService {
    private final EventCategoryRepository repository;
    private final Mapper<EventCategory, EventCategoryDTO> mapper;

    @Override
    @Transactional(readOnly = true)
    public List<EventCategoryDTO> getCategories() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCategory(EventCategoryDTO dto) {
        EventCategory eventCategory = new EventCategory();
        eventCategory.setName(dto.getName());

        repository.save(eventCategory);
    }

    @Override
    @Transactional
    public void updateCategory(EventCategoryDTO dto) {
        EventCategory eventCategory = repository.getById(dto.getId());
        eventCategory.setName(dto.getName());
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
