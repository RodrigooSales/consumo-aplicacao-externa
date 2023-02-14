package com.rodrigo.desafioRest.services;

import com.rodrigo.desafioRest.entities.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {

    private RestTemplate restTemplate = new RestTemplate();

    private final String BASE_URL = "http://localhost:3000/usuarios/";

    public Usuario buscarUsuarioPorId(String id) {
    	String url = BASE_URL + id;
        return restTemplate.getForObject(url, Usuario.class);
    }

    public void atualizarUsuario(Usuario usuario) {
        String url = BASE_URL + usuario.getId();
        restTemplate.put(url, usuario);
    }
}
