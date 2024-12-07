

package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewID")
    private Long reviewID;

    @Column(name = "rating")
    private Long rating;
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "bookID", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "evaluate")
    private String evaluate;



}
