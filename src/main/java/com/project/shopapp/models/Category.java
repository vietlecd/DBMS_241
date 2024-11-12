package com.project.shopapp.models;


import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateID")
    private Long cateID;

    @Column(name = "namecategory")
    private String namecategory;



    @Column(name = "catedescription")
    private String catedescription;

    @ManyToMany(mappedBy = "categories")
    private Set<Book> books;

    public String getCatedescription() {
        return catedescription;
    }

    public void setCatedescription(String catedescription) {
        this.catedescription = catedescription;
    }

    // Getters và setters
    public Long getCateID() {
        return cateID;
    }

    public void setCateID(Long cateID) {
        this.cateID = cateID;
    }



    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getNamecategory() {
        return namecategory;
    }

    public void setNamecategory(String namecategory) {
        this.namecategory = namecategory;
    }

}