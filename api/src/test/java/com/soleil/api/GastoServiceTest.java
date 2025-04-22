package com.soleil.api;

import com.soleil.api.model.Gasto;
import com.soleil.api.repository.GastoRepository;
import com.soleil.api.service.GastoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GastoServiceTest {

    @Mock
    private GastoRepository repositorio;

    @InjectMocks
    private GastoService servicio;

    private Gasto gasto;

    @BeforeEach
    void setUp() {
        gasto = new Gasto(100, "Compra material", "Amazon");
    }

    @Test
    void testObtenerTodos() {
        when(repositorio.findAll()).thenReturn(Arrays.asList(gasto));

        List<Gasto> resultado = servicio.obtenerTodos();

        assertEquals(1, resultado.size());
        verify(repositorio, times(1)).findAll();
    }

    @Test
    void testObtenerPorId() {
        when(repositorio.findById(1)).thenReturn(Optional.of(gasto));

        Optional<Gasto> resultado = servicio.obtenerPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Amazon", resultado.get().getProveedor());
    }

    @Test
    void testGuardarGasto() {
        when(repositorio.save(gasto)).thenReturn(gasto);

        Gasto guardado = servicio.guardarGasto(gasto);

        assertNotNull(guardado);
        assertEquals("Compra material", guardado.getMotivo());
    }

    @Test
    void testEliminarGasto() {
        servicio.eliminarGasto(1);
        verify(repositorio, times(1)).deleteById(1);
    }

    @Test
    void testActualizarGasto() {
        Gasto actualizado = new Gasto(200, "Renovación equipo", "MediaMarkt");

        when(repositorio.findById(1)).thenReturn(Optional.of(gasto));
        when(repositorio.save(any(Gasto.class))).thenAnswer(i -> i.getArgument(0));

        Gasto resultado = servicio.actualizarGasto(1, actualizado);

        assertEquals(200, resultado.getCantidad());
        assertEquals("Renovación equipo", resultado.getMotivo());
        assertEquals("MediaMarkt", resultado.getProveedor());
        verify(repositorio).save(any(Gasto.class));
    }

    @Test
    void testActualizarGastoIdInexistente() {
        when(repositorio.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                servicio.actualizarGasto(99, gasto));

        assertEquals("Gasto no encontrado", exception.getMessage());
    }
}
