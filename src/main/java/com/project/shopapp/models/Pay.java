package com.project.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pay")
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "point_id", referencedColumnName = "id")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookID")
    private Book book;

    @Column(name = "book_price")
    private Integer book_price;

    @Column(name = "payment_date")
    private Timestamp timestamp;
}