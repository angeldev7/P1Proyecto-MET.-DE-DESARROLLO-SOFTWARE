package Controlador;

import Modelo.Constantes;
import Modelo.Usuario;
import Modelo.UsuarioDTO;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuariosJSON {
    private static GestorUsuariosJSON instancia;
    private Gson gson;
    private List<UsuarioDTO> usuarios;
    
    private GestorUsuariosJSON() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        cargarUsuarios();
    }
    
    /**
     * Obtiene la instancia única del gestor (Singleton)
     */
    public static GestorUsuariosJSON getInstancia() {
        if (instancia == null) {
            instancia = new GestorUsuariosJSON();
        }
        return instancia;
    }
    
    /**
     * Carga los usuarios desde el archivo JSON
     * Si no existe, crea usuarios por defecto
     */
    private void cargarUsuarios() {
        File archivo = new File(Constantes.ARCHIVO_USUARIOS);
        
        if (archivo.exists()) {
            try {
                String json = new String(Files.readAllBytes(Paths.get(Constantes.ARCHIVO_USUARIOS)));
                Type listType = new TypeToken<ArrayList<UsuarioDTO>>(){}.getType();
                usuarios = gson.fromJson(json, listType);
                
                if (usuarios == null) {
                    usuarios = new ArrayList<>();
                }
                
                System.out.println("✅ Usuarios cargados desde JSON: " + usuarios.size());
                
            } catch (IOException e) {
                System.err.println("❌ Error al leer usuarios.json: " + e.getMessage());
                crearUsuariosPorDefecto();
            }
        } else {
            System.out.println("⚠️ Archivo usuarios.json no existe. Creando usuarios por defecto...");
            crearUsuariosPorDefecto();
        }
    }
    
    /**
     * Crea usuarios por defecto y guarda en JSON
     */
    private void crearUsuariosPorDefecto() {
        usuarios = new ArrayList<>();
        
        // Usuario Jefatura (admin / admin123)
        Usuario jefatura = new Usuario(1, "admin", "admin123", 
            "Diego Montesdeoca", "Jefatura Financiera");
        usuarios.add(UsuarioDTO.fromUsuario(jefatura));
        
        // Usuario Asistente (asistente / asistente123)
        Usuario asistente = new Usuario(2, "asistente", "asistente123", 
            "María López", "Asistente Contable");
        usuarios.add(UsuarioDTO.fromUsuario(asistente));
        
        // Usuario Asistente adicional (carlos / carlos123)
        Usuario asistente2 = new Usuario(3, "carlos", "carlos123", 
            "Carlos Rodríguez", "Asistente Contable");
        usuarios.add(UsuarioDTO.fromUsuario(asistente2));
        
        guardarUsuarios();
        System.out.println("✅ Usuarios por defecto creados");
    }
    
    /**
     * Guarda la lista de usuarios en el archivo JSON
     */
    private void guardarUsuarios() {
        try {
            String json = gson.toJson(usuarios);
            Files.write(Paths.get(Constantes.ARCHIVO_USUARIOS), json.getBytes());
            System.out.println("💾 Usuarios guardados en JSON");
        } catch (IOException e) {
            System.err.println("❌ Error al guardar usuarios.json: " + e.getMessage());
        }
    }
    
    /**
     * Valida las credenciales de un usuario
     * @return Usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        for (UsuarioDTO dto : usuarios) {
            if (dto.getNombreUsuario().equals(nombreUsuario)) {
                Usuario usuario = dto.toUsuario();
                if (usuario.autenticar(contrasena)) {
                    return usuario;
                }
            }
        }
        return null;
    }
    
    /**
     * Agrega un nuevo usuario al sistema
     * @return true si se agregó correctamente, false si ya existe
     */
    public boolean agregarUsuario(String nombreUsuario, String contrasena, 
                                  String nombreCompleto, String rol) {
        // Verificar si el usuario ya existe
        for (UsuarioDTO dto : usuarios) {
            if (dto.getNombreUsuario().equals(nombreUsuario)) {
                return false; // Usuario ya existe
            }
        }
        
        // Generar nuevo ID
        int nuevoId = usuarios.stream()
            .mapToInt(UsuarioDTO::getIdUsuario)
            .max()
            .orElse(0) + 1;
        
        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(nuevoId, nombreUsuario, contrasena, 
                                          nombreCompleto, rol);
        usuarios.add(UsuarioDTO.fromUsuario(nuevoUsuario));
        
        // Guardar en JSON
        guardarUsuarios();
        
        System.out.println("✅ Usuario agregado: " + nombreUsuario + " (" + rol + ")");
        return true;
    }
    
    /**
     * Obtiene todos los usuarios del sistema
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        for (UsuarioDTO dto : usuarios) {
            listaUsuarios.add(dto.toUsuario());
        }
        return listaUsuarios;
    }
    
    /**
     * Obtiene usuarios por rol
     */
    public List<Usuario> obtenerUsuariosPorRol(String rol) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        for (UsuarioDTO dto : usuarios) {
            if (dto.getRol().equals(rol)) {
                listaUsuarios.add(dto.toUsuario());
            }
        }
        return listaUsuarios;
    }
    
    /**
     * Actualiza un usuario existente
     */
    public boolean actualizarUsuario(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == usuario.getIdUsuario()) {
                usuarios.set(i, UsuarioDTO.fromUsuario(usuario));
                guardarUsuarios();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Elimina un usuario del sistema
     */
    public boolean eliminarUsuario(int idUsuario) {
        boolean eliminado = usuarios.removeIf(dto -> dto.getIdUsuario() == idUsuario);
        if (eliminado) {
            guardarUsuarios();
            System.out.println("🗑️ Usuario eliminado: ID " + idUsuario);
        }
        return eliminado;
    }
    
    /**
     * Obtiene la cantidad total de usuarios
     */
    public int contarUsuarios() {
        return usuarios.size();
    }
    
    /**
     * Verifica si existe un usuario con el nombre dado
     */
    public boolean existeUsuario(String nombreUsuario) {
        return usuarios.stream()
            .anyMatch(dto -> dto.getNombreUsuario().equals(nombreUsuario));
    }
}
