package com.soleil.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.repository.FichajeRepository;
import com.soleil.api.service.FichajeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FichajeServiceTest {

    @Mock
    private FichajeRepository repositorio;

    @InjectMocks
    private FichajeService servicio;

    private Fichaje fichaje;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado("123456789", "Juan", "Pérez", "juan@empresa.com", "juan123", "contraseña123", "admin");
        fichaje = new Fichaje(LocalDate.now(), LocalTime.now(), empleado);
    }

    @Test
    void testObtenerTodos() {
        when(repositorio.findAll()).thenReturn(Arrays.asList(fichaje));
        List<Fichaje> resultado = servicio.obtenerTodos();
        assertEquals(1, resultado.size());
        verify(repositorio, times(1)).findAll();
    }

    @Test
    void testObtenerPorId() {
        when(repositorio.findById(1)).thenReturn(Optional.of(fichaje));
        Optional<Fichaje> resultado = servicio.obtenerPorId(1);
        assertTrue(resultado.isPresent());
        assertEquals(empleado.getDni(), resultado.get().getEmpleado().getDni());
    }

    @Test
    void testGuardarFichaje() {
        when(repositorio.save(fichaje)).thenReturn(fichaje);
        Fichaje guardado = servicio.guardarFichaje(fichaje);
        assertNotNull(guardado);
        assertEquals(fichaje.getEmpleado().getDni(), guardado.getEmpleado().getDni());
    }

    @Test
    void testEliminarFichaje() {
        servicio.eliminarFichaje(1);
        verify(repositorio, times(1)).deleteById(1);
    }

//    @Test
//    void testActualizarHoraEntrada() {
//        LocalDate nuevaEntrada = LocalDate.of(2024, 4, 2);
//        Fichaje actualizado = new Fichaje(nuevaEntrada, fichaje.getHora_salida(), empleado);
//
//        when(repositorio.findById(1)).thenReturn(Optional.of(fichaje));
//        when(repositorio.save(any(Fichaje.class))).thenAnswer(i -> i.getArgument(0));
//
//        Fichaje resultado = servicio.actualizarHoraEntrada(1, actualizado);
//
//        assertEquals(nuevaEntrada, resultado.getHora_entrada());
//        verify(repositorio).save(any(Fichaje.class));
//    }

    @Test
    void testActualizarHoraSalida() {
        Fichaje actualizado = new Fichaje(LocalTime.now());

        when(repositorio.findById(1)).thenReturn(Optional.of(fichaje));
        when(repositorio.save(any(Fichaje.class))).thenAnswer(i -> i.getArgument(0));

        Fichaje resultado = servicio.actualizarHoraSalida(1, actualizado);

        assertEquals(actualizado, resultado.getHora_salida());
        verify(repositorio).save(any(Fichaje.class));
    }

//    @Test
//    void testActualizarHoraEntradaConIdInexistente() {
//        when(repositorio.findById(99)).thenReturn(Optional.empty());
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () ->
//                servicio.actualizarHoraEntrada(99, fichaje));
//
//        assertEquals("Dia no encontrado", exception.getMessage());
//    }

    @Test
    void testActualizarHoraSalidaConIdInexistente() {
        when(repositorio.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                servicio.actualizarHoraSalida(99, fichaje));

        assertEquals("Dia no encontrado", exception.getMessage());
    }
}
