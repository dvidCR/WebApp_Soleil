package com.soleil.api;

import com.soleil.api.dto.TratamientoDTO;
import com.soleil.api.model.Empleado;
import com.soleil.api.model.Paciente;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.repository.PacienteRepository;
import com.soleil.api.repository.TratamientoRepository;
import com.soleil.api.service.PacienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private TratamientoRepository tratamientoRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
    	Empleado empleado = new Empleado("55555555C");
        paciente = new Paciente("12345678A", "Laura", "Gómez", empleado);
    }

    @Test
    void testObtenerTodos() {
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> resultado = pacienteService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Laura", resultado.get(0).getNombre());
    }

    @Test
    void testObtenerPorDni() {
        when(pacienteRepository.findById("12345678A")).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = pacienteService.obtenerPorDni("12345678A");

        assertTrue(resultado.isPresent());
        assertEquals("Gómez", resultado.get().getApellidos());
    }

    @Test
    void testGuardarPaciente() {
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        Paciente guardado = pacienteService.guardarPaciente(paciente);

        assertNotNull(guardado);
        assertEquals("Laura", guardado.getNombre());
    }

    @Test
    void testEliminarPaciente() {
        pacienteService.eliminarPaciente("12345678A");
        verify(pacienteRepository, times(1)).deleteById("12345678A");
    }

    @Test
    void testActualizarPaciente() {
        Paciente actualizado = new Paciente("12345678A", "Lucía", "Fernández", null);

        when(pacienteRepository.findById("12345678A")).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(i -> i.getArgument(0));

        Paciente resultado = pacienteService.actualizarPaciente("12345678A", actualizado);

        assertEquals("Lucía", resultado.getNombre());
        assertEquals("Fernández", resultado.getApellidos());
    }

    @Test
    void testActualizarPacienteNoEncontrado() {
        when(pacienteRepository.findById("99999999Z")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pacienteService.actualizarPaciente("99999999Z", paciente));

        assertEquals("Paciente no encontrado", exception.getMessage());
    }

    @Test
    void testVerTratamiento() {
        Tratamiento tratamiento1 = new Tratamiento("Fisioterapia", "Ejercicios de movilidad", new Paciente("12345678A"));
        Tratamiento tratamiento2 = new Tratamiento("Psicología", "Sesiones de apoyo emocional", new Paciente("12345678A"));

        when(tratamientoRepository.buscarTratamientosPorDniPaciente("12345678A"))
            .thenReturn(List.of(tratamiento1, tratamiento2));

        List<TratamientoDTO> resultado = pacienteService.verTratamiento("12345678A");

        assertEquals(2, resultado.size());
        assertEquals("Fisioterapia", resultado.get(0).getTipo_tratamiento());
        assertEquals("Ejercicios de movilidad", resultado.get(0).getDescripcion());
        assertEquals("Psicología", resultado.get(1).getTipo_tratamiento());
        assertEquals("Sesiones de apoyo emocional", resultado.get(1).getDescripcion());
    }

}
