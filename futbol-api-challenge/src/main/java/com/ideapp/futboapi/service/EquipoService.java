package com.ideapp.futboapi.service;

import com.ideapp.futboapi.entity.Equipo;
import java.util.List;

/**
 * Interfaz para la capa de servicio que maneja la lógica de negocio para los equipos.
 * Define el contrato de las operaciones CRUD y otras operaciones relacionadas con los equipos.
 */
public interface EquipoService {

    /**
     * Devuelve una lista de todos los equipos.
     * @return una lista de objetos Equipo.
     */
    List<Equipo> findAll();

    /**
     * Busca un equipo por su ID.
     * @param id el ID del equipo a buscar.
     * @return el objeto Equipo encontrado.
     * @throws com.ideapp.futboapi.exception.ResourceNotFoundException si el equipo no se encuentra.
     */
    Equipo findById(Long id);

    /**
     * Guarda un nuevo equipo o actualiza uno existente.
     * @param equipo el objeto Equipo a guardar.
     * @return el equipo guardado (con el ID asignado si es nuevo).
     */
    Equipo save(Equipo equipo);

    /**
     * Actualiza un equipo existente identificado por su ID.
     * @param id el ID del equipo a actualizar.
     * @param equipo el objeto Equipo con los nuevos datos.
     * @return el equipo actualizado.
     * @throws com.ideapp.futboapi.exception.ResourceNotFoundException si el equipo a actualizar no se encuentra.
     */
    Equipo update(Long id, Equipo equipo);

    /**
     * Elimina un equipo por su ID.
     * @param id el ID del equipo a eliminar.
     * @throws com.ideapp.futboapi.exception.ResourceNotFoundException si el equipo a eliminar no se encuentra.
     */
    void deleteById(Long id);
}