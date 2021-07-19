package com.internship.sep.web;

import lombok.Data;


@Data
public class FileDTO {
    private Long id;
    private String content;
    private String type;
    private String name;

}
