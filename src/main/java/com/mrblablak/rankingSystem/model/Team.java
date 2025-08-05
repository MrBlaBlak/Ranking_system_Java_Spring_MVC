package com.mrblablak.rankingSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "win_or_loose")
    private int winOrLoose;
    @NotNull
    @Column(name = "flag_advantage")
    private int flagAdvantage;

}
