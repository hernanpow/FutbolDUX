package com.ideapp.futboapi.controller;

import com.ideapp.futboapi.dto.EquipoDTO;
import com.ideapp.futboapi.entity.Equipo;
import com.ideapp.futboapi.mapper.EquipoMapper;
import com.ideapp.futboapi.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equipos")
@RequiredArgsConstructor
@Tag(name = "Equipos", description = "Operaciones sobre equipos de fútbol") // Agrupa los endpoints en Swagger
public class EquipoController {

    private final EquipoService equipoService;

    @Operation(summary = "Consultar todos los equipos", description = "Devuelve una lista de todos los equipos de fútbol registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o ausente")
    })
    @GetMapping
    public ResponseEntity<List<EquipoDTO>> getAllEquipos() {

        List<Equipo> equipos = equipoService.findAll();

        List<EquipoDTO> equipoDTOs = equipos.stream()
                .map(EquipoMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(equipoDTOs);
    }


    @Operation(summary = "Consultar un equipo por su ID", description = "Devuelve la información del equipo correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EquipoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o ausente")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> getEquipoById(
            @Parameter(description = "ID del equipo a buscar", required = true, example = "1")
            @PathVariable Long id) {

        Equipo equipo = equipoService.findById(id);

        EquipoDTO equipoDTO = EquipoMapper.toDTO(equipo);

        return ResponseEntity.ok(equipoDTO);
    }
}
