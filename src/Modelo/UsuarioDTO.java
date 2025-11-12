package Modelo;

/**
 * Data Transfer Object para Usuario
 * Usado para serialización JSON
 * Fase: Implementación - Metodología Cascada
 * 
 * @author Sistema Contable
 * @version 1.0.0
 */
public class UsuarioDTO {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;  // Ahora en texto plano
    private String nombreCompleto;
    private String rol;
    
    // Constructor vacío para Gson
    public UsuarioDTO() {}
    
    // Constructor completo
    public UsuarioDTO(int idUsuario, String nombreUsuario, String contrasena, 
                      String nombreCompleto, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }
    
    // Conversión desde Usuario
    public static UsuarioDTO fromUsuario(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getIdUsuario(),
            usuario.getNombreUsuario(),
            usuario.getContrasena(),  // Contraseña en texto plano
            usuario.getNombreCompleto(),
            usuario.getRol()
        );
    }
    
    // Conversión a Usuario
    public Usuario toUsuario() {
        return new Usuario(idUsuario, nombreUsuario, contrasena, nombreCompleto, rol, true);
    }
    
    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
