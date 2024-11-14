package com.project.shopapp.models;

import com.project.shopapp.models.composite_key.ClaimId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "claim")
@IdClass(ClaimId.class)
public class Claim{
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point pointId;

    @Column(name = "claim_type")
    private String claimType;  //LOGIN_STREAK or AD_VIEWS or REVIEWS;

    @Column(name = "claim_date")
    private LocalDate claimDate;


}
