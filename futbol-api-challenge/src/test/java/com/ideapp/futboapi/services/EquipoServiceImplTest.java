package com.ideapp.futboapi.services;

import com.ideapp.futboapi.entity.Equipo;
import com.ideapp.futboapi.exception.ResourceNotFoundException;
import com.ideapp.futboapi.repository.EquipoRepository;
import com.ideapp.futboapi.service.impl.EquipoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoServiceImplTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Test
    void findById_CuandoEquipoExiste_DeberiaDevolverEquipo() {
        long equipoId = 1L;
        Equipo equipoEsperado = new Equipo(equipoId, "Real Madrid", "La Liga", "España");

        when(equipoRepository.findById(equipoId)).thenReturn(Optional.of(equipoEsperado));

        Equipo equipoEncontrado = equipoService.findById(equipoId);

        assertNotNull(equipoEncontrado, "El equipo encontrado no debería ser nulo");
        assertEquals(equipoEsperado.getId(), equipoEncontrado.getId(), "Los IDs de los equipos no coinciden");
        assertEquals(equipoEsperado.getNombre(), equipoEncontrado.getNombre(), "Los nombres de los equipos no coinciden");
        assertEquals(equipoEsperado.getLiga(),equipoEncontrado.getLiga(), "Los ligas de los equipos no coinciden");
        assertEquals(equipoEsperado.getPais(),equipoEncontrado.getPais(), "Los paises de los equipos no coinciden");

        verify(equipoRepository, times(1)).findById(equipoId);
    }
    @Test
    void findById_CuandoEquipoNoExiste_DeberiaLanzarResourceNotFoundException() {
        long equipoId = 99L;
        String mensajeEsperado = "Equipo no encontrado";

        when(equipoRepository.findById(equipoId)).thenReturn(Optional.empty());

        ResourceNotFoundException exceptionLanzada = assertThrows(
                ResourceNotFoundException.class,
                () -> equipoService.findById(equipoId)
        );

        assertEquals(mensajeEsperado, exceptionLanzada.getMessage());

        verify(equipoRepository, times(1)).findById(equipoId);
    }


    @Test
    void findAll_CuandoHayEquipos_DeberiaDevolverListaDeEquipos() {
        List<Equipo> listaEquipos = List.of(
                new Equipo(1L, "Boca Juniors", "Liga Argentina", "Argentina"),
                new Equipo(2L, "River Plate", "Liga Argentina", "Argentina")
        );
        when(equipoRepository.findAll()).thenReturn(listaEquipos);


        List<Equipo> resultado = equipoService.findAll();


        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(equipoRepository, times(1)).findAll();
    }

    @Test
    void findAll_CuandoNoHayEquipos_DeberiaDevolverListaVacia() {
        when(equipoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Equipo> resultado = equipoService.findAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(equipoRepository, times(1)).findAll();
    }

    @Test
    void save_DeberiaGuardarYDevolverEquipo() {
        Equipo equipoSinId = new Equipo(null,"Boca Juniors", "Liga Argentina", "Argentina");
        Equipo equipoConId = new Equipo(1L, "Boca Juniors", "Liga Argentina", "Argentina");
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoConId);

        Equipo equipoGuardado = equipoService.save(equipoSinId);

        assertNotNull(equipoGuardado);
        assertEquals(1L, equipoGuardado.getId());
        assertEquals("Boca Juniors", equipoGuardado.getNombre());
        assertEquals("Liga Argentina", equipoGuardado.getLiga());
        assertEquals("Argentina", equipoGuardado.getPais());
        verify(equipoRepository, times(1)).save(equipoSinId);
    }

    @Test
    void update_CuandoEquipoExiste_DeberiaActualizarYDevolverEquipo() {
        long equipoId = 1L;
        Equipo equipoExistente = new Equipo(equipoId, "Viejo Nombre", "Vieja Liga", "Viejo Pais");
        Equipo datosNuevos = new Equipo(null, "Nuevo Nombre", "Nueva Liga", "Nuevo Pais");

        when(equipoRepository.findById(equipoId)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(any(Equipo.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Equipo equipoActualizado = equipoService.update(equipoId, datosNuevos);

        assertNotNull(equipoActualizado);
        assertEquals(equipoId, equipoActualizado.getId());
        assertEquals("Nuevo Nombre", equipoActualizado.getNombre());
        assertEquals("Nueva Liga", equipoActualizado.getLiga());
        verify(equipoRepository, times(1)).findById(equipoId);
        verify(equipoRepository, times(1)).save(equipoExistente);
    }

    @Test
    void update_CuandoEquipoNoExiste_DeberiaLanzarResourceNotFoundException() {
        long equipoId = 99L;
        Equipo datosNuevos = new Equipo(null, "Nuevo Nombre", "Nueva Liga", "Nuevo Pais");
        when(equipoRepository.findById(equipoId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> equipoService.update(equipoId, datosNuevos));
        verify(equipoRepository, times(1)).findById(equipoId);
        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    @Test
    void deleteById_CuandoEquipoExiste_DeberiaEliminarEquipo() {
        long equipoId = 1L;
        Equipo equipoExistente = new Equipo(equipoId, "Equipo a Borrar", "Liga", "Pais");
        when(equipoRepository.findById(equipoId)).thenReturn(Optional.of(equipoExistente));
        doNothing().when(equipoRepository).delete(equipoExistente);

        assertDoesNotThrow(() -> equipoService.deleteById(equipoId));

        verify(equipoRepository, times(1)).findById(equipoId);
        verify(equipoRepository, times(1)).delete(equipoExistente);
    }

    @Test
    void deleteById_CuandoEquipoNoExiste_DeberiaLanzarResourceNotFoundException() {
        long equipoId = 99L;
        when(equipoRepository.findById(equipoId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> equipoService.deleteById(equipoId));
        verify(equipoRepository, times(1)).findById(equipoId);
        verify(equipoRepository, never()).delete(any(Equipo.class));
    }

    @Test
    void findByNombre_CuandoEncuentraEquipos_DeberiaDevolverLista() {
        String nombreBusqueda = "Madrid";
        List<Equipo> listaEquipos = List.of(new Equipo(1L, "Real Madrid", "La Liga", "España"));
        when(equipoRepository.findByNombreContainingIgnoreCase(nombreBusqueda)).thenReturn(listaEquipos);

        List<Equipo> resultado = equipoService.findByNombre(nombreBusqueda);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(equipoRepository, times(1)).findByNombreContainingIgnoreCase(nombreBusqueda);
    }

    @Test
    void findByNombre_CuandoNoEncuentraEquipos_DeberiaLanzarResourceNotFoundException() {
        String nombreBusqueda = "Inexistente";
        when(equipoRepository.findByNombreContainingIgnoreCase(nombreBusqueda)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> equipoService.findByNombre(nombreBusqueda));
        verify(equipoRepository, times(1)).findByNombreContainingIgnoreCase(nombreBusqueda);
    }

}