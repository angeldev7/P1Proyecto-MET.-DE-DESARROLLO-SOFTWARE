package Modelo;

/**
 * Prueba unitaria para la clase Usuario.
 * Verifica la lógica de negocio sin depender de archivos externos.
 */
public class UsuarioTest {

    public static void main(String[] args) {
        System.out.println("--- Ejecutando Pruebas para la Clase Usuario ---");
        
        // Escenario 1: Autenticación exitosa
        testAutenticacionExitosa();
        
        // Escenario 2: Autenticación fallida por contraseña incorrecta
        testAutenticacionFallida();
        
        // Escenario 3: Verificación de permisos para Jefatura
        testPermisosJefatura();
        
        // Escenario 4: Verificación de permisos para Asistente
        testPermisosAsistente();
        
        System.out.println("\n--- Pruebas para Usuario finalizadas ---");
    }

    private static void testAutenticacionExitosa() {
        System.out.print("Prueba 1: Autenticación exitosa... ");
        Usuario usuario = new Usuario(1, "admin", "admin123", "Admin", TipoRol.JEFATURA_FINANCIERA);
        boolean resultado = usuario.autenticar("admin123");
        
        if (resultado) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ");
        }
    }

    private static void testAutenticacionFallida() {
        System.out.print("Prueba 2: Autenticación fallida (contraseña incorrecta)... ");
        Usuario usuario = new Usuario(1, "admin", "admin123", "Admin", TipoRol.JEFATURA_FINANCIERA);
        boolean resultado = usuario.autenticar("password-incorrecto");
        
        if (!resultado) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ");
        }
    }

    private static void testPermisosJefatura() {
        System.out.print("Prueba 3: Jefatura tiene permiso para aprobar... ");
        Usuario jefe = new Usuario(1, "admin", "admin123", "Admin", TipoRol.JEFATURA_FINANCIERA);
        boolean tienePermiso = jefe.tienePermiso("APROBAR_TRANSACCION");
        
        if (tienePermiso) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ");
        }
    }

    private static void testPermisosAsistente() {
        System.out.print("Prueba 4: Asistente NO tiene permiso para aprobar... ");
        Usuario asistente = new Usuario(2, "asist", "asist123", "Asistente", TipoRol.ASISTENTE_CONTABLE);
        boolean tienePermiso = asistente.tienePermiso("APROBAR_TRANSACCION");
        
        if (!tienePermiso) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ");
        }
    }
}
