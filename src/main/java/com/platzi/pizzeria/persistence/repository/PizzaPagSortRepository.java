package com.platzi.pizzeria.persistence.repository;

import com.platzi.pizzeria.persistence.entity.PizzaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface PizzaPagSortRepository extends ListPagingAndSortingRepository<PizzaEntity,Integer> {

    /*
     *********************************** QUERY METHODS ***************************
     */
    //RETORNAMOS UNA PAGINA DE LA ENTIDAD LA CUAL SE LE PASA COMO PARAMETRO UNA CLASE PAGEABLE0
    Page<PizzaEntity> findAllByAvailableTrue(Pageable pageable);

}
