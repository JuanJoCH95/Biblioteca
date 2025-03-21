package com.co.biblioteca.controller;

import com.co.biblioteca.dto.UsuarioDTO;
import com.co.biblioteca.model.UsuariosDao;
import java.util.ArrayList;
import java.util.List;
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
                usuario.getApellido().isEmpty() || usuario.getTelefono().isEmpty() || usuario.getEmail().isEmpty()) {
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
            if(!usuarioDao.validateUsuario(usuario.getNumDocumento())) {
                usuarioDao.insertUsuario(usuario);
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con el numero de documento " + usuario.getNumDocumento() + " registrado en el sistema", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo encargado de listar los usuarios registrados en el sistema
     * @return 
     */
    public List<UsuarioDTO> listarUsuarios(String numDocumento) {
        List<UsuarioDTO> listaUsuarios = new ArrayList<>();
        
        try {
            listaUsuarios = numDocumento.isEmpty() ? usuarioDao.findAll() : usuarioDao.findSpecific(numDocumento);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return listaUsuarios;
    }
    
    /**
     * Metodo encargado de eliminar usuarios
     * @param numDocumento 
     */
    public void eliminarUsuario(String numDocumento) {
        try {
            usuarioDao.deleteUsuario(numDocumento);
            JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado exitosamente del sistema", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo encargado de editar un usuario
     * @param usuario 
     */
    public void editarUsuario(UsuarioDTO usuario) {
        try {
            usuarioDao.updateUsuario(usuario);
            JOptionPane.showMessageDialog(null, "La información del usuario ha sido actualizada con exito", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo encargado de consultar un usuario especifico
     * @param numDocumento
     * @return 
     */
    public UsuarioDTO consultarUsuario(String numDocumento) {
        UsuarioDTO usuario = null;
        
        try {
            usuario = usuarioDao.findUsuario(numDocumento);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return usuario;
    }
}
