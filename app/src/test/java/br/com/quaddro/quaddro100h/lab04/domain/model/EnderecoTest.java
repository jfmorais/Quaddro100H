package br.com.quaddro.quaddro100h.lab04.domain.model;

import org.junit.Test;

public class EnderecoTest {

    @Test
    public void instanciaTest() {
        Endereco e = Endereco.of("Alameda", "Santos", "1000", "andar 7",
                "00000-000", "Jardim Paulista", "São Paulo", UF.SP);
        System.out.println(e);

        e = Endereco.getInstance();
        System.out.println(e);
    }
}
