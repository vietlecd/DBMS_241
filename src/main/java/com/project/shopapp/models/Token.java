package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"token"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, length = 255, unique = true)
    private String token;

    @Column(name = "token_type", nullable = false, length = 50)
    private String tokenType; // "ACCESS" or "REFRESH"

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linked_access_token", referencedColumnName = "id")
    private Token linkedAccessToken;

}
