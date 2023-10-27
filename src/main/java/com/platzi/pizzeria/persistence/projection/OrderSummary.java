package com.platzi.pizzeria.persistence.projection;

import java.time.LocalDateTime;

public interface OrderSummary {

    //................................. PROJECTION EN JAVA ................................

    /*
    En el contexto de ORM, una proyección se refiere a seleccionar un subconjunto de columnas de una tabla
    de base de datos y mapearlas a objetos Java. En lugar de recuperar todos los campos de una entidad,
    puedes seleccionar solo las columnas que necesitas y mapearlas a objetos Java. Esto puede mejorar el
    rendimiento y reducir la cantidad de datos transferidos entre la base de datos y la aplicación.
     */
    /*
    ****************************** QUERY PERSONALIZADO ***************************
     */
    //DEBEMOS PONER LOS ATRIBUTOS QUE SE DESEEN TENER EN EL QUERY PERSONALIZADO
    //PERO COMO UNA INTERFAZ SIEMPRE DEBE EXPRESARSE EN FORMA DE METODOS, SE EXPRESA COMO SI FUERAN METODOS GET

    Integer getIdOrder();
    String getCustomerName();
    LocalDateTime getOrderDate();
    Double getOrderTotal();
    String getPizzaNames();



}
