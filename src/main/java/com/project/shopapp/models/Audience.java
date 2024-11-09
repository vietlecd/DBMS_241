package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audience")
public class Audience extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="friend1")
    private Integer friend1;

    @Column(name="friend2")
    private Integer  friend2;

    @Column(name = "status")
    private Integer status; // 0 for requested, 1 for accepted, -1 for rejected

}
