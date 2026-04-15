package com.example.projetspringavocat.Services;

import com.example.projetspringavocat.Dtos.RdvRequestDTO;
import com.example.projetspringavocat.Dtos.RdvResponseDTO;
import com.example.projetspringavocat.Entities.StatusRdv;

import java.util.List;

public interface RdvService {
    RdvResponseDTO proposerRdv(RdvRequestDTO request);
    RdvResponseDTO confirmerRdv(Integer rdvId);
    RdvResponseDTO annulerRdv(Integer rdvId, String motifRefus);
    List<RdvResponseDTO> getRdvsPourSecretaire(StatusRdv status);
    List<RdvResponseDTO> getRdvsPourClient(StatusRdv status);
}

