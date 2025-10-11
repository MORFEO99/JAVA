package com.example.billetera.controller;

import com.example.billetera.model.Usuario;
import com.example.billetera.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult
            result, Model model) {
        if (result.hasErrors()) {
            return "registro";
        }

        if (usuarioService.existsByUsername(usuario.getUsername())) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "registro";
        }

        if (usuarioService.existsByEmail(usuario.getEmail())) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        usuarioService.save(usuario);
        return "redirect:/login?registroExitoso";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}
