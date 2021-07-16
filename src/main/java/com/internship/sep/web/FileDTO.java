package com.internship.sep.web;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private String name;
    private String type;
    private byte[] data;
}
