package br.com.quaddro.quaddro100h.lab02.app.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;
import br.com.quaddro.quaddro100h.lab01.design.pattern.OnSeekBarChangeAdapter;
import br.com.quaddro.quaddro100h.lab02.domain.model.Megasena;

public class MegasenaActivity extends QuaddroActivity {

    // Veio da View
    TextView textView;
    SeekBar seekBar;
    ListView listView;

    // Veio do Model
    int jogos;
    ArrayList<String> lista = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.megasena_view);

        textView = getViewById(R.id.tv_sorteio);
        seekBar = getViewById(R.id.sb_sorteio);
        listView = getViewById(android.R.id.list);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(
                    SeekBar seekBar,
                    int progress,
                    boolean fromUser) {
                if (fromUser) {
                    textView.setText(String.valueOf(progress));
                }
            }
        });

        if (savedInstanceState != null) {
            jogos = savedInstanceState.getInt("jogos");
            lista = savedInstanceState.getStringArrayList("lista");

            textView.setText(String.valueOf(jogos));
            seekBar.setProgress(jogos);
        }
    }

    @Override
    protected void onResume() {
        super.onResume(); // Inversão de controle

        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                lista));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("jogos", jogos);
        outState.putStringArrayList("lista", lista);
    }

    public void sortear(View v) {
        Log.i(TAG, "Sorteando...");

        jogos = seekBar.getProgress();
        lista = Megasena.sortear(jogos);

        onResume();
    }
}
