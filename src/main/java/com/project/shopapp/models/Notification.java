package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private Integer nid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "messagedate", nullable = false)
    private LocalDateTime messagedate; // Thời gian báo cáo

    @Column(name = "messagecontent", nullable = false)
    private String messagecontent; // Nội dung báo cáo

    // Constructor đã sửa
    public Notification(String message, User user) {
        this.messagecontent = message;
        this.user = user;
        this.messagedate = LocalDateTime.now(); // Gán thời gian hiện tại cho messagedate
    }
}
