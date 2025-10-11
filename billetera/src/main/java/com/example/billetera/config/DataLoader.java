package com.example.billetera.config;

import com.example.billetera.model.Usuario;
import com.example.billetera.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario admin por defecto si no existe
        if (!usuarioService.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setNombreCompleto("Administrador");
            admin.setEmail("admin@billetera.com");
            admin.setTelefono("123456789");
            admin.setRol("ADMIN");
            usuarioService.save(admin);
            System.out.println("Usuario admin creado: admin/admin123");
        }

        // Crear usuario demo
        if (!usuarioService.existsByUsername("demo")) {
            Usuario demo = new Usuario();
            demo.setUsername("demo");
            demo.setPassword("demo123");
            demo.setNombreCompleto("Usuario Demo");
            demo.setEmail("demo@billetera.com");
            demo.setTelefono("987654321");
            demo.setRol("USER");
            usuarioService.save(demo);
            System.out.println("Usuario demo creado: demo/demo123");
        }
    }
}