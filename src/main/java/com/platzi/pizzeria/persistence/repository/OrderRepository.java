package com.platzi.pizzeria.persistence.repository;

import com.platzi.pizzeria.persistence.entity.OrderEntity;
import com.platzi.pizzeria.persistence.projection.OrderSummary;
import com.platzi.pizzeria.service.OrderService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity,Integer> {

    /*
    *********************************** QUERY METHODS ***************************
     */

    //RECUPERA UNA LISTA DE ORDENES QUE SE HIZIERON EL DIA DE HOY
    //AFTER: FECHA DE DESPUES
    //BEFORE: FECHA DE ANTES
    //(LA FECHA CON LA CUAL SE QUIERE COMPARAR)
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);

    //PERMITE VER LAS ORDENES QUE NO HAN SIDO PARA CONSUMIR EN EL LOCAL
    //IN: CUANDO SE USA IN SE DEBE RECIBIR UN LISTA CON LOS PARAMETROS QUE VA A BUSCAR
    List<OrderEntity> findAllByMethodIn(List<String> methods);



    /*
    ************************* @QUERY CON SQL NATIVO *****************************
     */

    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id",nativeQuery = true)
    List<OrderEntity> findCustomerOrders(@Param("id") String idCustomer);

    /*
     ************************* @QUERY PERSONALIZADO *****************************
     */

    @Query(value = "SELECT po.id_order AS idOrder,cu.name AS customerName,po.date AS orderDate,po.total AS orderTotal,GROUP_CONCAT(pi.name) AS pizzaNames FROM pizza_order po " +
            "INNER JOIN customer cu ON po.id_customer=cu.id_customer " +
            "INNER JOIN order_item oi ON po.id_order= oi.id_order " +
            "INNER JOIN pizza pi ON oi.id_pizza=pi.id_pizza " +
            "WHERE po.id_order=:orderId GROUP BY po.id_order, cu.name, po.date, po.total;",nativeQuery = true)
    OrderSummary findSummary(@Param("orderId") int orderId);




}
