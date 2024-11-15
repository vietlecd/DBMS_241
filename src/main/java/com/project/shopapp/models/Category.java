package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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





}