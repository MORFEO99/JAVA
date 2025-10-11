package com.example.billetera.repository;

import com.example.billetera.model.Transaccion;
import com.example.billetera.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByUsuarioEmisorOrderByFechaDesc(Usuario usuario);
    List<Transaccion> findByUsuarioReceptorOrderByFechaDesc(Usuario usuario);
    List<Transaccion> findByUsuarioEmisorOrUsuarioReceptorOrderByFechaDesc(Usuario
                                                                                   emisor, Usuario receptor);
}