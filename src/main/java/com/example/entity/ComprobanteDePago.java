package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.entity.enums.EstadoComprobante;
import com.example.entity.enums.MetodoPago;

@Entity
@Table(name = "Comprobante_Pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteDePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Comprobante")
    private Integer idComprobante;

    @OneToOne
    @JoinColumn(name = "ID_Cita", nullable = false, unique = true)
    private Cita cita;

    @Column(name = "Nombre_Pagador", nullable = false, length = 100)
    private String nombrePagador;

    @Column(name = "Apellidos_Pagador", nullable = false, length = 100)
    private String apellidosPagador;

    @Column(name = "DNI_Pagador", length = 8)
    private String dniPagador;

    @Column(name = "Contacto_Pagador", length = 15)
    private String contactoPagador;

    @Column(name = "Fecha_Emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();

    @Column(name = "Monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "Metodo_Pago", nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado")
    private EstadoComprobante estado = EstadoComprobante.EMITIDO;
    
    @Transient
    private Integer idCita; 

}