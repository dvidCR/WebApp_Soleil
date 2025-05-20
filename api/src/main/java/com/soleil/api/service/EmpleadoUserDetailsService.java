//package com.soleil.api.service;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.soleil.api.model.Empleado;
//
//@Service
//public class EmpleadoUserDetailsService implements UserDetailsService {
//	
//	private final EmpleadoService empleadoService;
//	
//	public EmpleadoUserDetailsService(EmpleadoService empleadoService) {
//        this.empleadoService = empleadoService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Empleado emp = empleadoService.obtenerPorUsuario(username)
//            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
//        return emp;
//    }
//}