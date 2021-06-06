package com.max.springproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "user_status")
    private UserStatus userStatus;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Event> events;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userStatus=" + userStatus +
                ", first_name='" + first_name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", events=" + events +
                '}';
    }
}