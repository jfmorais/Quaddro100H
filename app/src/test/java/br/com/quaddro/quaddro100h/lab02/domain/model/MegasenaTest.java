package br.com.quaddro.quaddro100h.lab02.domain.model;

import org.junit.Test;

import java.util.List;

public class MegasenaTest {

    @Test
    public void sortearTest() {
        String s = Megasena.sortear();

        System.out.println("sortearTest..");
        System.out.println(s);
    }

    @Test
    public void sortear10Test() {
        final int jogos = 10;
        List<String> lista = Megasena.sortear(jogos);

        System.out.println("sortear10Test...");
        if (!lista.isEmpty()) {
            for (String s : lista) {
                System.out.println(s);
            }
        }
    }
}
