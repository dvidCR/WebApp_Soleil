package com.soleil.api.viewController;

import com.soleil.api.model.Empleado;
import com.soleil.api.model.Gasto;
import com.soleil.api.model.Servicio;
import com.soleil.api.service.EmpleadoService;
import com.soleil.api.service.GastoService;
import com.soleil.api.service.ServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class viewController {

    @Autowired
    private EmpleadoService servicio;
    
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private GastoService gastoService;

    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
    }
    
    @GetMapping("/vistaEmpleado")
    public String mostrarEmpleado(Model model) {
        return "empleado";
    }
    
    @GetMapping("/admin")
    public String mostrarAdmin(Model model) {
        return "admin";
    }

    @GetMapping("/gestionEmpleado")
    public String mostrarGestionEmpleado(Model model) {
        return "gestionEmpleado";
    }

    @GetMapping("/contabilidad")
    public String mostrarContabilidad(Model model) {
        return "contabilidad";
    }
    
    @GetMapping("/crearUsuario")
    public String mostrarUsuario(Model model) {
        return "crearUsuario";
    }

    @GetMapping("/tablaContabilidad")
    public String mostrarTablaContabilidad(Model model) {
    	List<Servicio> servicios = servicioService.obtenerTodos();
        List<Gasto> gastos = gastoService.obtenerTodos();

        double totalIngresos = servicioService.calcularTotalIngresos();
        double totalGastos = gastoService.calcularTotalGastos();
        double beneficio = totalIngresos - totalGastos;

        model.addAttribute("servicios", servicios);
        model.addAttribute("gastos", gastos);
        model.addAttribute("totalIngresos", totalIngresos);
        model.addAttribute("totalGastos", totalGastos);
        model.addAttribute("beneficio", beneficio);

        return "contabilidad";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                 @RequestParam String contrasena,
                                 Model model) {

        List<Empleado> empleados = servicio.obtenerUsuario(usuario, contrasena);

        if (empleados.isEmpty()) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "index";
        }

        Empleado empleado = empleados.get(0);
        String rol = empleado.getRol();

        if ("admin".equalsIgnoreCase(rol)) {
            model.addAttribute("admin", empleado);
            return "admin";
        } else if ("empleado".equalsIgnoreCase(rol)) {
            model.addAttribute("empleado", empleado);
            return "empleado";
        } else {
            model.addAttribute("error", "Rol no reconocido");
            return "index";
        }
    }
}
