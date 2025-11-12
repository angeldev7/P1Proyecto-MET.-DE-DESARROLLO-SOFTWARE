package Controlador;

import Modelo.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de persistencia de transacciones en JSON
 * Maneja facturas y gastos
 * Fase: Implementación - Metodología Cascada
 * 
 * @author Sistema Contable
 * @version 1.0.0
 */
public class GestorTransaccionesJSON {
    private static GestorTransaccionesJSON instancia;
    private static final String ARCHIVO_TRANSACCIONES = "transacciones.json";
    private Gson gson;
    private List<TransaccionDTO> transacciones;
    private ControladorUsuario controladorUsuario;
    
    private GestorTransaccionesJSON() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        controladorUsuario = ControladorUsuario.getInstancia();
        cargarTransacciones();
    }
    
    /**
     * Obtiene la instancia única del gestor (Singleton)
     */
    public static GestorTransaccionesJSON getInstancia() {
        if (instancia == null) {
            instancia = new GestorTransaccionesJSON();
        }
        return instancia;
    }
    
    /**
     * Carga las transacciones desde el archivo JSON
     */
    private void cargarTransacciones() {
        File archivo = new File(ARCHIVO_TRANSACCIONES);
        
        if (archivo.exists()) {
            try {
                String json = new String(Files.readAllBytes(Paths.get(ARCHIVO_TRANSACCIONES)));
                Type listType = new TypeToken<ArrayList<TransaccionDTO>>(){}.getType();
                transacciones = gson.fromJson(json, listType);
                
                if (transacciones == null) {
                    transacciones = new ArrayList<>();
                }
                
                System.out.println("✅ Transacciones cargadas desde JSON: " + transacciones.size());
                
            } catch (IOException e) {
                System.err.println("❌ Error al leer transacciones.json: " + e.getMessage());
                transacciones = new ArrayList<>();
            } catch (JsonSyntaxException e) {
                System.err.println("❌ Error de formato en transacciones.json: " + e.getMessage());
                transacciones = new ArrayList<>();
            }
        } else {
            System.out.println("⚠️ Archivo transacciones.json no existe. Iniciando lista vacía.");
            transacciones = new ArrayList<>();
        }
    }
    
    /**
     * Guarda la lista de transacciones en el archivo JSON
     */
    private void guardarTransacciones() {
        try {
            String json = gson.toJson(transacciones);
            Files.write(Paths.get(ARCHIVO_TRANSACCIONES), json.getBytes());
            System.out.println("💾 Transacciones guardadas en JSON (" + transacciones.size() + " registros)");
        } catch (IOException e) {
            System.err.println("❌ Error al guardar transacciones.json: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Agrega una nueva factura
     */
    public boolean agregarFactura(Factura factura) {
        TransaccionDTO dto = TransaccionDTO.fromFactura(factura);
        transacciones.add(dto);
        guardarTransacciones();
        System.out.println("✅ Factura agregada: " + factura.getNumeroFactura());
        return true;
    }
    
    /**
     * Agrega un nuevo gasto
     */
    public boolean agregarGasto(Gasto gasto) {
        TransaccionDTO dto = TransaccionDTO.fromGasto(gasto);
        transacciones.add(dto);
        guardarTransacciones();
        System.out.println("✅ Gasto agregado: " + gasto.getNumeroComprobante());
        return true;
    }
    
    /**
     * Actualiza el estado de una transacción
     */
    public boolean actualizarEstado(int idTransaccion, String nuevoEstado) {
        for (TransaccionDTO dto : transacciones) {
            if (dto.getIdTransaccion() == idTransaccion) {
                dto.setEstado(nuevoEstado);
                guardarTransacciones();
                System.out.println("✅ Estado actualizado para transacción #" + idTransaccion + ": " + nuevoEstado);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Elimina una transacción (marca como eliminado)
     */
    public boolean eliminarTransaccion(int idTransaccion) {
        return actualizarEstado(idTransaccion, EstadoTransaccion.ELIMINADO);
    }
    
    /**
     * Obtiene todas las transacciones
     */
    public List<Transaccion> obtenerTodasTransacciones() {
        List<Transaccion> lista = new ArrayList<>();
        
        for (TransaccionDTO dto : transacciones) {
            try {
                // Buscar el usuario por ID
                Usuario usuario = buscarUsuarioPorId(dto.getIdUsuarioRegistro());
                if (usuario != null) {
                    Transaccion transaccion = dto.toTransaccion(usuario);
                    // Restaurar el estado guardado
                    transaccion.setEstado(dto.getEstado());
                    lista.add(transaccion);
                }
            } catch (Exception e) {
                System.err.println("⚠️ Error al convertir transacción #" + dto.getIdTransaccion() + ": " + e.getMessage());
            }
        }
        
        return lista;
    }
    
    /**
     * Obtiene transacciones activas (no eliminadas)
     */
    public List<Transaccion> obtenerTransaccionesActivas() {
        List<Transaccion> todas = obtenerTodasTransacciones();
        List<Transaccion> activas = new ArrayList<>();
        
        for (Transaccion t : todas) {
            if (!t.getEstado().equals(EstadoTransaccion.ELIMINADO)) {
                activas.add(t);
            }
        }
        
        return activas;
    }
    
    /**
     * Obtiene el próximo ID disponible
     */
    public int obtenerProximoId() {
        int maxId = 0;
        for (TransaccionDTO dto : transacciones) {
            if (dto.getIdTransaccion() > maxId) {
                maxId = dto.getIdTransaccion();
            }
        }
        return maxId + 1;
    }
    
    /**
     * Busca un usuario por ID
     */
    private Usuario buscarUsuarioPorId(int idUsuario) {
        List<Usuario> usuarios = controladorUsuario.getUsuarios();
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                return u;
            }
        }
        
        // Si no se encuentra, retornar usuario genérico
        return new Usuario(idUsuario, "desconocido", "", "Usuario Desconocido", TipoRol.ASISTENTE_CONTABLE);
    }
    
    /**
     * Genera respaldo de transacciones
     */
    public boolean generarRespaldo(int anio) {
        try {
            String nombreRespaldo = "transacciones_respaldo_" + anio + ".json";
            
            // Filtrar transacciones del año
            List<TransaccionDTO> transaccionesAnio = new ArrayList<>();
            for (TransaccionDTO dto : transacciones) {
                if (dto.getFecha().startsWith(String.valueOf(anio))) {
                    transaccionesAnio.add(dto);
                }
            }
            
            // Guardar respaldo
            String json = gson.toJson(transaccionesAnio);
            Files.write(Paths.get(nombreRespaldo), json.getBytes());
            
            System.out.println("💾 Respaldo generado: " + nombreRespaldo + " (" + transaccionesAnio.size() + " transacciones)");
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Error al generar respaldo: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Limpia todas las transacciones (usar con precaución)
     */
    public void limpiarTransacciones() {
        transacciones.clear();
        guardarTransacciones();
        System.out.println("🗑️ Todas las transacciones eliminadas");
    }
    
    /**
     * Obtiene la cantidad total de transacciones
     */
    public int contarTransacciones() {
        return transacciones.size();
    }
}
