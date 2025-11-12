package Modelo;

import java.time.LocalDate;

/**
 * Data Transfer Object para Transacción
 * Usado para serialización JSON
 * Fase: Implementación - Metodología Cascada
 * 
 * @author Sistema Contable
 * @version 1.0.0
 */
public class TransaccionDTO {
    private int idTransaccion;
    private String fecha; // LocalDate como String para JSON
    private String tipoDocumento; // "Factura" o "Gasto"
    private String proveedorCliente;
    private double monto;
    private String cuentaContable;
    private String numeroDocumento;
    private String estado;
    private int idUsuarioRegistro;
    
    // Campos específicos de Factura
    private Double subtotal;
    private Double iva;
    
    // Campos específicos de Gasto
    private Boolean deducible;
    private Double ivaCompra;
    
    // Constructor vacío para Gson
    public TransaccionDTO() {}
    
    // Constructor completo
    public TransaccionDTO(int idTransaccion, String fecha, String tipoDocumento,
                         String proveedorCliente, double monto, String cuentaContable,
                         String numeroDocumento, String estado, int idUsuarioRegistro) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipoDocumento = tipoDocumento;
        this.proveedorCliente = proveedorCliente;
        this.monto = monto;
        this.cuentaContable = cuentaContable;
        this.numeroDocumento = numeroDocumento;
        this.estado = estado;
        this.idUsuarioRegistro = idUsuarioRegistro;
    }
    
    /**
     * Convierte una Factura a TransaccionDTO
     */
    public static TransaccionDTO fromFactura(Factura factura) {
        TransaccionDTO dto = new TransaccionDTO(
            factura.getIdTransaccion(),
            factura.getFecha().toString(),
            factura.getTipoDocumento(),
            factura.getProveedorCliente(),
            factura.getMonto(),
            factura.getCuentaContable(),
            factura.getNumeroDocumento(),
            factura.getEstado(),
            factura.getUsuarioRegistro().getIdUsuario()
        );
        dto.subtotal = factura.getSubtotal();
        dto.iva = factura.getIva();
        return dto;
    }
    
    /**
     * Convierte un Gasto a TransaccionDTO
     */
    public static TransaccionDTO fromGasto(Gasto gasto) {
        TransaccionDTO dto = new TransaccionDTO(
            gasto.getIdTransaccion(),
            gasto.getFecha().toString(),
            gasto.getTipoDocumento(),
            gasto.getProveedorCliente(),
            gasto.getMonto(),
            gasto.getCuentaContable(),
            gasto.getNumeroDocumento(),
            gasto.getEstado(),
            gasto.getUsuarioRegistro().getIdUsuario()
        );
        dto.deducible = gasto.isDeducible();
        dto.ivaCompra = gasto.getIvaCompra();
        return dto;
    }
    
    /**
     * Convierte el DTO a Transacción (Factura o Gasto)
     */
    public Transaccion toTransaccion(Usuario usuario) {
        LocalDate fechaLocal = LocalDate.parse(fecha);
        
        if ("Factura".equals(tipoDocumento)) {
            return new Factura(
                idTransaccion,
                fechaLocal,
                proveedorCliente,
                subtotal != null ? subtotal : 0.0,
                cuentaContable,
                numeroDocumento,
                usuario
            );
        } else if ("Gasto".equals(tipoDocumento)) {
            return new Gasto(
                idTransaccion,
                fechaLocal,
                proveedorCliente,
                monto,
                cuentaContable,
                numeroDocumento,
                deducible != null ? deducible : false,
                usuario
            );
        }
        
        throw new IllegalArgumentException("Tipo de documento desconocido: " + tipoDocumento);
    }
    
    // Getters y Setters
    public int getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(int idTransaccion) { this.idTransaccion = idTransaccion; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    
    public String getProveedorCliente() { return proveedorCliente; }
    public void setProveedorCliente(String proveedorCliente) { this.proveedorCliente = proveedorCliente; }
    
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    
    public String getCuentaContable() { return cuentaContable; }
    public void setCuentaContable(String cuentaContable) { this.cuentaContable = cuentaContable; }
    
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getIdUsuarioRegistro() { return idUsuarioRegistro; }
    public void setIdUsuarioRegistro(int idUsuarioRegistro) { this.idUsuarioRegistro = idUsuarioRegistro; }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    
    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }
    
    public Boolean getDeducible() { return deducible; }
    public void setDeducible(Boolean deducible) { this.deducible = deducible; }
    
    public Double getIvaCompra() { return ivaCompra; }
    public void setIvaCompra(Double ivaCompra) { this.ivaCompra = ivaCompra; }
}
