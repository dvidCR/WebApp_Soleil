package com.soleil.api.viewController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.model.Gasto;
import com.soleil.api.model.Servicio;
import com.soleil.api.service.EmpleadoService;
import com.soleil.api.service.FichajeService;
import com.soleil.api.service.GastoService;
import com.soleil.api.service.ServicioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class viewController {

    @Autowired
    private EmpleadoService servicio;
    
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private GastoService gastoService;
    
    @Autowired
    private FichajeService fichajeService;

    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
    }
    
    @GetMapping("/portalEmpleado")
    public String mostrarPortalEmpleado(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoActivo");
        if (empleado == null) {
            return "redirect:/login";
        }
        model.addAttribute("empleado", empleado);
        return "portalEmpleado";
    }
    
    @GetMapping("/admin")
    public String mostrarAdmin(Model model, HttpSession session) {
    	Empleado empleado = (Empleado) session.getAttribute("empleadoActivo");
        if (empleado == null) {
            return "redirect:/login";
        }
        model.addAttribute("empleado", empleado);
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
    
    @GetMapping("/tablaGestionEmpleado")
    public String mostrarTablaGestionEmpleado(Model model) {
    	List<Servicio> servicios = servicioService.obtenerTodos();
        model.addAttribute("servicios", servicios);
        return "gestionEmpleado";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                 @RequestParam String contrasena,
                                 Model model,
                                 HttpSession session) {

        List<Empleado> empleados = servicio.obtenerUsuario(usuario, contrasena);

        if (empleados.isEmpty()) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "index";
        }

        Empleado empleado = empleados.get(0);
        session.setAttribute("empleadoActivo", empleado);

        String rol = empleado.getRol();

        if ("admin".equalsIgnoreCase(rol)) {
            return "redirect:/admin";
        } else if ("empleado".equalsIgnoreCase(rol)) {
            return "redirect:/portalEmpleado";
        } else {
            model.addAttribute("error", "Rol no reconocido");
            return "index";
        }
    }
    
    @PostMapping("/ficharVista")
    @ResponseBody
    public String ficharVista(HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoActivo");

        if (empleado == null) {
            return "Usuario no autenticado";
        }

        List<Fichaje> fichajes = fichajeService.obtenerTodos().stream()
            .filter(f -> f.getEmpleado().getDni().equals(empleado.getDni()) &&
                         f.getFecha().equals(LocalDate.now()))
            .toList();

        for (Fichaje f : fichajes) {
            if (f.getHora_salida() == null) {
                fichajeService.actualizarHoraSalida(f.getId_fichaje(), new Fichaje(LocalTime.now()));
                return "Salida fichada correctamente";
            }
        }

        fichajeService.guardarFichaje(new Fichaje(LocalDate.now(), LocalTime.now(), empleado));
        return "Entrada fichada correctamente";
    }
}