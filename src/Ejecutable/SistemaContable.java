package Ejecutable;

import Vista.InterfazLoginMejorada;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal ejecutable del Sistema Contable
 * Comercial el mejor vendedor S.A.
 * Ahora con gestion de usuarios en JSON
 * 
 * @author WildBär Systems
 * @version 1.0
 */
public class SistemaContable {
    
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            // Intentar usar Nimbus Look and Feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Si falla, usar el Look and Feel del sistema
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("No se pudo configurar el Look and Feel");
                ex.printStackTrace();
            }
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Mostrar mensaje de bienvenida en consola
                mostrarBienvenida();
                
                // Crear y mostrar la ventana de login mejorada con registro
                InterfazLoginMejorada login = new InterfazLoginMejorada();
                login.setVisible(true);
            }
        });
    }
    
    /**
     * Muestra un mensaje de bienvenida en la consola
     */
    private static void mostrarBienvenida() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║         SISTEMA CONTABLE - VERSIÓN 1.2                  ║");
        System.out.println("║    Comercial el mejor vendedor S.A.                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("✅ Sistema iniciado correctamente...");
        System.out.println("📅 Fecha: 08-nov-2025");
        System.out.println("💻 Desarrollado por: WildBär Systems");
        System.out.println();
        System.out.println("🆕 NOVEDADES VERSIÓN 1.2:");
        System.out.println("   ✅ Validación inteligente de entrada (acepta $, USD, comas)");
        System.out.println("   ✅ Mensajes de error mejorados y amigables");
        System.out.println("   ✅ Respaldos en formato JSON legible con estadísticas");
        System.out.println("   ✅ Confirmaciones detalladas de operaciones");
        System.out.println();
        System.out.println("📊 CARACTERÍSTICAS:");
        System.out.println("   ✅ Persistencia de transacciones en JSON");
        System.out.println("   ✅ Persistencia de bitácora de auditoría en JSON");
        System.out.println("   ✅ Los datos se conservan al cerrar el sistema");
        System.out.println("   ✅ Generación de respaldos anuales en JSON");
        System.out.println();
        System.out.println("👥 USUARIOS PREDEFINIDOS:");
        System.out.println("   • Jefatura Financiera:");
        System.out.println("     Usuario: admin       | Contraseña: admin123");
        System.out.println();
        System.out.println("   • Asistentes Contables:");
        System.out.println("     Usuario: asistente   | Contraseña: asistente123");
        System.out.println("     Usuario: carlos      | Contraseña: carlos123");
        System.out.println();
        System.out.println("💡 Puede crear nuevos usuarios desde la pantalla de login");
        System.out.println("📄 Archivos del sistema:");
        System.out.println("   - usuarios.json (contraseñas en texto plano)");
        System.out.println("   - transacciones.json (facturas y gastos)");
        System.out.println("   - bitacora.json (registro de auditoría)");
        System.out.println("   - respaldo_transacciones_YYYY_*.json (respaldos anuales)");
        System.out.println();
    }
}
