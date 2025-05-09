package com.soleil.api.viewController;

import com.soleil.api.model.Empleado;
import com.soleil.api.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmpleadoViewController {

    @Autowired
    private EmpleadoService servicio;

    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
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
            return "usuario";
        } else {
            model.addAttribute("error", "Rol no reconocido");
            return "index";
        }
    }
}
