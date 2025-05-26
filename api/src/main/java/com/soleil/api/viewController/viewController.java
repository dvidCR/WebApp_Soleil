package com.soleil.api.viewController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.soleil.api.dto.EmpleadoDTO;
import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.model.Gasto;
import com.soleil.api.model.Paciente;
import com.soleil.api.model.Servicio;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.service.EmpleadoService;
import com.soleil.api.service.FichajeService;
import com.soleil.api.service.GastoService;
import com.soleil.api.service.PacienteService;
import com.soleil.api.service.ServicioService;
import com.soleil.api.service.TratamientoService;

import jakarta.validation.Valid;

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
    public String mostrarPortalEmpleado(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/index";
        }
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        Empleado empleado = empleadoOpt.get();

        List<Fichaje> fichajesHoy = fichajeService.obtenerTodos().stream()
                .filter(f -> f.getDni_empleado() != null &&
                             f.getDni_empleado().getDni().equals(empleado.getDni()) &&
                             f.getFecha().equals(LocalDate.now()))
                .toList();
        
        model.addAttribute("empleado", empleado);
        model.addAttribute("fichajesHoy", fichajesHoy);
        return "portalEmpleado";
    }
    
    @PostMapping("/ficharVista")
    @ResponseBody
    public String ficharVista(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "Error: Usuario no autenticado.";
        }

        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "Error: Empleado no encontrado.";
        }

        Empleado empleado = empleadoOpt.get();
        LocalDate hoy = LocalDate.now();

        List<Fichaje> fichajesDeHoy = fichajeService.obtenerTodos().stream()
                .filter(f -> f.getDni_empleado() != null &&
                             f.getDni_empleado().getDni().equals(empleado.getDni()) &&
                             f.getFecha().equals(hoy))
                .toList();

        if (!fichajesDeHoy.isEmpty()) {
            Fichaje ultimoFichaje = fichajesDeHoy.get(fichajesDeHoy.size() - 1);

            if (ultimoFichaje.getHora_salida() != null) {
                return "Ya has fichado entrada y salida hoy.";
            }

            fichajeService.actualizarHoraSalida(
                ultimoFichaje.getId_fichaje(),
                new Fichaje(LocalTime.now())
            );
            return "Salida fichada correctamente";
        }

        fichajeService.guardarFichaje(new Fichaje(LocalDate.now(), LocalTime.now(), empleado));
        return "Entrada fichada correctamente";
    }
    
    @GetMapping("/anadirServicio")
    public String mostrarServiciosDelEmpleado(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Servicio> servicios;

        if (userDetails != null) {
            Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
            if (empleadoOpt.isPresent()) {
                Empleado empleado = empleadoOpt.get();
                servicios = servicioService.obtenerTodos().stream()
                    .filter(s -> s.getDni_empleado() != null && s.getDni_empleado().getDni().equals(empleado.getDni()))
                    .toList();
                model.addAttribute("empleado", empleado);
            } else {
                servicios = servicioService.obtenerTodos();
            }
        } else {
            servicios = servicioService.obtenerTodos();
        }

        List<Paciente> pacientes = pacienteService.obtenerTodos();
        List<Tratamiento> tratamientos = tratamientoService.obtenerTodos();
        List<Empleado> empleados = empleadoService.obtenerTodos();

        model.addAttribute("servicios", servicios);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("empleados", empleados);
        model.addAttribute("tratamientos", tratamientos);

        return "anadirServicio";
    }
    
    @PostMapping("/anadirServicio")
    public String empleadoAddServicio(@ModelAttribute Servicio servicio) {
        servicioService.guardarServicio(servicio);
        return "redirect:/anadirServicio";
    }
    
    @GetMapping("/admin")
    public String mostrarAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	if (userDetails == null) {
            return "redirect:/index";
        }
    	
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();       
        model.addAttribute("empleado", empleado);
        return "admin";
    }
    
    @GetMapping("/configurarUsuarios")
    public String mostrarUsuario(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();
        model.addAttribute("empleado", empleado);
        model.addAttribute("empleados", empleados);
        model.addAttribute("dniSeleccionado", "");
        return "configurarUsuarios";
    }
    
    @PostMapping("/configurarUsuarios")
    public String crearEmpleado(@Valid @ModelAttribute EmpleadoDTO empleadoDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("empleados", empleadoService.obtenerTodos());
            return "configurarUsuarios";
        }
        empleadoService.guardarEmpleadoDesdeDTO(empleadoDTO);
        return "redirect:/configurarUsuarios";
    }

    @PutMapping("/configurarUsuarios/{dni}")
    public String actualizarEmpleado(@PathVariable String dni, @Valid @ModelAttribute EmpleadoDTO empleadoDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("empleados", empleadoService.obtenerTodos());
            return "configurarUsuarios";
        }
        empleadoService.actualizarEmpleadoDesdeDTO(dni, empleadoDTO);
        return "redirect:/configurarUsuarios";
    }
    
    @PutMapping("/configurarUsuarios/actualizarEmpleadoDNI/{dni}")
    public String actualizarEmpleadoDni(@PathVariable String dni, @RequestParam String nuevoDni) {
        empleadoService.actualizarDni(dni, nuevoDni);
        return "redirect:/configurarUsuarios";
    }
    
    @Transactional
    @DeleteMapping("/configurarUsuarios/{dni}")
    public String borrarEmpleado(@PathVariable String dni) {
        empleadoService.eliminarEmpleado(dni);
        return "redirect:/configurarUsuarios";
    }
    
    @GetMapping("/configurarPacientes")
    public String mostrarPaciente(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();   
        
        model.addAttribute("empleado", empleado);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("dniSeleccionado", "");
        return "configurarPacientes";
    }
    
    @PostMapping("/configurarPacientes")
    public String crearPaciente(@ModelAttribute Paciente paciente) {
        pacienteService.guardarPaciente(paciente);
        return "redirect:/configurarPacientes";
    }

    @PutMapping("/configurarPacientes/{dni}")
    public String actualizarPaciente(@PathVariable String dni, @ModelAttribute Paciente paciente) {
        pacienteService.actualizarPaciente(dni, paciente);
        return "redirect:/configurarPacientes";
    }
    
    @PutMapping("/configurarPacientes/actualizarPacienteDNI/{dni}")
    public String actualizarPacienteDni(@PathVariable String dni, @RequestParam String nuevoDni) {
        pacienteService.actualizarDni(dni, nuevoDni);
        return "redirect:/configurarPacientes";
    }
    
    @Transactional
    @DeleteMapping("/configurarPacientes/{dni}")
    public String borrarPaciente(@PathVariable String dni) {
        pacienteService.eliminarPaciente(dni);
        return "redirect:/configurarPacientes";
    }
    
    @GetMapping("/configurarTratamientos")
    public String mostrarTratamientos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Tratamiento> tratamientos = tratamientoService.obtenerTodos();
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();
        
        model.addAttribute("empleado", empleado);
        model.addAttribute("tratamientos", tratamientos);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("tratamientoSeleccionado", "");
        return "configurarTratamientos";
    }

    @PostMapping("/configurarTratamientos")
    public String crearTratamiento(@ModelAttribute Tratamiento tratamiento, @RequestParam(required = false) String dni_paciente) {
        if (dni_paciente != null && !dni_paciente.isEmpty()) {
            Optional<Paciente> pacienteOpt = pacienteService.obtenerPorDni(dni_paciente);
            if (pacienteOpt.isPresent()) {
                tratamiento.setDni_paciente(pacienteOpt.get());
            } else {
                tratamiento.setDni_paciente(null);
            }
        } else {
            tratamiento.setDni_paciente(null);
        }
        tratamientoService.guardarTratamiento(tratamiento);
        return "redirect:/configurarTratamientos";
    }

    @PutMapping("/configurarTratamientos/{id}")
    public String actualizarTratamiento(@PathVariable Integer id, @ModelAttribute Tratamiento tratamiento, @RequestParam(required = false) String dni_paciente) {
        Optional<Tratamiento> tratExistenteOpt = tratamientoService.obtenerPorId(id);
        
        if (tratExistenteOpt.isEmpty()) {
            return "redirect:/configurarTratamientos";
        }
        
        Tratamiento tratExistente = tratExistenteOpt.get();

        tratExistente.setTipo_tratamiento(tratamiento.getTipo_tratamiento());
        tratExistente.setDescripcion(tratamiento.getDescripcion());

        if (dni_paciente != null && !dni_paciente.isEmpty()) {
            Optional<Paciente> pacienteOpt = pacienteService.obtenerPorDni(dni_paciente);
            if (pacienteOpt.isPresent()) {
                tratExistente.setDni_paciente(pacienteOpt.get());
            } else {
                tratExistente.setDni_paciente(null);
            }
        } else {
            tratExistente.setDni_paciente(null);
        }

        tratamientoService.actualizarTratamiento(id, tratExistente);
        return "redirect:/configurarTratamientos";
    }

    @Transactional
    @DeleteMapping("/configurarTratamientos/{id}")
    public String borrarTratamiento(@PathVariable Integer id) {
        tratamientoService.eliminarTratamiento(id);
        return "redirect:/configurarTratamientos";
    }

    @GetMapping("/gestionEmpleado")
    public String mostrarTablaGestionEmpleado(@RequestParam(name = "filtroEmpleado", required = false) String dniEmpleado, Model model,  @AuthenticationPrincipal UserDetails userDetails) {
        cargarServiciosFiltrados(dniEmpleado, model, true);
        
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();
        
        model.addAttribute("empleado", empleado);
        model.addAttribute("filtroEmpleado", dniEmpleado);
        return "gestionEmpleado";
    }

    @GetMapping("/contabilidad")
    public String mostrarTablaContabilidad(@RequestParam(name = "filtroEmpleado", required = false) String dniEmpleado, @RequestParam(name = "filtroProveedor", required = false) String proveedor, Model model,  @AuthenticationPrincipal UserDetails userDetails) {
        cargarServiciosFiltrados(dniEmpleado, model, false);

        cargarGastosFiltrados(proveedor, model);
        
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();
        
        model.addAttribute("empleado", empleado);
        
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
        
        BigDecimal ingresosRedondeados = BigDecimal.valueOf(totalIngresos).setScale(2, RoundingMode.HALF_UP);


        List<Empleado> empleados = empleadoService.obtenerTodos();

        model.addAttribute("servicios", servicios);
        model.addAttribute("totalIngresos", ingresosRedondeados.doubleValue());
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
        
        BigDecimal ingresosRedondeados = BigDecimal.valueOf(totalGastos).setScale(2, RoundingMode.HALF_UP);


        model.addAttribute("gastos", gastos);
        model.addAttribute("totalGastos", ingresosRedondeados.doubleValue());       
        model.addAttribute("proveedores", gastoService.obtenerTodosProveedoresUnicos());
    }
    
    @GetMapping("/verFichajes")
    public String mostrarFichajes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Fichaje> fichajes = fichajeService.obtenerTodos();
        List<Empleado> empleados = empleadoService.obtenerTodos();
        
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorUsuario(userDetails.getUsername());
        if (empleadoOpt.isEmpty()) {
            return "redirect:/index";
        }
        
        Empleado empleado = empleadoOpt.get();
        
        model.addAttribute("empleado", empleado);     
        model.addAttribute("fichajes", fichajes);
        model.addAttribute("empleados", empleados);
        return "verFichajes";
    }
    
}