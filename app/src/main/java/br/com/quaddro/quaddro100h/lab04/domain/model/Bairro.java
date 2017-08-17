package br.com.quaddro.quaddro100h.lab04.domain.model;

import java.io.Serializable;
import java.util.Locale;

import br.com.quaddro.quaddro100h.lab04.domain.exception.BairroException;

public class Bairro implements Serializable {

    private String nome;

    private Municipio municipio;

    public Bairro() {
        super();
        this.municipio = new Municipio();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public void setUf(UF uf) {
        getMunicipio().setUf(uf);
    }

    public void setMunicipioNome(String nome) {
        getMunicipio().setNome(nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bairro bairro = (Bairro) o;

        if (getNome() != null ? !getNome().equals(bairro.getNome()) : bairro.getNome() != null)
            return false;
        return getMunicipio() != null ? getMunicipio().equals(bairro.getMunicipio()) : bairro.getMunicipio() == null;

    }

    @Override
    public int hashCode() {
        int result = getNome() != null ? getNome().hashCode() : 0;
        result = 31 * result + (getMunicipio() != null ? getMunicipio().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bairro{" +
                "nome='" + nome + '\'' +
                ", municipio=" + municipio +
                '}';
    }

    public UF getUf() {
        return getMunicipio().getUf();
    }

    public String getMunicipioNome() {
        return getMunicipio().getNome();
    }

    public int getUfOrdinal() {
        return getMunicipio().getUfOrdinal();
    }

    public void validar() throws BairroException {
        if (nome == null) {
            throw new BairroException("Bairro nulo!");
        }

        if (nome.isEmpty()) {
            throw new BairroException("Favor, informe o bairro!");
        }
    }
}
