package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID")
    private Integer bookID;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "uploader")
    private User uploader;

    @Column(name = "pdf")
    private String pdf;

    @Column(name = "description")
    private String description;

    @Column(name = "coverimage")
    private String coverimage;

    @Column(name = "price")
    private String price;

    @Column(name = "publishyear")
    private Integer publishyear;
    @Column(name = "status")
    private String status;



    @ManyToMany
    @JoinTable(
            name = "cate", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "bookID", referencedColumnName = "bookID"), // Tham chiếu đến bookID của BookEntity
            inverseJoinColumns = @JoinColumn(name = "cateID", referencedColumnName = "cateID") // Tham chiếu đến cateID của CategoryEntity
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> comments = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Column(name = "totalpage")
    private Integer totalpage;

    @ManyToMany(mappedBy = "bookSet")
    private List<ListReading> listReadings;

    @ManyToMany(mappedBy = "bookSet")
    Set<Author> authorList;

}