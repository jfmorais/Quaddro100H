package br.com.quaddro.quaddro100h.lab08.domain.model;

import java.io.Serializable;

public class Hotel implements Serializable {

    private String nome;
    private String endereco;
    private Float estrelas;

    public Hotel(String nome, String endereco, Float estrelas) {
        super();

        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Float getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Float estrelas) {
        this.estrelas = estrelas;
    }

    @Override
    public String toString() {
        return nome;
    }
}
