package Controlador;

import Modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestión de transacciones contables
 * Ahora con persistencia en JSON
 * Fase: Implementación - Metodología Cascada
 */
public class ControladorTransaccion {
    private static ControladorTransaccion instancia;
    private List<Transaccion> transacciones;
    private int proximoId;
    private ControladorBitacora controladorBitacora;
    private GestorTransaccionesJSON gestorJSON;
    
    private ControladorTransaccion() {
        controladorBitacora = ControladorBitacora.getInstancia();
        gestorJSON = GestorTransaccionesJSON.getInstancia();
        
        // Cargar transacciones desde JSON
        transacciones = gestorJSON.obtenerTodasTransacciones();
        proximoId = gestorJSON.obtenerProximoId();
        
        // Solo cargar datos de prueba si no hay transacciones
        if (transacciones.isEmpty()) {
            cargarDatosPrueba();
        }
    }
    
    public static ControladorTransaccion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorTransaccion();
        }
        return instancia;
    }
    
    /**
     * Carga datos de prueba
     */
    private void cargarDatosPrueba() {
        Usuario usuarioPrueba = new Usuario(99, "sistema", "sistema", 
                                            "Sistema", TipoRol.JEFATURA_FINANCIERA);
        
        transacciones.add(new Factura(proximoId++, LocalDate.of(2025, 10, 13), 
            "Importadora XYZ", 150.75, "2101.01", "FAC-001", usuarioPrueba));
        transacciones.add(new Gasto(proximoId++, LocalDate.of(2025, 10, 12), 
            "Oficina S.A.", 45.20, "5105.05", "COMP-001", true, usuarioPrueba));
    }
    
    /**
     * Registra una nueva factura
     */
    public boolean registrarFactura(LocalDate fecha, String cliente, double subtotal,
                                   String cuentaContable, String numeroFactura, 
                                   Usuario usuario) {
        Factura factura = new Factura(proximoId++, fecha, cliente, subtotal, 
                                     cuentaContable, numeroFactura, usuario);
        
        if (factura.validar()) {
            transacciones.add(factura);
            gestorJSON.agregarFactura(factura); // Persistir en JSON
            controladorBitacora.registrar(usuario, "REGISTRAR_FACTURA", 
                String.format("Factura %s por $%.2f", numeroFactura, factura.getMonto()));
            return true;
        }
        return false;
    }
    
    /**
     * Registra un nuevo gasto
     */
    public boolean registrarGasto(LocalDate fecha, String proveedor, double monto,
                                 String cuentaContable, String numeroComprobante,
                                 boolean deducible, Usuario usuario) {
        Gasto gasto = new Gasto(proximoId++, fecha, proveedor, monto, 
                               cuentaContable, numeroComprobante, deducible, usuario);
        
        if (gasto.validar()) {
            transacciones.add(gasto);
            gestorJSON.agregarGasto(gasto); // Persistir en JSON
            controladorBitacora.registrar(usuario, "REGISTRAR_GASTO", 
                String.format("Gasto %s por $%.2f", numeroComprobante, monto));
            return true;
        }
        return false;
    }
    
    /**
     * Aprueba una transacción (solo Jefatura)
     */
    public boolean aprobarTransaccion(int idTransaccion, Usuario usuario) {
        if (!usuario.tienePermiso("APROBAR_TRANSACCION")) {
            return false;
        }
        
        for (Transaccion t : transacciones) {
            if (t.getIdTransaccion() == idTransaccion) {
                t.setEstado(EstadoTransaccion.APROBADO);
                gestorJSON.actualizarEstado(idTransaccion, EstadoTransaccion.APROBADO); // Persistir
                controladorBitacora.registrar(usuario, "APROBAR_TRANSACCION", 
                    "Transacción #" + idTransaccion + " aprobada");
                return true;
            }
        }
        return false;
    }
    
    /**
     * Rechaza una transacción (solo Jefatura)
     */
    public boolean rechazarTransaccion(int idTransaccion, Usuario usuario) {
        if (!usuario.tienePermiso("APROBAR_TRANSACCION")) { // Mismo permiso que aprobar
            return false;
        }
        
        for (Transaccion t : transacciones) {
            if (t.getIdTransaccion() == idTransaccion) {
                t.setEstado(EstadoTransaccion.RECHAZADO);
                gestorJSON.actualizarEstado(idTransaccion, EstadoTransaccion.RECHAZADO); // Persistir
                controladorBitacora.registrar(usuario, "RECHAZAR_TRANSACCION", 
                    "Transacción #" + idTransaccion + " rechazada");
                return true;
            }
        }
        return false;
    }
    
    /**
     * Elimina una factura con errores
     */
    public boolean eliminarFactura(int idTransaccion, Usuario usuario) {
        if (!usuario.tienePermiso("ELIMINAR_FACTURA")) {
            return false;
        }
        
        for (Transaccion t : transacciones) {
            if (t.getIdTransaccion() == idTransaccion && 
                t.getEstado().equals(EstadoTransaccion.REGISTRADO)) {
                t.setEstado(EstadoTransaccion.ELIMINADO);
                gestorJSON.eliminarTransaccion(idTransaccion); // Persistir
                controladorBitacora.registrar(usuario, "ELIMINAR_FACTURA", 
                    "Factura #" + idTransaccion + " eliminada");
                return true;
            }
        }
        return false;
    }
    
    /**
     * Obtiene todas las transacciones activas
     */
    public List<Transaccion> getTransaccionesActivas() {
        return transacciones.stream()
            .filter(t -> !t.getEstado().equals(EstadoTransaccion.ELIMINADO))
            .collect(Collectors.toList());
    }
    
    /**
     * Calcula la retención de IVA anual
     * Retiene el 30% del IVA en compras (gastos)
     */
    public double calcularRetencionIVA(int anio) {
        double ivaCompras = 0;
        
        for (Transaccion t : transacciones) {
            if (t.getFecha().getYear() == anio && 
                t.getEstado().equals(EstadoTransaccion.APROBADO)) {
                if (t instanceof Gasto) {
                    ivaCompras += ((Gasto) t).getIvaCompra();
                }
            }
        }
        
        // Retención del 30% del IVA en compras
        return ivaCompras * 0.30;
    }
    
    public List<Transaccion> getTransacciones() {
        return new ArrayList<>(transacciones);
    }
}
