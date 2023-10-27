package com.platzi.pizzeria.service;

import com.platzi.pizzeria.persistence.entity.OrderEntity;
import com.platzi.pizzeria.persistence.projection.OrderSummary;
import com.platzi.pizzeria.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll(){
        List<OrderEntity> orderEntities=this.orderRepository.findAll();
        orderEntities.forEach(o-> System.out.println(o.getCustomer().getName()));
        return orderEntities;
    }


    /*
     *********************************** QUERY METHODS ***************************
     */

    //CAPTURA LAS COMPRAS DEL DIA DE HOY
    public List<OrderEntity> getTodayOrders(){
        //OBTIENE LA FECHA DE HOY DESDE LA HORA 0 MINUTO 0
        LocalDateTime today= LocalDate.now().atTime(0,0);
        //RETORNAMOS PARA QUE SE HAGA LA FILTRACIÓN
        return this.orderRepository.findAllByDateAfter(today);
    }

    //CONSTANTES PARA DAR CLARIDAD AL EJERCICIO
    private static final String DELIVERY="D";
    private static final String CARRYOUT="C";
    private static final String ON_SITE="S";
    //FILTRA POR PIZZZAS QUE FUERON POR DELIVERY O PARA RECOGER
    public List<OrderEntity> getOutsideOrders(){
        //LISTA DE STREENS CON LOS PARAMETROS QUE BUSCARA EL QUERY METHOD
        List<String> methods= Arrays.asList(DELIVERY,CARRYOUT);
        return this.orderRepository.findAllByMethodIn(methods);
    }



    /*
     ************************* @QUERY CON SQL NATIVO *****************************
     */

    public List<OrderEntity> getCustomerOrders(String idCustomer){
        return this.orderRepository.findCustomerOrders(idCustomer);
    }


    /*
     ************************* @QUERY PERSONALIZADO *****************************
     */
    public OrderSummary getSummary(int orderId){
        return this.orderRepository.findSummary(orderId);
    }

}
