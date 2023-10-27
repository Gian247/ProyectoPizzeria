package com.platzi.pizzeria.persistence.repository;

import com.platzi.pizzeria.persistence.entity.PizzaEntity;
import com.platzi.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity,Integer> {

    /*
    ******************  QUERY METHODS *********************
     */
    //PIZZAS DISPONIBLES ORDENADAS POR EL PRECIO
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();



    //OBTENER PIZZA MEDIANTE EL NOMBRE
    // IgnoreCase: ADMITIENDO MINUSCULAS Y MAYUSCULAS Y QUE ESTEN DISPONIBLES
    //PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);


    //findfirst: REcupera el primer registro que cumpla con los condicionales
    //findTop: permite definir cuantos registros de obtendran
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);



    //TODAS LAS PIZZAS QUE ESTEN DISPONIBLES Y QUE EN SU DESCRIPCION
    // (CONTAINS)SI CONTENGA LO QUE SE LE MANDE POR EL PARAMETRO
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);



    //TODAS LAS PIZZAS QUE ESTEN DISPONIBLES Y QUE EN SU DESCRIPCION
    // (Not)NO CONTENGA LO QUE SE LE MANDE POR EL PARAMETRO
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);


    //QUERY METHOD PARA CONTAR CON UNA CONDICION
    int countByVeganTrue();

    //OBTIENE EL TOP 3 DE PIZZAS QUE ESTES DISPONIBLES Y CUYO PRECIO SEA MENOR IGUAL AL LA VARIABLE
    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);


    /*
     ************************* @Modifying  para CRUD con @Query *****************************
     */

    //1ER FORMA
    /*@Query(value = "UPDATE pizza "+
            "SET price = :newPrice "+
            "WHERE idPizza = :idPizza",nativeQuery = true)
    void updatePrice(@Param("idPizza") int idPizza, @Param("newPrice") double newPrice);*/

    //2DA FORMA
    //UTILIZANDO EL SpEL(SPRING ESPECIAL LENGUAJE)
    //DE ESTA MANERA PORDEMOS TENER UN OBJETO COMPLETO COMO PARAMETRO Y UTILIZAR ATRIBUTOS INTERNOS DE ESE
    //OBJETO EN UNA CONSULTA SQL

    @Query(value = "UPDATE pizza "+
            "SET price = :#{#newPizzaPrice.newPrice} "+//UTILIZA EL SPRING SPECIAL LANGUAGE PARA AGARRAR
            //DE UN OBJETO ALGUN PARAMETRO EN ESPECIFICO DENTRO DEL QUERY
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}",nativeQuery = true)
    @Modifying
    void updatePrice(@Param("newPizzaPrice")UpdatePizzaPriceDto newPizzaPrice);





}
