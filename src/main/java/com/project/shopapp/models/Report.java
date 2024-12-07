package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rpID")
    private Integer rpID; // ID tự động tăng của báo cáo

    @ManyToOne
    @JoinColumn(name = "bookID", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reportdate", nullable = false)
    private LocalDateTime reportDate; // Thời gian báo cáo

    @Column(name = "reportcontent", nullable = false)
    private String reportContent; // Nội dung báo cáo
}
