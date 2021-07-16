package com.internship.sep.repositories;
import com.internship.sep.models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileDBRepository extends JpaRepository<FileDB, Long> {

}