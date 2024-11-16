package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chapter")
public class Chapter {

    @Id
    @Column(name = "cId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cTitle")
    private String title;

    @Column(name = "cContent")
    private String content;

    @Column(name = "cFee")
    private Integer fee;

}
