package org.hmonge.junitapp5.ejemplo.models;

import org.hmonge.junitapp5.ejemplo.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentasTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Hernando", new BigDecimal("1000.12345"));

        String esperado = "Hernando";
        String actual = cuenta.getPersona();

        assertNotNull(actual);
        assertEquals(esperado, actual);
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Hernando", new BigDecimal("1000.12345"));

        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9977"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9977"));

        //assertNotEquals(cuenta2, cuenta);
        assertEquals(cuenta2, cuenta);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Hernando", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal("100"));

        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Hernando", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal("100"));

        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Hernando", new BigDecimal("1000.12345"));

        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal("1001"));
        });

        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";

        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuentas() {

        Banco banco = new Banco("Banco del Estado");
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Hernando", new BigDecimal("1500.8989"));

        banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Banco banco = new Banco("Banco del Estado");
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Hernando", new BigDecimal("1500.8989"));

        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        assertAll(
                () -> {
                    assertEquals(2, banco.getCuentas().size());
                },
                () -> {
                    assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());
                },
                () -> {
                    assertEquals("Banco del Estado", cuenta2.getBanco().getNombre());
                },
                () -> {
                    assertEquals("Hernando", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Hernando"))
                            .findFirst()
                            .get()
                            .getPersona()
                    );
                },
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("John Doe"))
                    );
                }
        );
    }
}