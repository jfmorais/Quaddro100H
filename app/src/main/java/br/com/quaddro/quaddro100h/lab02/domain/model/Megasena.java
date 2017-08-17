package br.com.quaddro.quaddro100h.lab02.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class Megasena {

    // Estado (stateless)
    // Construtor
    private Megasena() {
        super();
    }

    // Comportamento
    public static String sortear() {
        final Object[] jogo = { 0, 0, 0, 0, 0, 0 };
        int numero, contador = 0;

        sorteio : do {
            numero = (int) (1 + Math.random() * 60);

            for (int i = 0; i < jogo.length; i++) {
                if (jogo[i].equals(0)) {
                    jogo[i] = numero;
                    contador++;
                    continue sorteio;
                } else if (jogo[i].equals(numero)) {
                    continue sorteio;
                }
            }
        } while (contador < jogo.length);

        Arrays.sort(jogo);

        return String.format(Locale.getDefault(),
                "%02d %02d %02d %02d %02d %02d", jogo);
    }

    public static ArrayList<String> sortear(int jogos) {
        ArrayList<String> lista = new ArrayList<>();
        String jogo;

        while (jogos != 0) {
            jogo = sortear();
            if (lista.contains(jogo)) {
                continue;
            }

            lista.add(jogo);
            //jogos = jogos - 1;
            //jogos -= 1;
            jogos--;
        }

        Collections.sort(lista);

        return lista;
    }
}
