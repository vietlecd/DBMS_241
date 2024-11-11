//package com.project.shopapp.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "book")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Book {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "title", nullable = false, length = 350)
//    private String title;
//
//    private Float price;
//
//    @Column(name = "thumbnail", length = 300)
//    private String thumbnail;
//
//    @Column(name = "description")
//    private String description;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
//}
