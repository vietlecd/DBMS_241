package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID")
    private Long bookID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "coverimage")
    private String coverimage;

    @Column(name = "price")
    private String price;

    @Column(name = "publishyear")
    private Integer publishyear;

    @ManyToMany
    @JoinTable(
            name = "cate", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "bookID", referencedColumnName = "bookID"), // Tham chiếu đến bookID của Book
            inverseJoinColumns = @JoinColumn(name = "cateID", referencedColumnName = "cateID") // Tham chiếu đến cateID của Category
    )
    private Set<Category> categories;

    @ManyToMany(mappedBy = "bookSet")
    Set<Author> authorList;

}