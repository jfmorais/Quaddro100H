package br.com.quaddro.quaddro100h.lab08.app.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.Serializable;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;

public class HotelActivity extends QuaddroActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hotel_view);

        fm = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Serializable model;
        HotelFragment hotelFragment;
        FragmentTransaction transaction;

        model = getIntent().getSerializableExtra("hotel");
        hotelFragment = new HotelFragment();
        Bundle b = new Bundle();
        b.putSerializable("hotel", model);
        hotelFragment.setArguments(b);

        transaction = fm.beginTransaction();
        transaction.replace(R.id.detalhe, hotelFragment, "LAB");
        transaction.commit();
    }
}
