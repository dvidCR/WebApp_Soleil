package com.soleil.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.soleil.api.model.Empleado;
import com.soleil.api.repository.EmpleadoRepository;
import com.soleil.api.service.EmpleadoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository repositorio;

    @InjectMocks
    private EmpleadoService servicio;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        // Inicializamos el empleado para las pruebas
        empleado = new Empleado("123456789", "Juan", "Pérez", "juan@empresa.com", "juan123", "contraseña123", "admin");
    }

    @Test
    void testObtenerPorDni() {
        // Definimos el comportamiento del mock
        when(repositorio.findById("123456789")).thenReturn(Optional.of(empleado));

        // Ejecutamos el método que estamos probando
        Optional<Empleado> resultado = servicio.obtenerPorDni("123456789");

        // Comprobamos que el resultado no sea vacío y que los valores coincidan
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        assertEquals("Pérez", resultado.get().getApellidos());
    }

    @Test
    void testEmpleadoNoExistente() {
        // Definimos que el empleado no existe
        when(repositorio.findById("987654321")).thenReturn(Optional.empty());

        // Ejecutamos el método que estamos probando
        Optional<Empleado> resultado = servicio.obtenerPorDni("987654321");

        // Comprobamos que el resultado esté vacío (no encontrado)
        assertFalse(resultado.isPresent());
    }
}
