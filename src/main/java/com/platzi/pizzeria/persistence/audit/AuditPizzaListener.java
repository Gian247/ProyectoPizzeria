package com.platzi.pizzeria.persistence.audit;

import com.platzi.pizzeria.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

/*
VA A CONTENER UNA SERIE DE METODOS QUE SEEJECUTARAN AUTOMATICAMANETE CUANDO SE ELIMINE ACTUALIZAE O  CREE UNA PIZZA
 */
public class AuditPizzaListener {
    private PizzaEntity currentValue;

    @PostLoad//SE EJECUTA DESPUES QUE SE CARGUE UN SELECT A UN ENTITY
    public void postLoad(PizzaEntity entity){
        System.out.println("POST LOAD");
        //TOMA EL VALOR DEL ENTITY QUE SE ACABA DE CARGAR
        this.currentValue= SerializationUtils.clone(entity);

    }
    @PostPersist//Debe ser aplicada a un metodo que no retorne nada y que sea publico, EJECUTA DESPUES DE UN INSERCCION
    @PostUpdate//EJECUTA EL METODO DESPUES DE UNA ACTUALIZACION
    public void onPostPersist(PizzaEntity entity){//RECIBE UN OBJETO DE TIPO ENTITY
        System.out.println("POST PERSIST OR UPDATE");
        System.out.println("VALOR ANTERIOR: "+this.currentValue);
        System.out.println("NUEVO VALOR: "+entity.toString());
    }

    @PreRemove//EJECUTA EL METODO ANTES DE QUE SE ELIMINE UN DATO
    public void onPreDelete(PizzaEntity entity){
        System.out.println(entity.toString());

    }
}
