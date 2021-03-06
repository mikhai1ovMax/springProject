package com.max.springproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "location")
    private String location;
    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
