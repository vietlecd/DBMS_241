package com.project.shopapp.repositories.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateID")
    private Long cateID;

    @Column(name = "name")
    private String name;

    @Column(name = "catedescription")
    private String catedescription;


    public String getCatedescription() {
        return catedescription;
    }

    public void setCatedescription(String catedescription) {
        this.catedescription = catedescription;
    }

    @ManyToMany(mappedBy = "categories")
    private Set<BookEntity> books;





    // Getters và setters
    public Long getCateID() {
        return cateID;
    }

    public void setCateID(Long cateID) {
        this.cateID = cateID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BookEntity> books) {
        this.books = books;
    }
}
