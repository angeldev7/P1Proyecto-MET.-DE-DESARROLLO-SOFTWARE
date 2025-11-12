package Modelo;

/**
 * Data Transfer Object para Bitácora
 * Usado para serialización JSON
 * Fase: Implementación - Metodología Cascada
 * 
 * @author Sistema Contable
 * @version 1.0.0
 */
public class BitacoraDTO {
    private int idRegistro;
    private int idUsuario;
    private String fechaHora; // LocalDateTime como String para JSON
    private String accion;
    private String descripcion;
    
    // Constructor vacío para Gson
    public BitacoraDTO() {}
    
    // Constructor completo
    public BitacoraDTO(int idRegistro, int idUsuario, String fechaHora, 
                      String accion, String descripcion) {
        this.idRegistro = idRegistro;
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
        this.accion = accion;
        this.descripcion = descripcion;
    }
    
    /**
     * Convierte una Bitácora a BitacoraDTO
     */
    public static BitacoraDTO fromBitacora(Bitacora bitacora) {
        return new BitacoraDTO(
            bitacora.getIdRegistro(),
            bitacora.getUsuario().getIdUsuario(),
            bitacora.getFechaHora().toString(),
            bitacora.getAccion(),
            bitacora.getDescripcion()
        );
    }
    
    /**
     * Convierte el DTO a Bitácora
     */
    public Bitacora toBitacora(Usuario usuario) {
        Bitacora bitacora = new Bitacora(
            idRegistro,
            usuario,
            accion,
            descripcion
        );
        return bitacora;
    }
    
    // Getters y Setters
    public int getIdRegistro() { return idRegistro; }
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
