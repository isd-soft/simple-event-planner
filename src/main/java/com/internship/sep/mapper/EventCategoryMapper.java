package com.internship.sep.mapper;

import com.internship.sep.models.EventCategory;
import com.internship.sep.web.EventCategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class EventCategoryMapper implements Mapper<EventCategory, EventCategoryDTO> {
    @Override
    public EventCategoryDTO map(EventCategory entity) {
        EventCategoryDTO dto = new EventCategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    @Override
    public  EventCategory unmap(EventCategoryDTO eventCategoryDTO) {
        EventCategory eventCategory = new EventCategory();
        eventCategory.setId(eventCategoryDTO.getId());
        eventCategory.setName(eventCategoryDTO.getName());

        return eventCategory;
    }
}
