package com.platzi.pizzeria.service;

import com.platzi.pizzeria.persistence.entity.UserEntity;
import com.platzi.pizzeria.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/*
Esta clase implemetara la interfaz  UserDetailService , se encargara
de indicarle a Spring que se van a usar los propios usuario y no los
cargador en memorio sino desde una base de datos
 */
@Service
public class UserSecurityService implements UserDetailsService {

    //Inyectamos la dependencia de consulta a la bd
    private final UserRepository userRepository;
    //HACEMOS LA INYECCION DE DEPENDENCIAS MEDIANTE CONTRUCTOR
    @Autowired//Esto no es obligatorio solo sirve para que el codigo
    //sea mas legible
    public UserSecurityService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //ESTE METODO TOMA UNA PK Y RETORNA UN OPCIONAL
        UserEntity userEntity=this.userRepository.findById(username)
                //SINO LO ENCUENTRA LANZA UNA EXCEPCION
                .orElseThrow(()->new UsernameNotFoundException("User "+username+ "not found"));
        //RETORNA LOS DATOS DEL USUSARIO CON LOS DATOS DE LA DB
        return User.builder()
                .username(userEntity.getUsername())
                //DESDE LA BASE DE DATOS VIENE ENCRYPTADA
                .password(userEntity.getPassword())
                .roles("ADMIN")
                //CARACTERISTICA DE SPRING: SABER SI UN USUARIO ESTA BLOQUEADO
                .accountLocked(userEntity.getLocked())
                //CARACTERISTICA DE SPRING: SABER SI UN USUARIO ESTA DESABILITADO
                .disabled(userEntity.getDisabled())
                .build();
    }
}
