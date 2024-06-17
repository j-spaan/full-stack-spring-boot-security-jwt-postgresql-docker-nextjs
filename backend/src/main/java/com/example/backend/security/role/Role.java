package com.example.backend.security.role;

import com.example.backend.config.AppConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppConstants.Table.ROLES)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // TODO add uniqueness constraint
    private String name;
}
