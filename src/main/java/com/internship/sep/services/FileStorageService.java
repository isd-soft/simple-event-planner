package com.internship.sep.services;
import com.internship.sep.models.FileDB;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Stream;


public interface FileStorageService {

   FileDB store(MultipartFile file) throws IOException;
   FileDB getFile(Long id);
   Stream<FileDB> getAllFiles();
}
