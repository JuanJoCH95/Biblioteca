package com.co.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_persona;

    private String documento;
    private String nombre_persona;
    private String apellido;
    private String email;
    private String telefono;

    public Persona() {
        super();
    }

    public Persona(Integer id_persona, String documento, String nombre_persona, String apellido, String email, String telefono) {
        super();
        this.id_persona = id_persona;
        this.documento = documento;
        this.nombre_persona = nombre_persona;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre_persona() {
        return nombre_persona;
    }

    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
