package com.example.billetera.controller;

import com.example.billetera.model.Transaccion;
import com.example.billetera.model.Usuario;
import com.example.billetera.service.TransaccionService;
import com.example.billetera.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/enviar")
    public String mostrarFormularioEnvio(Model model) {
        model.addAttribute("transaccion", new Transaccion());
        return "transacciones/enviar";
    }

    @PostMapping("/enviar")
    public String enviarDinero(@RequestParam String destino,
                               @RequestParam BigDecimal monto,
                               @RequestParam String descripcion,
                               RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario emisor = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<Usuario> receptorOpt = usuarioService.findByEmail(destino);
        if (receptorOpt.isEmpty()) {
            receptorOpt = usuarioService.findByUsername(destino);
        }

        if (receptorOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario destino no encontrado");
            return "redirect:/transacciones/enviar";
        }

        Usuario receptor = receptorOpt.get();

        try {
            Transaccion transaccion = transaccionService.realizarTransferencia(emisor,
                    receptor, monto, descripcion);
            redirectAttributes.addFlashAttribute("success", "Transferencia realizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/historial")
    public String historialTransacciones(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("transaccionesEnviadas",
                transaccionService.obtenerTransaccionesEnviadas(usuario));
        model.addAttribute("transaccionesRecibidas",
                transaccionService.obtenerTransaccionesRecibidas(usuario));
        return "transacciones/historial";
    }
}