package br.com.quaddro.quaddro100h.lab06.app.controller;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;

public class AnimacaoQuadroActivity extends QuaddroActivity {

    AnimationDrawable a;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animacao_quadro_view);

        ImageView sprite = getViewById(R.id.sprite_image);
        a = (AnimationDrawable) sprite.getBackground();
    }

    @Override
    protected void onResume() {
        super.onResume();

        a.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        a.stop();
    }
}
