package com.ideapp.futboapi.service.impl;

import com.ideapp.futboapi.entity.Equipo;
import com.ideapp.futboapi.exception.ResourceNotFoundException;
import com.ideapp.futboapi.repository.EquipoRepository;
import com.ideapp.futboapi.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;

    @Override
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo findById(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));
    }

    @Override
    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo update(Long id, Equipo equipo) {

        Equipo equipoExistente = findById(id);

        equipoExistente.setNombre(equipo.getNombre());
        equipoExistente.setLiga(equipo.getLiga());
        equipoExistente.setPais(equipo.getPais());

        return equipoRepository.save(equipoExistente);
    }

    @Override
    public void deleteById(Long id) {
        Equipo equipoExistente = findById(id);
        equipoRepository.delete(equipoExistente);
    }

    public List<Equipo> findByNombre(String nombre){
        List<Equipo> equipos = equipoRepository.findByNombreContainingIgnoreCase(nombre);

        if(equipos.isEmpty()){
            throw new ResourceNotFoundException("No se encontro ningun equipo.");
        }

        return equipos;
    }
}