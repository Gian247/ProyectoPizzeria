package com.platzi.pizzeria.persistence.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass //Le indica a sprin que es un superclase que puede ser heredada desde otras clases
public class AuditableEntity {


    @Column(name = "created_date")
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime modifiedDate;
}
