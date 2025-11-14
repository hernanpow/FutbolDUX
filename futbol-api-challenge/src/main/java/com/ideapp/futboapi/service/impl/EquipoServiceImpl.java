package com.ideapp.futboapi.service;

import com.ideapp.futboapi.entity.Equipo;
import com.ideapp.futboapi.exception.ResourceNotFoundException;
import com.ideapp.futboapi.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz EquipoService.
 * Contiene la lógica de negocio para gestionar los equipos.
 */
@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    // Inyección de dependencias vía constructor (manejado por Lombok @RequiredArgsConstructor)
    private final EquipoRepository equipoRepository;

    @Override
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo findById(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con ID: " + id));
    }

    @Override
    public Equipo save(Equipo equipo) {
        // En una aplicación más compleja, aquí iría la validación de negocio.
        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo update(Long id, Equipo equipo) {
        // Primero, nos aseguramos de que el equipo exista
        Equipo equipoExistente = findById(id);

        // Actualizamos los campos del equipo existente con los datos nuevos
        equipoExistente.setNombre(equipo.getNombre());
        equipoExistente.setLiga(equipo.getLiga());
        equipoExistente.setPais(equipo.getPais());

        // Guardamos y devolvemos el equipo actualizado
        return equipoRepository.save(equipoExistente);
    }

    @Override
    public void deleteById(Long id) {
        // Verificamos si el equipo existe antes de intentar borrarlo.
        // findById ya lanza la excepción si no lo encuentra.
        Equipo equipoExistente = findById(id);
        equipoRepository.delete(equipoExistente);
    }
}