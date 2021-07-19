package com.internship.sep.models;
import javax.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Getter
@Setter
@Entity

@Table(name = "files")
public class FileDB extends AbstractEntity {
//
//    @Column(name="name")
//    private String name;
//
//    @Column(name="type")
//    private String type;

    @Lob
    private byte[] content;
//
//    public FileDB(MultipartFile file) {
//
//
//         this.file = file;
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FileDB{" +
                "file=" + content +
                '}';
    }
}
