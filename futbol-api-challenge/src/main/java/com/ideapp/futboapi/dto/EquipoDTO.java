package com.ideapp.futboapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoDTO {
    private Long id;
    private String nombre;
    private String liga;
    private String pais;
}