package com.platzi.pizzeria.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;

    @Column(name = "id_customer", nullable = false, length = 15)
    private String idCustomer;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "DECIMAL(6,2)")
    private Double total;

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private String method;

    @Column(name = "additional_notes", length = 200)
    private String additionalNotes;

    //RELACIONES ENTRE CLASES

    @OneToOne(fetch = FetchType.LAZY)//Significa de que no cargue sino hasta que se use
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer",insertable = false,updatable = false)
    @JsonIgnore //Para que no lo incluya en el JSON
    private CustomerEntity customer;
    //COMO EN ESTE CASO SON MUCHOS ELEMENTOS LA RELACION ES POR ELLO QUE SE CREA UNA LIST PARA ALMACENAR TODOS
    //LOS ELEMENTOS EN CUESTION
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @OrderBy("price ASC")//ORDENA EL RESULTADO DE ESTE ATRIBUTO POR UN CAMPO
    private List<OrderItemEntity> items;



}