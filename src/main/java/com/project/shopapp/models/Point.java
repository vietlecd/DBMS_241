package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "point")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Point extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "type")
    private String type;

    public void addPoint(int point) {
        if (this.amount == null) {
            amount = 0; 
        }
        this.setAmount(this.amount + point);
    }

}
