package com.co.biblioteca.model;

import com.co.biblioteca.db.Conexion;
import com.co.biblioteca.dto.PrestamoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que contiene todos los metodos para realizar las transacciones de prestamos en la BD del sistema
 * @author Juan Jose Cardona Henao
 * @version 1.0
 */
public class PrestamosDao {
    
    private static final String insertPrestamo = "INSERT INTO prestamos (id_persona, id_libro, fecha_inicio, estado) VALUES (?, ?, STR_TO_DATE(?, '%d-%m-%Y'), ?)";
    
    /**
     * Metodo encargado de insertar un nuevo prestamo en la BD
     * @param prestamo
     * @throws SQLException 
     */
    public void insertPrestamo(PrestamoDTO prestamo) throws SQLException {
	Connection conn = null;
        PreparedStatement preparedStm = null;
        int index = 1;
        
        try {
            conn = Conexion.conectarBD();
            preparedStm = conn.prepareStatement(insertPrestamo);
            preparedStm.setInt(index++, prestamo.getIdPersona());
            preparedStm.setInt(index++, prestamo.getIdLibro());
            preparedStm.setString(index++, prestamo.getFechaInicio());
            preparedStm.setString(index++, prestamo.getEstado());
            preparedStm.executeUpdate();
	} finally {
            Conexion.closeConnection(preparedStm, conn);
	}
    }
}
