package com.project.shopapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToMany
    @JoinTable(
            name = "write",
            joinColumns = @JoinColumn(name = "authorId"),
            inverseJoinColumns = @JoinColumn(name = "bookId")
    )
    Set<Book> bookSet;

    @Column(name = "bio")
    private String bio;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "status")
    private Integer status;

}
