package com.soleil.api.viewController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.model.Gasto;
import com.soleil.api.model.Servicio;
import com.soleil.api.service.EmpleadoService;
import com.soleil.api.service.FichajeService;
import com.soleil.api.service.GastoService;
import com.soleil.api.service.PacienteService;
import com.soleil.api.service.ServicioService;
import com.soleil.api.service.TratamientoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class viewController {

    @Autowired
    private EmpleadoService empleadoService;
    
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private GastoService gastoService;
    
    @Autowired
    private FichajeService fichajeService;
    
    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private TratamientoService tratamientoService;

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
    
    @GetMapping("/configurarUsuarios")
    public String mostrarUsuario(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        model.addAttribute("dniSeleccionado", "");
        return "configurarUsuarios";
    }
    
    @PostMapping("/configurarUsuarios")
    public String crearEmpleado(@ModelAttribute Empleado empleado) {
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/configurarUsuarios";
    }

    @PutMapping("/configurarUsuarios/{dni}")
    public String actualizarEmpleado(@PathVariable String dni, @ModelAttribute Empleado empleado) {
        empleadoService.actualizarEmpleado(dni, empleado);
        return "redirect:/configurarUsuarios";
    }
    
    @PutMapping("/configurarUsuarios/actualizarDNI/{dni}")
    public String actualizarDni(@PathVariable String dni, @RequestParam String nuevoDni) {
        empleadoService.actualizarDni(dni, nuevoDni);
        return "redirect:/configurarUsuarios";
    }
    
    @Transactional
    @DeleteMapping("/configurarUsuarios/{dni}")
    public String borrarEmpleado(@PathVariable String dni) {
        empleadoService.eliminarEmpleado(dni);
        return "redirect:/configurarUsuarios";
    }

    @GetMapping("/gestionEmpleado")
    public String mostrarTablaGestionEmpleado(@RequestParam(name = "filtroEmpleado", required = false) String dniEmpleado, Model model) {
    	cargarServiciosFiltrados(dniEmpleado, model, true);
        model.addAttribute("filtroEmpleado", dniEmpleado);
        return "gestionEmpleado";
    }

    @GetMapping("/contabilidad")
    public String mostrarTablaContabilidad(@RequestParam(name = "filtroEmpleado", required = false) String dniEmpleado, @RequestParam(name = "filtroProveedor", required = false) String proveedor, Model model) {
        cargarServiciosFiltrados(dniEmpleado, model, false);

        cargarGastosFiltrados(proveedor, model);
        
        List<String> proveedoresUnicos = gastoService.obtenerTodosProveedoresUnicos();
        model.addAttribute("proveedores", proveedoresUnicos);
        
        List<Servicio> serviciosTodos = servicioService.obtenerTodos();
        model.addAttribute("serviciosTodos", serviciosTodos);
        
        model.addAttribute("empleados", empleadoService.obtenerTodos());
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        model.addAttribute("tratamientos", tratamientoService.obtenerTodos());

        Double totalIngresos = (Double) model.getAttribute("totalIngresos");
        if (totalIngresos == null) totalIngresos = 0.0;

        Double totalGastos = (Double) model.getAttribute("totalGastos");
        if (totalGastos == null) totalGastos = 0.0;

        double beneficio = totalIngresos - totalGastos;
        model.addAttribute("beneficio", beneficio);
        
        model.addAttribute("filtroEmpleado", dniEmpleado);
        model.addAttribute("filtroProveedor", proveedor);

        return "contabilidad";
    }
    
    private void cargarServiciosFiltrados(String dniEmpleado, Model model, boolean excluirEmpleadoNulo) {
        List<Servicio> servicios;

        if (dniEmpleado != null && !dniEmpleado.isEmpty()) {
            servicios = servicioService.buscarServiciosPorFiltroEmpleado(dniEmpleado);
        } else {
            servicios = servicioService.obtenerTodos();
        }
        
        if (excluirEmpleadoNulo) {
            servicios = servicios.stream()
                    .filter(s -> s.getDni_empleado() != null)
                    .toList();
        }

        double totalIngresos = servicios.stream()
                .mapToDouble(s -> s.getTarifa() * s.getNum_sesiones())
                .sum();

        List<Empleado> empleados = empleadoService.obtenerTodos();

        model.addAttribute("servicios", servicios);
        model.addAttribute("totalIngresos", totalIngresos);
        model.addAttribute("empleados", empleados);
    }
    
    private void cargarGastosFiltrados(String proveedor, Model model) {
        List<Gasto> gastos;

        if (proveedor != null && !proveedor.isEmpty()) {
            gastos = gastoService.buscarPorProveedor(proveedor);
        } else {
            gastos = gastoService.obtenerTodos();
        }

        double totalGastos = gastos.stream()
                .mapToDouble(Gasto::getCantidad)
                .sum();

        model.addAttribute("gastos", gastos);
        model.addAttribute("totalGastos", totalGastos);
        
        model.addAttribute("proveedores", gastoService.obtenerTodosProveedoresUnicos());
    }


    @PostMapping("/servicio/add")
    public String addServicio(@ModelAttribute Servicio servicio) {
        servicioService.guardarServicio(servicio);
        return "redirect:/contabilidad";
    }

    @PostMapping("/servicio/update")
    public String updateServicio(@ModelAttribute Servicio servicio) {
        servicioService.actualizarServicio(servicio.getId_servicio(), servicio);
        return "redirect:/contabilidad";
    }

    @PostMapping("/servicio/delete")
    public String deleteServicio(@RequestParam int id_servicio) {
        servicioService.eliminarServicio(id_servicio);
        return "redirect:/contabilidad";
    }

    @PostMapping("/gasto/add")
    public String addGasto(@ModelAttribute Gasto gasto) {
        gastoService.guardarGasto(gasto);
        return "redirect:/contabilidad";
    }

    @PostMapping("/gasto/update")
    public String updateGasto(@ModelAttribute Gasto gasto) {
        gastoService.actualizarGasto(gasto.getId_gasto(), gasto);
        return "redirect:/contabilidad";
    }

    @PostMapping("/gasto/delete")
    public String deleteGasto(@RequestParam int id_gasto) {
        gastoService.eliminarGasto(id_gasto);
        return "redirect:/contabilidad";
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                 @RequestParam String contrasena,
                                 Model model,
                                 HttpSession session) {

        List<Empleado> empleados = empleadoService.obtenerUsuario(usuario, contrasena);

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
            .filter(f -> f.getDni_empleado().getDni().equals(empleado.getDni()) &&
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