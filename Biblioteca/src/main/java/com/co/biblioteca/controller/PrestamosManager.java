package com.co.biblioteca.controller;

import com.co.biblioteca.dto.LibroDTO;
import com.co.biblioteca.dto.PrestamoDTO;
import com.co.biblioteca.dto.UsuarioDTO;
import com.co.biblioteca.model.LibrosDao;
import com.co.biblioteca.model.PrestamosDao;
import com.co.biblioteca.model.SancionesDao;
import com.co.biblioteca.model.UsuariosDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

/**
 * Clase que contiene la logica de negocio necesaria para gestionar el modulo de prestamos
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class PrestamosManager {
    
    LibrosDao libroDao = new LibrosDao();
    UsuariosDao usuarioDao = new UsuariosDao();
    PrestamosDao prestamoDao = new PrestamosDao();
    SancionesDao sancionDao = new SancionesDao();
    
    /**
     * Metodo encargado de validar los campos del formulario
     * @param usuario
     * @param libro
     * @return 
     */
    private Boolean validarDatos(String usuario, String libro) {
        try {
            if(usuario.isEmpty() || libro.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                return false;
            } else {
                if(!usuarioDao.validateUsuario(usuario)) {
                    JOptionPane.showMessageDialog(null, "No se encontró un usuario en el sistema asociado al documento " + usuario, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                if(sancionDao.findSancionByUsuario(usuario) != null) {
                    JOptionPane.showMessageDialog(null, "El usuario  " + usuario + " se encuentra sancionado, por lo tanto no puede realizar prestamos", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                if(libroDao.findLibro(Integer.parseInt(libro)) == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró un libro en el sistema asociado al ID " + libro, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                if(libroDao.findDisponibles(Integer.parseInt(libro)) == 0) {
                    JOptionPane.showMessageDialog(null, "El libro seleccionado no se encuentra disponible en el momento" + usuario, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Metodo encargado de gestionar el prestamo de libros
     * @param usuario
     * @param libro 
     */
    public void realizarPrestamo(String usuario, String libro) {
        if(validarDatos(usuario, libro)) {
            try {
                PrestamoDTO prestamo = new PrestamoDTO();
                UsuarioDTO usuarioDto = usuarioDao.findUsuario(usuario);
                LibroDTO libroDto = libroDao.findLibro(Integer.parseInt(libro));
                
                prestamo.setIdPersona(usuarioDto.getIdUsuario());
                prestamo.setIdLibro(Integer.parseInt(libro));
                prestamo.setFechaInicio(getFechaActual());
                prestamo.setFechaFin(getFechaVencimiento(prestamo.getFechaInicio()));
                prestamo.setEstado("Activo");
                prestamoDao.insertPrestamo(prestamo);
                
                libroDto.setDisponibles(libroDto.getDisponibles() - 1);
                libroDao.updateLibro(libroDto);
                
                JOptionPane.showMessageDialog(null, "El prestamo se ha realizado exitosamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                notificarEmail(usuarioDto, libroDto);
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
                JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado en el sistema", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Metodo encargado de obtener la fecha actual y darle formato
     * @return 
     */
    private String getFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        return formato.format(fechaActual);
    }
    
    /**
     * Metodo encargado de hacer la suma y obtener la fecha de vencimiento del prestamo
     * @param fecha
     * @return
     * @throws ParseException 
     */
    private String getFechaVencimiento(String fecha) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDate = formato.parse(fecha);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaDate); 
        calendar.add(Calendar.DAY_OF_YEAR, 20);
        return formato.format(calendar.getTime());
    }
    
    /**
     * Metodo encargado de notificar por email al usuario cuando realiza un prestamo
     * @param usuarioDto
     * @param libroDto 
     */
    private void notificarEmail(UsuarioDTO usuarioDto, LibroDTO libroDto) {
        try {
            String email = "juancardonah1017@gmail.com";
            String password = "qqnnkhaujrhbbcxy";
            String emailTo = usuarioDto.getEmail();
            String asunto = "Prestamo de libros - MyLib";
            String mensaje = usuarioDto.getNombre() + " " + usuarioDto.getApellido()
                + ", usted ha realizado el préstamo de un libro a través del sistema MyLib. <br><br>"
                + "<b>Nombre del libro:</b> " + libroDto.getNombreLibro() + "<br>" + "<b>Fecha del préstamo:</b> " + getFechaActual() + "<br>"
                + "<b>Fecha de devolución:</b> " + getFechaVencimiento(getFechaActual()) + "<br><br>"
                + "Recuerde realizar la devolución o renovación antes de la fecha establecida, evite sanciones.";
        
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", email);
            prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            prop.setProperty("mail.smtp.auth", "true");
            
            Session mSession;
            mSession = Session.getDefaultInstance(prop);
            
            MimeMessage mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(email));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(asunto);
            mCorreo.setText(mensaje, "ISO-8859-1", "html");
            
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(email, password);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }
}
