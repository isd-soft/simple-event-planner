package com.internship.sep.mapper;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.FileDB;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.FileDTO;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper implements Mapper<FileDB, FileDTO>{
    @Override
    public FileDTO map(FileDB entity) {

        if (entity == null) {
            return null;
        }

        FileDTO dto = new FileDTO();

        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setType(entity.getType());
//        dto.setData(entity.getData());

        return dto;
    }

    @Synchronized
    @Nullable
    @Override
    public FileDB unmap(FileDTO dto) {

        if (dto == null) {
            return null;
        }

        FileDB entity = new FileDB();
        entity.setId(dto.getId());
//        entity.setName(dto.getName());
//        entity.setType(dto.getType());
//        entity.setData(dto.getData());


        return entity;
    }
}
