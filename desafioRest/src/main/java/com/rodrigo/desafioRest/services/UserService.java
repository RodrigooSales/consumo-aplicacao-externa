package com.rodrigo.desafioRest.services;

import org.apache.catalina.User;
import org.springframework.web.reactive.function.client.WebClient;

import com.rodrigo.desafioRest.entities.Office;

public class UserService {
    public User findById(String id){

        String url = "http://localhost:3000";
        String uri = "/usuarios/{id}";

        User cargo = WebClient
                .create(url)
                .get()
                .uri(uri, id)
                .retrieve()
                .bodyToMono(User.class).block();

        return cargo;

    }
}
