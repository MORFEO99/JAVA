package com.example.billetera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_emisor_id")
    private Usuario usuarioEmisor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_receptor_id")
    private Usuario usuarioReceptor;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    private String descripcion;

    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EstadoTransaccion estado = EstadoTransaccion.COMPLETADA;

    // Constructores
    public Transaccion() {}

    public Transaccion(Usuario usuarioEmisor, Usuario usuarioReceptor, BigDecimal monto,
                       String descripcion) {
        this.usuarioEmisor = usuarioEmisor;
        this.usuarioReceptor = usuarioReceptor;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuarioEmisor() { return usuarioEmisor; }
    public void setUsuarioEmisor(Usuario usuarioEmisor) { this.usuarioEmisor =
            usuarioEmisor; }
    public Usuario getUsuarioReceptor() { return usuarioReceptor; }
    public void setUsuarioReceptor(Usuario usuarioReceptor) { this.usuarioReceptor =
            usuarioReceptor; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public EstadoTransaccion getEstado() { return estado; }
    public void setEstado(EstadoTransaccion estado) { this.estado = estado; }
}

enum EstadoTransaccion {
    PENDIENTE, COMPLETADA, RECHAZADA
}