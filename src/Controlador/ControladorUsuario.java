package Controlador;

import Modelo.*;
import java.util.List;

/**
 * Controlador para gestión de usuarios y autenticación
 * Ahora integrado con GestorUsuariosJSON para persistencia
 * Fase: Implementación - Metodología Cascada
 */
public class ControladorUsuario {
    private static ControladorUsuario instancia;
    private GestorUsuariosJSON gestorJSON;
    private Usuario usuarioActual;
    private ControladorBitacora controladorBitacora;
    
    private ControladorUsuario() {
        gestorJSON = GestorUsuariosJSON.getInstancia();
        controladorBitacora = ControladorBitacora.getInstancia();
    }
    
    public static ControladorUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorUsuario();
        }
        return instancia;
    }
    
    /**
     * Autentica un usuario en el sistema usando JSON
     */
    public boolean autenticar(String nombreUsuario, String contrasena) {
        Usuario usuario = gestorJSON.validarCredenciales(nombreUsuario, contrasena);
        
        if (usuario != null) {
            usuarioActual = usuario;
            controladorBitacora.registrar(usuario, "LOGIN", 
                "Inicio de sesión exitoso");
            return true;
        }
        return false;
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    public void cerrarSesion() {
        if (usuarioActual != null) {
            controladorBitacora.registrar(usuarioActual, "LOGOUT", 
                "Cierre de sesión");
            usuarioActual = null;
        }
    }
    
    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean hayUsuarioAutenticado() {
        return usuarioActual != null;
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Agrega un nuevo usuario al sistema
     * @return true si se agregó correctamente, false si ya existe
     */
    public boolean agregarUsuario(String nombreUsuario, String contrasena, 
                                  String nombreCompleto, String rol) {
        boolean agregado = gestorJSON.agregarUsuario(nombreUsuario, contrasena, 
                                                     nombreCompleto, rol);
        if (agregado && usuarioActual != null) {
            controladorBitacora.registrar(usuarioActual, "CREAR_USUARIO", 
                "Nuevo usuario: " + nombreUsuario + " - Rol: " + rol);
        }
        return agregado;
    }
    
    /**
     * Obtiene todos los usuarios del sistema
     */
    public List<Usuario> getUsuarios() {
        return gestorJSON.obtenerTodosLosUsuarios();
    }
    
    /**
     * Obtiene usuarios por rol
     */
    public List<Usuario> getUsuariosPorRol(String rol) {
        return gestorJSON.obtenerUsuariosPorRol(rol);
    }
    
    /**
     * Verifica si existe un usuario
     */
    public boolean existeUsuario(String nombreUsuario) {
        return gestorJSON.existeUsuario(nombreUsuario);
    }
}
