package com.co.biblioteca.controller;

import com.co.biblioteca.model.Persona;
import com.co.biblioteca.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/lista/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Integer id) {
        Persona persona = personaService.buscarPersona(id);
        return ResponseEntity.ok(persona);
    }

    @PostMapping("/nuevo")
    public Persona guardarPersona(@RequestBody Persona newPersona) {
        return personaService.guardarPersona(newPersona);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Integer id, @RequestBody Persona editPersona) {
        Persona persona = personaService.actualizarPersona(id, editPersona);
        return ResponseEntity.ok(persona);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarPersona(@PathVariable Integer id) {
        boolean respuesta = personaService.eliminarPersona(id);
        return ResponseEntity.ok(respuesta);
    }
}
