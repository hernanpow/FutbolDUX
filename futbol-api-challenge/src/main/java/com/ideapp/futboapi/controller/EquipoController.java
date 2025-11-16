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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equipos")
@RequiredArgsConstructor
@Tag(name = "Equipos", description = "Operaciones sobre equipos de fútbol")
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

    @Operation(summary = "Buscar equipos por nombre", description = "Devuelve una lista de equipos cuyos nombres contienen el valor proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipos encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún equipo que coincida con la búsqueda"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o ausente")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<EquipoDTO>> findEquipoByName(
            @Parameter(description = "Nombre o parte del nombre del equipo a buscar", required = true, example = "Madrid")
            @RequestParam String nombre) {

        List<Equipo> equipos = equipoService.findByNombre(nombre);


        List<EquipoDTO> equipoDTOs = equipos.stream()
                .map(EquipoMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(equipoDTOs);
    }


    @Operation(summary = "Crear un nuevo equipo", description = "Crea un nuevo equipo con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "La solicitud es inválida (ej. campos vacíos)"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o ausente")
    })
    @PostMapping
    public ResponseEntity<EquipoDTO> createEquipo(
            @Valid @RequestBody EquipoDTO equipoDTO) {

        Equipo nuevoEquipo = EquipoMapper.toEntity(equipoDTO);

        Equipo equipoGuardado = equipoService.save(nuevoEquipo);

        EquipoDTO dtoRespuesta = EquipoMapper.toDTO(equipoGuardado);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(equipoGuardado.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoRespuesta);
    }

    @Operation(summary = "Actualizar un equipo existente", description = "Actualiza la información completa de un equipo existente, identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "400", description = "La solicitud es inválida (ej. campos vacíos)"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o ausente")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> updateEquipo(
            @Parameter(description = "ID del equipo a actualizar", required = true, example = "1")
            @PathVariable Long id,

            @Valid @RequestBody EquipoDTO equipoDTO) {

        Equipo equipoActualizado = EquipoMapper.toEntity(equipoDTO);

        Equipo equipoGuardado = equipoService.update(id, equipoActualizado);

        EquipoDTO dtoRespuesta = EquipoMapper.toDTO(equipoGuardado);

        return ResponseEntity.ok(dtoRespuesta);
    }

    @Operation(summary = "Eliminar un equipo por su ID", description = "Elimina permanentemente el equipo correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o ausente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(
            @Parameter(description = "ID del equipo a eliminar", required = true, example = "1")
            @PathVariable Long id) {

        equipoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}


