package com.restful.bookstore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Publishing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "publishing", fetch = FetchType.LAZY)
    private List<Book> books;

    public Publishing() {
    }

    public Publishing(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
