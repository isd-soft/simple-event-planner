package com.internship.sep.mapper;

import com.internship.sep.models.FileDB;
import com.internship.sep.web.FileDTO;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class FileMapper implements Mapper<FileDB, FileDTO> {
    @Override
    public FileDTO map(FileDB entity) {
        FileDTO dto = new FileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());

        return dto;
    }

    @Override
    public FileDB unmap(FileDTO dto) {
        FileDB file = new FileDB();
        if (dto.getContent() != null) {
            file.setContent(dto.getContent().getBytes(StandardCharsets.UTF_8));
        }
        file.setId(dto.getId());
        file.setName(dto.getName());
        file.setType(dto.getType());
        return file;
    }
}
