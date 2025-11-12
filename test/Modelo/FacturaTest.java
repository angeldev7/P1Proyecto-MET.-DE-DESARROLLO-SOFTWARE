package Modelo;

import java.time.LocalDate;

/**
 * Prueba unitaria para la clase Factura.
 * Verifica la lógica de negocio de cálculos.
 */
public class FacturaTest {

    public static void main(String[] args) {
        System.out.println("--- Ejecutando Pruebas para la Clase Factura ---");
        
        // Escenario 5: Cálculo correcto del IVA
        testCalcularIVA();
        
        // Escenario 6: Cálculo correcto del Monto Total
        testCalcularTotal();
        
        System.out.println("\n--- Pruebas para Factura finalizadas ---");
    }

    private static void testCalcularIVA() {
        System.out.print("Prueba 5: Cálculo de IVA correcto... ");
        Usuario usuario = new Usuario(1, "test", "test", "Test", "Test");
        // Subtotal 100.0, IVA esperado 15.0 (15%)
        Factura factura = new Factura(1, LocalDate.now(), "Cliente", 100.0, "101", "F-001", usuario);
        
        double ivaCalculado = factura.getIva();
        double ivaEsperado = 15.0;
        
        // Usamos una pequeña tolerancia para comparar doubles
        if (Math.abs(ivaCalculado - ivaEsperado) < 0.001) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ (Esperado: " + ivaEsperado + ", Obtenido: " + ivaCalculado + ")");
        }
    }

    private static void testCalcularTotal() {
        System.out.print("Prueba 6: Cálculo de Monto Total correcto... ");
        Usuario usuario = new Usuario(1, "test", "test", "Test", "Test");
        // Subtotal 100.0 + IVA 15.0 = Total 115.0
        Factura factura = new Factura(1, LocalDate.now(), "Cliente", 100.0, "101", "F-001", usuario);
        
        double totalCalculado = factura.getMonto();
        double totalEsperado = 115.0;
        
        if (Math.abs(totalCalculado - totalEsperado) < 0.001) {
            System.out.println("✅ PASÓ");
        } else {
            System.out.println("❌ FALLÓ (Esperado: " + totalEsperado + ", Obtenido: " + totalCalculado + ")");
        }
    }
}
