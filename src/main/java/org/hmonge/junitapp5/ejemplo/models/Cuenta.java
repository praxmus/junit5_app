package org.hmonge.junitapp5.ejemplo.models;

import java.math.BigDecimal;

public class Cuentas {
    private String persona;
    private BigDecimal saldo;

    public Cuentas(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
