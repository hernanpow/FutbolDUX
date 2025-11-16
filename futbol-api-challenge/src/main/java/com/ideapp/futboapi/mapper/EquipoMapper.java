package com.ideapp.futboapi.mapper;

import com.ideapp.futboapi.dto.EquipoDTO;
import com.ideapp.futboapi.entity.Equipo;

public class EquipoMapper {

    public static EquipoDTO toDTO(Equipo equipo) {
        return new EquipoDTO(
                equipo.getId(),
                equipo.getNombre(),
                equipo.getLiga(),
                equipo.getPais()
        );
    }

    public static Equipo toEntity(EquipoDTO equipoDTO) {
        Equipo equipo = new Equipo();
        equipo.setId(equipoDTO.getId());
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setLiga(equipoDTO.getLiga());
        equipo.setPais(equipoDTO.getPais());
        return equipo;
    }
}