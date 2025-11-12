package Controlador;

import Modelo.Bitacora;
import Modelo.BitacoraDTO;
import Modelo.TipoRol;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestión de la bitácora de auditoría
 * Ahora con persistencia en JSON
 * Fase: Implementación - Metodología Cascada
 */
public class ControladorBitacora {
    private static ControladorBitacora instancia;
    private List<Bitacora> registros;
    private int proximoId;
    private GestorBitacoraJSON gestorJSON;
    
    private ControladorBitacora() {
        gestorJSON = GestorBitacoraJSON.getInstancia();
        registros = new ArrayList<>();
        proximoId = gestorJSON.obtenerProximoId();
    }
    
    public static ControladorBitacora getInstancia() {
        if (instancia == null) {
            instancia = new ControladorBitacora();
        }
        return instancia;
    }
    
    /**
     * Registra una acción en la bitácora
     */
    public void registrar(Usuario usuario, String accion, String descripcion) {
        Bitacora registro = new Bitacora(proximoId++, usuario, accion, descripcion);
        registros.add(registro);
        gestorJSON.registrar(registro); // Persistir en JSON
        registro.registrar(); // Imprime en consola
    }
    
    /**
     * Obtiene todos los registros de la bitácora
     */
    public List<Bitacora> getRegistros() {
        // Cargar desde JSON y convertir a objetos Bitacora
        List<BitacoraDTO> dtos = gestorJSON.obtenerTodosRegistrosDTO();
        registros.clear();
        
        for (BitacoraDTO dto : dtos) {
            Usuario usuario = buscarUsuarioPorId(dto.getIdUsuario());
            if (usuario != null) {
                registros.add(dto.toBitacora(usuario));
            }
        }
        
        return new ArrayList<>(registros);
    }
    
    /**
     * Busca un usuario por ID
     */
    private Usuario buscarUsuarioPorId(int idUsuario) {
        ControladorUsuario controladorUsuario = ControladorUsuario.getInstancia();
        List<Usuario> usuarios = controladorUsuario.getUsuarios();
        
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                return u;
            }
        }
        
        // Usuario genérico si no se encuentra
        return new Usuario(idUsuario, "desconocido", "", "Usuario Desconocido", TipoRol.ASISTENTE_CONTABLE);
    }
    
    /**
     * Obtiene los últimos N registros
     */
    public List<Bitacora> getUltimosRegistros(int cantidad) {
        List<BitacoraDTO> dtos = gestorJSON.obtenerUltimosRegistrosDTO(cantidad);
        List<Bitacora> resultado = new ArrayList<>();
        
        for (BitacoraDTO dto : dtos) {
            Usuario usuario = buscarUsuarioPorId(dto.getIdUsuario());
            if (usuario != null) {
                resultado.add(dto.toBitacora(usuario));
            }
        }
        
        return resultado;
    }
    
    /**
     * Filtra registros por usuario
     */
    public List<Bitacora> getRegistrosPorUsuario(Usuario usuario) {
        List<BitacoraDTO> dtos = gestorJSON.obtenerRegistrosPorUsuarioDTO(usuario.getIdUsuario());
        List<Bitacora> resultado = new ArrayList<>();
        
        for (BitacoraDTO dto : dtos) {
            resultado.add(dto.toBitacora(usuario));
        }
        
        return resultado;
    }
}
