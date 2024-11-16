package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "list_reading")
//@IdClass(ListId.class)
@Builder
public class ListReading extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToMany
    @JoinTable(
            name = "include",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    Set<Book> bookSet;
}
