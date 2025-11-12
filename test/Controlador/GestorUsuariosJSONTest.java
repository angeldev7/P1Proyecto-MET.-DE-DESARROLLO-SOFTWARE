package Controlador;

import Modelo.Usuario;
import java.io.File;

/**
 * Prueba de integración para GestorUsuariosJSON.
 * Verifica la interacción con el sistema de archivos.
 */
public class GestorUsuariosJSONTest {

    private static final String ARCHIVO_PRUEBA = "usuarios_test.json";

    public static void main(String[] args) {
        System.out.println("--- Ejecutando Pruebas de Integración para GestorUsuariosJSON ---");
        
        // Limpiar antes de empezar
        limpiarArchivoDePrueba();

        // Escenario 7: Agregar y validar un usuario
        testAgregarYValidarUsuario();

        // Limpiar al final
        limpiarArchivoDePrueba();
        
        System.out.println("\n--- Pruebas de Integración finalizadas ---");
    }

    private static void testAgregarYValidarUsuario() {
        System.out.print("Prueba 7: Agregar, guardar, cargar y validar usuario... ");
        
        // Usamos una instancia del gestor apuntando a nuestro archivo de prueba
        GestorUsuariosJSON gestor = GestorUsuariosJSON.getInstancia(ARCHIVO_PRUEBA);
        
        // 1. Agregar un usuario
        boolean agregado = gestor.agregarUsuario("testuser", "testpass", "Test User", "Asistente Contable");
        
        // 2. Crear una nueva instancia del gestor para forzar la carga desde el archivo
        GestorUsuariosJSON gestorNuevo = GestorUsuariosJSON.getInstancia(ARCHIVO_PRUEBA);
        
        // 3. Validar el usuario
        Usuario usuarioValidado = gestorNuevo.validarCredenciales("testuser", "testpass");
        
        if (agregado && usuarioValidado != null && usuarioValidado.getNombreUsuario().equals("testuser")) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ");
        }
    }

    private static void limpiarArchivoDePrueba() {
        File archivo = new File(ARCHIVO_PRUEBA);
        if (archivo.exists()) {
            archivo.delete();
        }
        // Resetea el singleton para futuras pruebas
        GestorUsuariosJSON.resetInstancia();
    }
}
