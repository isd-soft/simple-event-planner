package com.internship.sep.mapper;
import com.internship.sep.models.LinkDB;
import com.internship.sep.web.LinkDTO;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
class LinkMapper implements Mapper<LinkDB, LinkDTO> {

    @Override
    public LinkDTO map(LinkDB entity) {

        if (entity == null) {
            return null;
        }

        LinkDTO dto = new LinkDTO();
        dto.setId(entity.getId());
        dto.setLink(entity.getLink());

        return dto;
    }
    @Synchronized
    @Nullable
    @Override
    public LinkDB unmap(LinkDTO dto) {

        if (dto == null) {
            return null;
        }

        LinkDB entity = new LinkDB();
        entity.setId(dto.getId());
        entity.setLink(dto.getLink());
        return entity;
    }


}