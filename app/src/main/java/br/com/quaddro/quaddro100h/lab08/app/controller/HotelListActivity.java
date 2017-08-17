package br.com.quaddro.quaddro100h.lab08.app.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;

public class HotelListActivity extends QuaddroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hotel_listar_view);
    }
}
