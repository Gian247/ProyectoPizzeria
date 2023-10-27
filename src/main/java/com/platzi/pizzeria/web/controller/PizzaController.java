package com.platzi.pizzeria.web.controller;

import com.platzi.pizzeria.persistence.entity.PizzaEntity;
import com.platzi.pizzeria.service.PizzaService;
import com.platzi.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //LE DICE A SPRING QUE ES EL RESCONTROLADOR
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @Autowired//INYECTAMOS EL SERVICIO QUE ACABAMOS DE CONTRUIR
    public PizzaController(PizzaService pizzaService) {
        //
        this.pizzaService = pizzaService;
    }
    /*
    **************************** ENDPOINTS ******************************************
     */

    //METODO
    //@GetMapping//METODO POR EL CUAL VA A SER LLAMADO
    //RESPONSEENTITY SIRVE PARA DARLE FORMATO A LAS RESPUESTAS HTTP

    /*public ResponseEntity<List<PizzaEntity>> getAll(){
        //DENTRO DEL METODO OK SE HACE EL LLAMADO A LA CLASE INYECTADAD Y A SU METODO
        // QUE FUNCIONA COMO SERVICIO PARA OBTENER LAS PIZZAS
        return  ResponseEntity.ok(this.pizzaService.getAll());
    }*/
    //CONSULTA CON PAGINACION
    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int elements){
        //DENTRO DEL METODO OK SE HACE EL LLAMADO A LA CLASE INYECTADAD Y A SU METODO
        // QUE FUNCIONA COMO SERVICIO PARA OBTENER LAS PIZZAS
        return  ResponseEntity.ok(this.pizzaService.getAll(page,elements));
    }
    
    
    
    
    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> get(@PathVariable("idPizza") int idPizza){
        return ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza){
        if(pizza.getIdPizza()==null || !this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();


    }

    //ACTUALIZAR
    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza){
        if(pizza.getIdPizza()!=null && this.pizzaService.exists(pizza.getIdPizza())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable int idPizza){
        //VERIFICAMOS SI LA PIZZA EXISTE
        if(this.pizzaService.exists(idPizza)){
            this.pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }

    /*
    ******************** OBTENIDO DESDE EL QUERY METHODS ***************************
     */

    /*@GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> getAvailable(){
        return ResponseEntity.ok(this.pizzaService.getAvailable());
    }*/

    //CONSULTA UTILIZANDO PAGINACION Y ORDENAMIENTO DE CONSULTAS
    @GetMapping("/available")
    //@CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<Page<PizzaEntity>> getAvailable(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8") int elements,
                                                          @RequestParam(defaultValue = "price") String sortBy,
                                                          @RequestParam(defaultValue = "ASC") String sortDirection){
        return ResponseEntity.ok(this.pizzaService.getAvailable(page,elements,sortBy,sortDirection));
    }

    //OBTENER UNA PIZZA MEDIANTE EL NOMBRE
    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name){
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }

    //OBTIENE LAS PIZZAS CON LOS

    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }


    //OBTIENE EL TOP 3 DE PIZZAS QUE ESTES DISPONIBLES Y CUYO PRECIO SEA MENOR IGUAL AL LA VARIABLE
    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapestPizzas(@PathVariable double price){
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));

    }


    /*
     ************************* @Modifying  para CRUD con @Query *****************************
     */
    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDto dto){
        if(this.pizzaService.exists(dto.getPizzaId())){
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
