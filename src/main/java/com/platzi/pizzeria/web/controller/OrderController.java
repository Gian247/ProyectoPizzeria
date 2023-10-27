package com.platzi.pizzeria.web.controller;

import com.platzi.pizzeria.persistence.entity.OrderEntity;
import com.platzi.pizzeria.persistence.projection.OrderSummary;
import com.platzi.pizzeria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAll(){
        return  ResponseEntity.ok(this.orderService.getAll());
    }

    //OBTIENE UNA LISTA DE LAS ORDENES A PARTIR DEL DIA DE HOY
    @GetMapping("/today")
    public ResponseEntity<List<OrderEntity>> getTodayOrders(){
        return ResponseEntity.ok(this.orderService.getTodayOrders());
    }
    //SE OBTINENE LAS PIZZAS QUE TENGAN LOS PARAMETROS DE FILTRADO COINCIDENTES
    @GetMapping("/outside")
    public ResponseEntity<List<OrderEntity>> getOutsideOrders(){
        return ResponseEntity.ok(this.orderService.getOutsideOrders());
    }



    /*
     ************************* @QUERY CON SQL NATIVO *****************************
     */

    @GetMapping("/customer/{idCustomer}")
    public ResponseEntity<List<OrderEntity>> getCustomersOrders(@PathVariable String idCustomer){
        return ResponseEntity.ok(this.orderService.getCustomerOrders(idCustomer));
    }

    /*
     ************************* @QUERY PERSONALIZADO *****************************
     */

    @GetMapping("/summary/{orderId}")
    public ResponseEntity<OrderSummary> getSummary(@PathVariable int orderId){
        return ResponseEntity.ok(this.orderService.getSummary(orderId));
    }
}
