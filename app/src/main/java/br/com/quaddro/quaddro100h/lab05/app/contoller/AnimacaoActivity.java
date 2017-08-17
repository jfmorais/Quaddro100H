package br.com.quaddro.quaddro100h.lab05.app.contoller;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.Locale;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;

public class AnimacaoActivity extends QuaddroActivity {

    Spinner spAnimacao;
    Spinner spInterpolacao;
    ImageView wilson;
    View btExecutar;

    AnimationAction aa;
    Animation[] animations;
    Interpolator[] interpolators;

    private class OnClickAction implements View.OnClickListener {

        Interpolator i;
        Animation a;

        @Override
        public void onClick(View v) {
            Log.i(TAG, String.format(Locale.getDefault(),
                    "Clicado em %s",
                    v.getClass().getSimpleName()));

            i = interpolators[spInterpolacao.getSelectedItemPosition()];
            a = animations[spAnimacao.getSelectedItemPosition()];

            a.setAnimationListener(aa);
            a.setInterpolator(i);
            wilson.requestLayout();
            wilson.setAnimation(a);

            a.start();
        }
    }

    private class AnimationAction implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            Log.i(TAG, "Iniciando animação " +
                    animation.getClass().getSimpleName());
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            Log.i(TAG, "Parando animação " +
                    animation.getClass().getSimpleName());
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.i(TAG, "Repetindo animação " +
                    animation.getClass().getSimpleName());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animacao_view);

        aa = new AnimationAction();
        spAnimacao = getViewById(R.id.sp_animacao);
        spInterpolacao = getViewById(R.id.sp_interpolador);
        wilson = getViewById(R.id.wilson);
        btExecutar = getViewById(R.id.bt_executar);

        btExecutar.setOnClickListener(new OnClickAction());

        initAnimacoes();
        initInterpoladores();
    }

    private void initAnimacoes() {
        animations = new Animation[spAnimacao.getCount()];
        animations[0] = new AlphaAnimation(1, 0);
        animations[1] = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animations[2] = new ScaleAnimation(
                1, 3, 1, 3,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animations[3] = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, // De X
                Animation.ABSOLUTE, 200,       // Para X
                Animation.RELATIVE_TO_SELF, 0, // De Y
                Animation.ABSOLUTE, 300);      // Para Y
        animations[3].setDuration(800);

        AnimationSet set = new AnimationSet(true);
        for (int i = 0; i < animations.length - 2; i++) {
            animations[i].setDuration(1000);
            animations[i].setRepeatMode(Animation.REVERSE);
            animations[i].setRepeatCount(1);
            set.addAnimation(animations[i]);
        }
        animations[4] = set;
    }

    private void initInterpoladores() {
        // Objeto Imutável
        interpolators = new Interpolator[] { // Objeto anônimo
                new AccelerateDecelerateInterpolator(),
                new AccelerateInterpolator(),
                new AnticipateInterpolator(),
                new AnticipateOvershootInterpolator(),
                new BounceInterpolator(),
                new CycleInterpolator(2f),
                new DecelerateInterpolator(),
                new LinearInterpolator(),
                new OvershootInterpolator()
        };
    }
}
