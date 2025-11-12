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
 * Gestor de persistencia de bitácora en JSON
 * Mantiene registro de auditoría permanente
 * Fase: Implementación - Metodología Cascada
 * 
 * @author Sistema Contable
 * @version 1.0.0
 */
public class GestorBitacoraJSON {
    private static GestorBitacoraJSON instancia;
    private static final String ARCHIVO_BITACORA = "bitacora.json";
    private Gson gson;
    private List<BitacoraDTO> registros;
    
    private GestorBitacoraJSON() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        cargarBitacora();
    }
    
    /**
     * Obtiene la instancia única del gestor (Singleton)
     */
    public static GestorBitacoraJSON getInstancia() {
        if (instancia == null) {
            instancia = new GestorBitacoraJSON();
        }
        return instancia;
    }
    
    /**
     * Carga la bitácora desde el archivo JSON
     */
    private void cargarBitacora() {
        File archivo = new File(ARCHIVO_BITACORA);
        
        if (archivo.exists()) {
            try {
                String json = new String(Files.readAllBytes(Paths.get(ARCHIVO_BITACORA)));
                Type listType = new TypeToken<ArrayList<BitacoraDTO>>(){}.getType();
                registros = gson.fromJson(json, listType);
                
                if (registros == null) {
                    registros = new ArrayList<>();
                }
                
                System.out.println("✅ Bitácora cargada desde JSON: " + registros.size() + " registros");
                
            } catch (IOException e) {
                System.err.println("❌ Error al leer bitacora.json: " + e.getMessage());
                registros = new ArrayList<>();
            } catch (JsonSyntaxException e) {
                System.err.println("❌ Error de formato en bitacora.json: " + e.getMessage());
                registros = new ArrayList<>();
            }
        } else {
            System.out.println("⚠️ Archivo bitacora.json no existe. Iniciando registro vacío.");
            registros = new ArrayList<>();
        }
    }
    
    /**
     * Guarda la bitácora en el archivo JSON
     */
    private void guardarBitacora() {
        try {
            String json = gson.toJson(registros);
            Files.write(Paths.get(ARCHIVO_BITACORA), json.getBytes());
            // No mostrar mensaje para no saturar consola
        } catch (IOException e) {
            System.err.println("❌ Error al guardar bitacora.json: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Registra una nueva entrada en la bitácora
     */
    public void registrar(Bitacora bitacora) {
        BitacoraDTO dto = BitacoraDTO.fromBitacora(bitacora);
        registros.add(dto);
        guardarBitacora();
    }
    
    /**
     * Obtiene todos los registros de la bitácora
     * El usuario se debe resolver desde ControladorBitacora
     */
    public List<BitacoraDTO> obtenerTodosRegistrosDTO() {
        return new ArrayList<>(registros);
    }
    
    /**
     * Obtiene los últimos N registros como DTO
     */
    public List<BitacoraDTO> obtenerUltimosRegistrosDTO(int cantidad) {
        int inicio = Math.max(0, registros.size() - cantidad);
        return new ArrayList<>(registros.subList(inicio, registros.size()));
    }
    
    /**
     * Obtiene registros por usuario como DTO
     */
    public List<BitacoraDTO> obtenerRegistrosPorUsuarioDTO(int idUsuario) {
        List<BitacoraDTO> lista = new ArrayList<>();
        
        for (BitacoraDTO dto : registros) {
            if (dto.getIdUsuario() == idUsuario) {
                lista.add(dto);
            }
        }
        
        return lista;
    }
    
    /**
     * Obtiene el próximo ID disponible
     */
    public int obtenerProximoId() {
        int maxId = 0;
        for (BitacoraDTO dto : registros) {
            if (dto.getIdRegistro() > maxId) {
                maxId = dto.getIdRegistro();
            }
        }
        return maxId + 1;
    }
    
    /**
     * Genera respaldo de la bitácora
     */
    public boolean generarRespaldo() {
        try {
            String timestamp = java.time.LocalDateTime.now().toString().replace(":", "-");
            String nombreRespaldo = "bitacora_respaldo_" + timestamp + ".json";
            
            String json = gson.toJson(registros);
            Files.write(Paths.get(nombreRespaldo), json.getBytes());
            
            System.out.println("💾 Respaldo de bitácora generado: " + nombreRespaldo + " (" + registros.size() + " registros)");
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Error al generar respaldo de bitácora: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene la cantidad total de registros
     */
    public int contarRegistros() {
        return registros.size();
    }
}
