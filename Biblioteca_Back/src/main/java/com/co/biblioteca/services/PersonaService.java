package com.co.biblioteca.services;

import com.co.biblioteca.exceptions.NotFoundException;
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
     * Metodo encargado de consultar todas las personas de la BD
     * @return
     */
    public List<Persona> listarPersonas() {
        return personaRepo.findAll();
    }

    /**
     * Metodo encargado de consultar una persona en especifico por su ID
     */
    public Persona buscarPersona(Integer id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("La persona que desea consultar no existe"));
    }

    /**
     * Metodo encargado de insertar una nueva persona en la BD
     */
    public Persona guardarPersona(Persona newPersona) {
        return personaRepo.save(newPersona);
    }

    /**
     * Metodo encargado de actualizar la info de una persona en la BD
     */
    public Persona actualizarPersona(Integer id, Persona personaEdit) {
        Persona personaExist = personaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("La persona que desea actualizar no existe"));

        personaExist.setDocumento(personaEdit.getDocumento());
        personaExist.setNombre_persona(personaEdit.getNombre_persona());
        personaExist.setApellido(personaEdit.getApellido());
        personaExist.setTelefono(personaEdit.getTelefono());
        personaExist.setEmail(personaEdit.getEmail());

        return personaRepo.save(personaExist);
    }

    /**
     * Metodo encargado de eliminar una persona de la BD
     */
    public boolean eliminarPersona(Integer id) {
        personaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("La persona que desea eliminar no existe"));

        personaRepo.deleteById(id);
        return true;
    }
}
