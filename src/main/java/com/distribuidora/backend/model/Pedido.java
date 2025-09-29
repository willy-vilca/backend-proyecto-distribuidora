package com.distribuidora.backend.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "id_ruta")
    private Integer idRuta;

    @Column(name = "direccion", columnDefinition = "text")
    private String direccion;
}
