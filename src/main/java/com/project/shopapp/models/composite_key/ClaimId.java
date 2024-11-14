package com.project.shopapp.models.composite_key;

import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ClaimId implements Serializable {
    private User userId;
    private Point pointId;
}
