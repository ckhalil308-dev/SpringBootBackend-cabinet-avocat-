package com.example.projetspringavocat.Dtos;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifierCreneauDto {
    private Boolean reserve;
    private String motif;
    public Boolean isReserve() {
        return reserve;
    }

}
