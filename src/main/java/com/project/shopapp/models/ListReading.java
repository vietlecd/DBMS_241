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
@Builder
public class ListReading extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "include",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    Set<Book> bookSet;

    public void addBook(Book book) {
        this.getBookSet().add(book);
        book.getListReadings().add(this);
    }

    public boolean removeBook(Book book) {
        this.getBookSet().remove(book);
        book.getListReadings().remove(this);
        return true;
    }
}
