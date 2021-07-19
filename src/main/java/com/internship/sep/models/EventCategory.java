package com.internship.sep.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "event_categories", uniqueConstraints = {
        @UniqueConstraint(name = "event_category_name_must_be_unique", columnNames = "name")
})
public class EventCategory extends AbstractEntity {

    @Column(name = "name")
    @NotBlank(message = "category name must not be null")
    private String name;

    @OneToMany(mappedBy = "eventCategory")
    private List<Event> events = new ArrayList<>();
}
