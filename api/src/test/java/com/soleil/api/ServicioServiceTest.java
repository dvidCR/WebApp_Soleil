package com.soleil.api;

import com.soleil.api.model.Servicio;
import com.soleil.api.model.Empleado;
import com.soleil.api.model.Paciente;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.repository.ServicioRepository;
import com.soleil.api.service.ServicioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    private Servicio servicio;

    @BeforeEach
    void setUp() {
        Empleado empleado = new Empleado("12345678A");
        Paciente paciente = new Paciente("87654321Z");
        Tratamiento tratamiento = new Tratamiento(1);

        servicio = new Servicio(
            LocalDate.now(),
            empleado,
            paciente,
            tratamiento,
            "Efectivo",
            50.0,
            "Sesión de terapia",
            3
        );
        servicio.setId_servicio(1);
    }

    @Test
    void testObtenerTodos() {
        when(servicioRepository.findAll()).thenReturn(List.of(servicio));

        List<Servicio> resultado = servicioService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Efectivo", resultado.get(0).getModo_pago());
    }

    @Test
    void testObtenerPorId() {
        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));

        Optional<Servicio> resultado = servicioService.obtenerPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(50.0, resultado.get().getTarifa());
    }

    @Test
    void testGuardarServicio() {
        when(servicioRepository.save(servicio)).thenReturn(servicio);

        Servicio guardado = servicioService.guardarServicio(servicio);

        assertNotNull(guardado);
        assertEquals("Sesión de terapia", guardado.getConcepto());
    }

    @Test
    void testEliminarServicio() {
        servicioService.eliminarServicio(1);
        verify(servicioRepository, times(1)).deleteById(1);
    }

    @Test
    void testActualizarServicio() {
        Servicio servicioActualizado = new Servicio(
            LocalDate.now(),
            new Empleado("99999999X"),
            new Paciente("11111111Y"),
            new Tratamiento(2),
            "Tarjeta",
            75.0,
            "Sesión nueva",
            5
        );
        servicioActualizado.setId_servicio(1);

        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));
        when(servicioRepository.save(any(Servicio.class))).thenAnswer(i -> i.getArgument(0));

        Servicio resultado = servicioService.actualizarServicio(1, servicioActualizado);

        assertEquals("Tarjeta", resultado.getModo_pago());
        assertEquals(75.0, resultado.getTarifa());
        assertEquals(5, resultado.getNum_sesiones());
    }

    @Test
    void testActualizarServicioNoEncontrado() {
        when(servicioRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                servicioService.actualizarServicio(999, servicio));

        assertEquals("Servicio no encontrado", exception.getMessage());
    }
}
