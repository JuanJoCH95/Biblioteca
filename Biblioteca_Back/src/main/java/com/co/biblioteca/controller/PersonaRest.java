package com.co.biblioteca.controller;

import com.co.biblioteca.model.Persona;
import com.co.biblioteca.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@RequestMapping("/apiRest/personas")
public class PersonaRest {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/lista")
    public List<Persona> listarPersonas() {
        return personaService.listarPersonas();
    }
}
