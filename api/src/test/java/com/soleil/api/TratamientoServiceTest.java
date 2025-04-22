package com.soleil.api;

import com.soleil.api.model.Paciente;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.repository.TratamientoRepository;
import com.soleil.api.service.TratamientoService;

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
class TratamientoServiceTest {

    @Mock
    private TratamientoRepository tratamientoRepository;

    @InjectMocks
    private TratamientoService tratamientoService;

    private Tratamiento tratamiento;

    @BeforeEach
    void setUp() {
        Paciente paciente = new Paciente("12345678A");
        tratamiento = new Tratamiento("Terapia física", paciente);
        tratamiento.setId_tratamiento(1);
    }

    @Test
    void testObtenerTodos() {
        when(tratamientoRepository.findAll()).thenReturn(List.of(tratamiento));

        List<Tratamiento> resultado = tratamientoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Terapia física", resultado.get(0).getTipo_tratamiento());
    }

    @Test
    void testObtenerPorId() {
        when(tratamientoRepository.findById(1)).thenReturn(Optional.of(tratamiento));

        Optional<Tratamiento> resultado = tratamientoService.obtenerPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Terapia física", resultado.get().getTipo_tratamiento());
    }

    @Test
    void testGuardarTratamiento() {
        when(tratamientoRepository.save(tratamiento)).thenReturn(tratamiento);

        Tratamiento resultado = tratamientoService.guardarTratamiento(tratamiento);

        assertNotNull(resultado);
        assertEquals("Terapia física", resultado.getTipo_tratamiento());
    }

    @Test
    void testEliminarTratamiento() {
        tratamientoService.eliminarTratamiento(1);
        verify(tratamientoRepository, times(1)).deleteById(1);
    }

    @Test
    void testActualizarGasto() {
        Tratamiento actualizado = new Tratamiento("Terapia ocupacional", new Paciente("87654321Z"));
        actualizado.setId_tratamiento(1);

        when(tratamientoRepository.findById(1)).thenReturn(Optional.of(tratamiento));
        when(tratamientoRepository.save(any(Tratamiento.class))).thenAnswer(i -> i.getArgument(0));

        Tratamiento resultado = tratamientoService.actualizarTratamiento(1, actualizado);

        assertEquals("Terapia ocupacional", resultado.getTipo_tratamiento());
        assertEquals("87654321Z", resultado.getDni_paciente().getDni());
    }

    @Test
    void testActualizarGastoNoEncontrado() {
        when(tratamientoRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tratamientoService.actualizarTratamiento(999, tratamiento));

        assertEquals("Tratamiento no encontrado", exception.getMessage());
    }
}
