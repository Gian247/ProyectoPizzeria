package com.platzi.pizzeria.service;

import com.platzi.pizzeria.persistence.entity.PizzaEntity;
import com.platzi.pizzeria.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizzeria.persistence.repository.PizzaRepository;
import com.platzi.pizzeria.service.dto.UpdatePizzaPriceDto;
import com.platzi.pizzeria.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {
    /*----------------------------------------------------------------------
               LIBRERIA CRUD REPOSITORY
    -----------------------------------------------------------------------
     */

    private final PizzaRepository pizzaRepository;



    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository,PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository=pizzaPagSortRepository;
    }

    //SERVICIO QUE OBTIENE TODOS LOS DATOS DEL REPOSITORIO APUNTADO HACIA LA BASE DE DATOS

    /*public List<PizzaEntity> getAll(){
        return this.pizzaRepository.findAll();
    }*/


    //MODIFICANDO PARA QUE LA RESPUESTA SEA PAGINADA
    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest= PageRequest.of(page,elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }

    //SERVICIO QUE OBTIENE A LAS PIZZAS FILTRADAS POR SU ID
    public PizzaEntity get(int idPizza){
        //ESTE METODO RETORNA UN OPTIONAL POR LO QUE SE TIENE QUE ESPECIFICAR EL VALOR DE RETORNO
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }

    //METODO COMPROBAR SI ALGO EXISTE MEDIANTE SU ID
    public boolean exists(int idPizza){
        //METODO DE CRUDREPOSITORY QUE PERMITE DETERMINAR SI UN OBJETO EXISTE O NO
        return this.pizzaRepository.existsById(idPizza);
    }

    public void delete(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }


    /*
    ******************* OBTENIDO DESDE QUERY METHODS *****************
     */
    //OBTENER PIZZAS DISPONIBLES MARCADOS CON TRUE
    /*public  List<PizzaEntity> getAvailable(){
        //CUANTAS PIZZAS VEGANAS HAY
        System.out.println(this.pizzaRepository.countByVeganTrue());

        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }*/


    //UTILIZANDO PAGINACION Y ORDENAMIENTO DE CONSULTAS
    public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDirection){


        //PARA PERSONALIZAR EL SENTIDO DEL ORDENAMIENTOS
        //(DIRECCIÓN DE ORDENAMIENTO, COLUMNA DE ORDENAMIENTO)
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        //PREPARAMOS NUESTRO PAGEABLE CON LOS 3 DATOS NECESARIOS
        // (PAGINA, CANTIDAD DE ELEMENTOS POR PAGINA, ORDENAMIENTO)
        Pageable pageRequest=PageRequest.of(page, elements, sort);

        return this.pizzaPagSortRepository.findAllByAvailableTrue(pageRequest);
    }



    //BUSCA POR NOMBRE RECOPILA MAYUSCULAS Y MINUSCULAS
    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()->new RuntimeException("La pizza no existe"));
    }

    public  List<PizzaEntity> getWith(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public  List<PizzaEntity> getWithout(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    //OBTIENE EL TOP 3 DE PIZZAS QUE ESTES DISPONIBLES Y CUYO PRECIO SEA MENOR IGUAL AL LA VARIABLE
    public List<PizzaEntity> getCheapest(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    /*
     ************************* @Modifying  para CRUD con @Query *****************************
     */

    @Transactional(noRollbackFor = EmailApiException.class, propagation = Propagation.REQUIRED)  //GARANTIZA TENER LAS 4 CARACTERISTICAS ACID
    //ES IMPORTANTE QUE SIEMPRE QUE SE TENGAN 2 O MAS LLAMADOS DESDE UN MISMO METODO A LA DB SI O SI SE USE
    //GRANTIZA QUE LAS TRANSACCIONES SE REALIZEN DE FORMA ESCALONADA
    public void updatePrice(UpdatePizzaPriceDto dto){
        this.pizzaRepository.updatePrice(dto);
        this.sendEmail();
    }


    /*
    *************************** TRANSACTIONAL **************************
     */

    private void sendEmail(){
        throw new EmailApiException();
    }






    /*
    JDBCTEPLATE
    Nos permite crear consultas SQL desde JAVA convertir el resultado en clases JAVA
     */
    /*-----------------------------------------------------------------------------------------------------
    ********************************************************************************************************
    ---------------------------------------------------------------------------------------------------------
    private final JdbcTemplate jdbcTemplate; //Llamando a la libreria JDBCTEMPLATE


    @Autowired//Inyeccion de dependencias
    public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //METODO QUE PERMITE CONSULTAR TODAS LAS PIZZAS
    public List<PizzaEntity> getAll(){
        //SE HACE UN LLAMADO AL JDBCTEMPLATE ( CONSULTA SQL , LA CLASE A DONDE VA A SER ALAMACENDAD LA CONSULTA)
        return this.jdbcTemplate.query("SELECT * FROM pizza WHERE available=0", new BeanPropertyRowMapper<>(PizzaEntity.class));
    }
    ----------------------------------------------------------------------------------------------------------
    ***************************************************************************************************************
    ---------------------------------------------------------------------------------------------------------
    */

}
