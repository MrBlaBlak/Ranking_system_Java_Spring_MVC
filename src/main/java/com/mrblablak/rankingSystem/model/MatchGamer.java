package com.mrblablak.rankingSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity
@Table(name = "match_gamer")
public class MatchGamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamer;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
