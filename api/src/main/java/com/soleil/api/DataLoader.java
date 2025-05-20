//package com.soleil.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.soleil.api.model.Empleado;
//import com.soleil.api.repository.EmpleadoRepository;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private EmpleadoRepository empleadoRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (empleadoRepository.findByUsuario("admin").isEmpty()) {
//            Empleado admin = new Empleado();
//            admin.setDni("00000000A");
//            admin.setNombre("Admin");
//            admin.setApellidos("Soleil");
//            admin.setCorreo("admin@soleil.com");
//            admin.setUsuario("admin");
//            admin.setContrasena(passwordEncoder.encode("12345678"));
//            admin.setRol("ADMIN");
//            empleadoRepository.save(admin);
//            System.out.println("Admin creado!");
//        }
//    }
//}