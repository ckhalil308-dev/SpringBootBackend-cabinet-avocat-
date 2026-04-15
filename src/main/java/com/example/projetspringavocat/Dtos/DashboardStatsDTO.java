package com.example.projetspringavocat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private Long totalClients;
    private Long affairesEnCours;
    private Long affairesTerminees;
    private Long audiencesCetteSemaine;
}

