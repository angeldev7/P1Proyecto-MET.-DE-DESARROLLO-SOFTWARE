package Modelo;

public final class Constantes {
    
    // Constructor privado para evitar instanciacion
    private Constantes() {
        throw new UnsupportedOperationException("Clase de constantes no instanciable");
    }
    
    //CONSTANTES FISCALES
    public static final double IVA_ECUADOR = 0.15; // 15% IVA
    public static final String MONEDA = "USD";
    
    //VALIDACIONES
    public static final int LONGITUD_MINIMA_CONTRASENA = 6;
    public static final int LONGITUD_MINIMA_USUARIO = 3;
    public static final double MONTO_MINIMO_TRANSACCION = 0.01;
    
    // ARCHIVOS 
    public static final String ARCHIVO_USUARIOS = "usuarios.json";
    public static final String ARCHIVO_TRANSACCIONES = "transacciones.json";
    public static final String ARCHIVO_BITACORA = "bitacora.json";
    public static final String EXTENSION_RESPALDO = ".bak";
    
    //MENSAJES
    public static final String MSG_ERROR_CAMPOS_VACIOS = "CAMPOS INCOMPLETOS\n\nDebe completar todos los campos obligatorios.";
    public static final String MSG_ERROR_AUTENTICACION = "CREDENCIALES INCORRECTAS\n\nEl usuario o contraseña no son validos.";
    public static final String MSG_EXITO_OPERACION = "Operacion realizada exitosamente";
    
    // FORMATOS
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    public static final String FORMATO_MONEDA = "$%.2f";
    public static final String FORMATO_PORCENTAJE = "%.2f%%";
}
