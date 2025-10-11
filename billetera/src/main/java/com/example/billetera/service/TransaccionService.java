package com.example.billetera.service;

import com.example.billetera.model.Transaccion;
import com.example.billetera.model.Usuario;
import com.example.billetera.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public Transaccion realizarTransferencia(Usuario emisor, Usuario receptor, BigDecimal
            monto, String descripcion) {
        // Verificar saldo suficiente
        if (emisor.getSaldo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Actualizar saldos
        BigDecimal nuevoSaldoEmisor = emisor.getSaldo().subtract(monto);
        BigDecimal nuevoSaldoReceptor = receptor.getSaldo().add(monto);

        usuarioService.actualizarSaldo(emisor, nuevoSaldoEmisor);
        usuarioService.actualizarSaldo(receptor, nuevoSaldoReceptor);

        // Crear transacción
        Transaccion transaccion = new Transaccion(emisor, receptor, monto, descripcion);
        return transaccionRepository.save(transaccion);
    }

    public List<Transaccion> obtenerTransaccionesUsuario(Usuario usuario) {
        return
                transaccionRepository.findByUsuarioEmisorOrUsuarioReceptorOrderByFechaDesc(usuario,
                        usuario);
    }

    public List<Transaccion> obtenerTransaccionesEnviadas(Usuario usuario) {
        return transaccionRepository.findByUsuarioEmisorOrderByFechaDesc(usuario);
    }

    public List<Transaccion> obtenerTransaccionesRecibidas(Usuario usuario) {
        return transaccionRepository.findByUsuarioReceptorOrderByFechaDesc(usuario);
    }

    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    public Optional<Transaccion> findById(Long id) {
        return transaccionRepository.findById(id);
    }
}