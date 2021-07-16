package com.internship.sep.web;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    private Long id;
    private MultipartFile multipartFile;

}
