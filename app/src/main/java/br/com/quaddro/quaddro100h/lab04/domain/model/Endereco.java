package br.com.quaddro.quaddro100h.lab04.domain.model;

import java.io.Serializable;
import java.util.Locale;

import br.com.quaddro.quaddro100h.lab04.domain.exception.BairroException;
import br.com.quaddro.quaddro100h.lab04.domain.exception.CEPException;
import br.com.quaddro.quaddro100h.lab04.domain.exception.EnderecoException;
import br.com.quaddro.quaddro100h.lab04.domain.exception.LogradouroException;
import br.com.quaddro.quaddro100h.lab04.domain.exception.MunicipioException;

public class Endereco implements Serializable {

    // Alameda Santos, 1000, andar 7, Jardim Paulista, 00000-000, São Paulo, SP

    private Long id;

    private String numero;

    private String complemento;

    private CEP cep;

    private Logradouro logradouro;

    private Endereco() {
        super();
        this.cep = CEP.getInstance();
        this.logradouro = new Logradouro();
    }

    private Endereco(Long id) {
        this();

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public CEP getCep() {
        return cep;
    }

    public void setCep(CEP cep) {
        this.cep = cep;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public void setUf(UF uf) {
        getLogradouro().setUf(uf);
    }

    public void setMunicipioNome(String nome) {
        getLogradouro().setMunicipioNome(nome);
    }

    public void setBairroNome(String nome) {
        getLogradouro().setBairroNome(nome);
    }

    public void setLogradouroNome(String nome) {
        getLogradouro().setNome(nome);
    }

    public void setLogradouroTipo(String tipo) {
        getLogradouro().setTipo(tipo);
    }

    public void setCep(String valor) {
        getCep().setValor(valor);
    }

    public static Endereco getInstance() {
        return new Endereco();
    }

    public static Endereco getInstance(Long id) {
        return new Endereco(id);
    }

    public static Endereco of(String logradouroTipo, String logradouroNome,
                              String numero, String complemento, String cep,
                              String bairro, String municipio, UF uf) {
        Endereco e = getInstance();

        e.setNumero(numero);
        e.setComplemento(complemento);
        e.setCep(cep);
        e.setLogradouroTipo(logradouroTipo);
        e.setLogradouroNome(logradouroNome);
        e.setBairroNome(bairro);
        e.setMunicipioNome(municipio);
        e.setUf(uf);

        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endereco endereco = (Endereco) o;

        if (getNumero() != null ? !getNumero().equals(endereco.getNumero()) : endereco.getNumero() != null)
            return false;
        if (getComplemento() != null ? !getComplemento().equals(endereco.getComplemento()) : endereco.getComplemento() != null)
            return false;
        if (getCep() != null ? !getCep().equals(endereco.getCep()) : endereco.getCep() != null)
            return false;
        return getLogradouro() != null ? getLogradouro().equals(endereco.getLogradouro()) : endereco.getLogradouro() == null;

    }

    @Override
    public int hashCode() {
        int result = getNumero() != null ? getNumero().hashCode() : 0;
        result = 31 * result + (getComplemento() != null ? getComplemento().hashCode() : 0);
        result = 31 * result + (getCep() != null ? getCep().hashCode() : 0);
        result = 31 * result + (getLogradouro() != null ? getLogradouro().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cep=" + cep +
                ", logradouro=" + logradouro +
                '}';
    }

    public UF getUf() {
        return getLogradouro().getUf();
    }

    public int getUfOrdinal() {
        return getLogradouro().getUfOrdinal();
    }

    public String getMunicipioNome() {
        return getLogradouro().getMunicipioNome();
    }

    public String getBairroNome() {
        return getLogradouro().getBairroNome();
    }

    public String getLogradouroTipo() {
        return getLogradouro().getTipo();
    }

    public String getLogradouroNome() {
        return getLogradouro().getNome();
    }

    public String getLogradouroCompleto() {
        return getLogradouro().getNomeCompleto();
    }

    public void setLogradouroCompleto(String logradouro) {
        if (logradouro.length() > 0) {
            int index = logradouro.indexOf(' ');
            if (index > 0) {
                setLogradouroTipo(logradouro.substring(0, index));
                setLogradouroNome(logradouro.substring(index + 1));
            } else {
                setLogradouroTipo(logradouro);
                setLogradouroNome("");
            }
        } else {
            setLogradouroTipo("");
            setLogradouroNome("");
        }
    }

    public boolean isNullId() {
        return getId() == null;
    }

    public boolean isNotNullId() {
        return !isNullId();
    }

    public static Endereco of(String logradouro, String numero,
                              String complemento, String cep,
                              String bairro, String municipio,
                              UF uf) {
        String tipo;
        if (logradouro.length() > 0) {
            int index = logradouro.indexOf(' ');
            if (index > 0) {
                tipo = logradouro.substring(0, index);
                logradouro = logradouro.substring(index + 1);
            } else {
                tipo = logradouro;
                logradouro = "";
            }
        } else {
            tipo = "";
        }
        return of(tipo, logradouro, numero, complemento, cep,
                bairro, municipio, uf);
    }

    public void validar() throws EnderecoException {
        // Numero
        // Complemento
    }

    public void validarLogradouro() throws LogradouroException {
        getLogradouro().validar();
    }

    public void validarCep() throws CEPException {
        getCep().validar();
    }

    public void validarBairro() throws BairroException {
        getBairro().validar();
    }

    public void validarMunicipio() throws MunicipioException {
        getMunicipio().validar();
    }

    public Bairro getBairro() {
        return getLogradouro().getBairro();
    }

    public Municipio getMunicipio() {
        return getLogradouro().getMunicipio();
    }
}
