package br.com.quaddro.quaddro100h.lab04.domain.model;

import java.io.Serializable;

import br.com.quaddro.quaddro100h.lab04.domain.exception.CEPException;

public class CEP implements Serializable {

    private String valor;

    private CEP() {
        super();
    }

    public static CEP getInstance() {
        return new CEP();
    }

    public CEP of(String valor) {
        CEP i = getInstance();

        i.setValor(valor);

        return i;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void validar() throws CEPException {
        if (getValor() == null) {
            throw new CEPException("CEP nulo!");
        }

        if (getValor().isEmpty()) {
            throw new CEPException("Favor, informe o CEP!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CEP cep = (CEP) o;

        return getValor() != null ? getValor().equals(cep.getValor()) : cep.getValor() == null;

    }

    @Override
    public int hashCode() {
        return getValor() != null ? getValor().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CEP{" +
                "valor='" + valor + '\'' +
                '}';
    }
}
