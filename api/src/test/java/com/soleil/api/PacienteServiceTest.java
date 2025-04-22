package com.soleil.api;

import com.soleil.api.model.Paciente;
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
        paciente = new Paciente("12345678A", "Laura", "Gómez");
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
        Paciente actualizado = new Paciente("12345678A", "Lucía", "Fernández");

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
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[]{"Fisioterapia", 5});
        mockResultados.add(new Object[]{"Psicología", 3});

        when(tratamientoRepository.verTratamiento("12345678A")).thenReturn(mockResultados);

        Map<String, Integer> resultado = pacienteService.verTratamiento("12345678A");

        assertEquals(2, resultado.size());
        assertEquals(5, resultado.get("Fisioterapia"));
        assertEquals(3, resultado.get("Psicología"));
    }
}
