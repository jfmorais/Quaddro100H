package br.com.quaddro.quaddro100h.lab04.app.dto;

public class EnderecoDTO {
/*
{
    "complemento": "de 502 ao fim - lado par",
    "bairro": "Pacaembu",
    "cidade": "S\u00e3o Paulo",
    "logradouro": "Avenida Pacaembu",
    "estado_info": { "area_km2": "248.221,996", "codigo_ibge": "35", "nome": "S\u00e3o Paulo" },
    "cep": "01234000",
    "cidade_info": { "area_km2": "1521,11", "codigo_ibge": "3550308" },
    "estado": "SP"
}
*/
    private String complemento;
    private String bairro;
    private String cidade;
    private String logradouro;
    private String cep;
    private String estado;

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "EnderecoDTO{" +
                "complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", cep='" + cep + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
