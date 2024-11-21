package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmark")
@Getter
@Setter
public class BookMark{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "markID")
    private Long markID; // ID tự động tăng

    @ManyToOne
    @JoinColumn(name = "bookID", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "createdate", nullable = false, updatable = false)
    private LocalDateTime createDate; // Thời gian tạo, mặc định là CURRENT_TIMESTAMP


    @Column(name = "pagenumber", nullable = false)
    private Long pageNumber; // Số trang được đánh dấu

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
