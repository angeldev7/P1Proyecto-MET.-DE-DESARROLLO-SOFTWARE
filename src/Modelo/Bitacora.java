package Modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa un registro en la bitácora de auditoría
 * Fase: Implementación - Metodología Cascada
 */
public class Bitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int idRegistro;
    private Usuario usuario;
    private LocalDateTime fechaHora;
    private String accion;
    private String descripcion;
    
    public Bitacora(int idRegistro, Usuario usuario, String accion, String descripcion) {
        this.idRegistro = idRegistro;
        this.usuario = usuario;
        this.fechaHora = LocalDateTime.now();
        this.accion = accion;
        this.descripcion = descripcion;
    }
    
    /**
     * Registra la acción en el sistema
     */
    public void registrar() {
        System.out.println("[BITÁCORA] " + this.toString());
    }
    
    // Getters
    public int getIdRegistro() { return idRegistro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getAccion() { return accion; }
    public String getDescripcion() { return descripcion; }
    
    public String getFechaHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHora.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s", 
            getFechaHoraFormateada(), 
            usuario.getNombreUsuario(), 
            accion, 
            descripcion);
    }
}
