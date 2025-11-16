package com.ideapp.futboapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "La liga no puede estar vacia")
    private String liga;
    @NotBlank(message = "El pais no puede estar vacio")
    private String pais;
}