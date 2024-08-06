package com.co.biblioteca.controller;

import com.co.biblioteca.dto.UsuarioDTO;
import com.co.biblioteca.model.UsuariosDao;
import javax.swing.JOptionPane;

/**
 * Clase que contiene la logica de negocio necesaria para gestionar el modulo de usuarios
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class UsuariosManager {
    
    UsuariosDao usuarioDao = new UsuariosDao();
    
    /**
     * Metodo encargado de validar todos los campos
     * @param usuario
     * @return 
     */
    public boolean validarDatos(UsuarioDTO usuario) {
        boolean isValid = true;
        
        if(usuario.getIdTipoDocumento() == 0 || usuario.getNumDocumento().isEmpty() || usuario.getNombre().isEmpty() ||
                usuario.getApellido().isEmpty() || usuario.getTelefono().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }
    
    /**
     * Metodo encargado de registrar usuarios
     * @param usuario 
     */
    public void registrarUsuario(UsuarioDTO usuario) {
        try {
            if(!usuarioDao.validateUsuario(usuario.getIdTipoDocumento(), usuario.getNumDocumento())) {
                usuarioDao.insertUsuario(usuario);
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con el mismo numero de documento registrado en el sistema", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
