package com.co.biblioteca.services;

import com.co.biblioteca.model.Persona;
import com.co.biblioteca.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonaService {

    @Autowired
    PersonaRepository personaRepo;

    /**
     * Metodo encargado de consultar todas las personas en la BD
     * @return
     */
    public List<Persona> listarPersonas() {
        return personaRepo.findAll();
    }
}
