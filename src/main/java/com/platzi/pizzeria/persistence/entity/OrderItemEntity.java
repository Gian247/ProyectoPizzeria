package com.platzi.pizzeria.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@IdClass(OrderItemId.class)//ESTA ANOTACION AYUDA A REFERENCIAR CLAVER FORANEAS COMPUESTAS
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity {
    //VARIOS IDS QUE SE REFERENCIUAN EN OTRA CLASE, ESTE METODO SE USA PARA REPRESENTAR LLAVES FORANEAS
    //COMPUESTAS
    @Id
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;
    @Id
    @Column(name = "id_item", nullable = false)
    private Integer idItem;
    @Column(name = "id_pizza", nullable = false)
    private Integer idPizza;

    @Column(nullable = false, columnDefinition = "DECIMAL(2,1)")
    private Double quantity;
    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double price;


    //RELACIONES ENTRE CLASES

    @ManyToOne//PUEDE HABER MUCHOS REGISTROS A PARTIR DE UNA SOLA ORDEN
    //NO INTERESA A TRAVEZ DE ESTA RELACION ACTUALIZAR O INSERTAR ELEMENTOS EN LA TABLA PADRE
    @JoinColumn(name = "id_order",referencedColumnName = "id_order", insertable = false,updatable = false)
    @JsonIgnore //Cuando se este serializando el objeto no tenga en cuenta esta propiedad, ya que esto generaria un
    //bucle infinito en el cual las ordenes llamarian a los items y los item a las ordenes, esto infinitamente
    private OrderEntity order;


    @OneToOne //UN ORDERITEM SOLO PUEDE TENER UNA PIZZA
    @JoinColumn(name = "id_pizza",referencedColumnName = "id_pizza",insertable = false,updatable = false)
    private PizzaEntity pizza;


}
