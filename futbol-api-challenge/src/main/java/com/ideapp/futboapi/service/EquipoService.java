package com.ideapp.futboapi.service;

import com.ideapp.futboapi.entity.Equipo;
import java.util.List;


public interface EquipoService {


    List<Equipo> findAll();

    Equipo findById(Long id);

    Equipo save(Equipo equipo);

    Equipo update(Long id, Equipo equipo);

    void deleteById(Long id);

    List<Equipo> findByNombre(String nombre);
}