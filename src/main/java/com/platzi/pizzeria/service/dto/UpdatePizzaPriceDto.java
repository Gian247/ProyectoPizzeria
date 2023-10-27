package com.platzi.pizzeria.service.dto;

import lombok.Data;

@Data //AUTOMATICAMENTE LOOMBOK CREA LOS GETTER Y SETTER
public class UpdatePizzaPriceDto {
    //DATOS QUE SE EDITARAN
    private int pizzaId;
    private double newPrice;
}
