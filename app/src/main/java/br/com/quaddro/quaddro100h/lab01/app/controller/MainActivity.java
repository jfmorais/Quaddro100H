package br.com.quaddro.quaddro100h.lab01.app.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab02.app.controller.MegasenaActivity;
import br.com.quaddro.quaddro100h.lab03.app.controller.TicTacToeActivity;
import br.com.quaddro.quaddro100h.lab04.app.controller.EnderecoInserirActivity;
import br.com.quaddro.quaddro100h.lab04.app.controller.EnderecoListarActivity;
import br.com.quaddro.quaddro100h.lab05.app.contoller.AnimacaoActivity;
import br.com.quaddro.quaddro100h.lab06.app.controller.AnimacaoQuadroActivity;
import br.com.quaddro.quaddro100h.lab08.app.controller.HotelListActivity;
import br.com.quaddro.quaddro100h.lab09.app.controller.NotificationActivity;
import br.com.quaddro.quaddro100h.lab11.app.controller.SensorActivity;
import br.com.quaddro.quaddro100h.lab13.app.controller.MapsActivity;
import br.com.quaddro.quaddro100h.lab14.app.controller.GPSActivity;

public class MainActivity extends QuaddroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main_view);

        this.setOnClickActivity(R.id.bt_megasena, MegasenaActivity.class);
        this.setOnClickActivity(R.id.bt_tictactoe, TicTacToeActivity.class);
        this.setOnClickActivity(R.id.bt_endereco, EnderecoListarActivity.class);
        this.setOnClickActivity(R.id.bt_animacao, AnimacaoActivity.class);
        this.setOnClickActivity(R.id.bt_animacao_quadro, AnimacaoQuadroActivity.class);
        this.setOnClickActivity(R.id.bt_fragment, HotelListActivity.class);
        this.setOnClickActivity(R.id.bt_notification, NotificationActivity.class);
        this.setOnClickActivity(R.id.bt_sensor, SensorActivity.class);
        this.setOnClickActivity(R.id.bt_mapas, MapsActivity.class);
        this.setOnClickActivity(R.id.bt_gps, GPSActivity.class);
    }
}
