package com.mrblablak.rankingSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @NoArgsConstructor
@Entity
@Table(name = "kills_and_caps")
public class KillsAndCaps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(optional=false)
    @JoinColumn(name = "match_gamer_id")
    private MatchGamer matchGamer;
    @NotNull
    private int kills;
    @NotNull
    private int caps;
    @NotNull
    private Titan_Name titan;
    public enum Titan_Name{
        ion, tone, ronin, northstar, monarch, legion, scorch, none
    }

}
