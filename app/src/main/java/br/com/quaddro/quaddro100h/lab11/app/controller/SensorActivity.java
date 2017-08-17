package br.com.quaddro.quaddro100h.lab11.app.controller;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;

public class SensorActivity extends QuaddroActivity {

    SensorManager sm;
    List<Sensor> list;
    TextView detalhes;
    int idSensorSelecionado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sensor_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        detalhes = getViewById(R.id.sensor_detalhe);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        list = sm.getSensorList(Sensor.TYPE_ALL);

        List<String> nomes = new ArrayList<>();
        for (Sensor i : list) {
            nomes.add(i.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nomes);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        Spinner s = getViewById(R.id.sensor);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                idSensorSelecionado = position;
                sm.unregisterListener(sensorEvent);
                sm.registerListener(sensorEvent,
                        list.get(position),
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private SensorEventListener sensorEvent = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            String msg = "Valores do Sensor:\n";
            for (int i = 0; i < event.values.length; i++) {
                msg += "valor[" + i + "] = " + event.values[i] + "\n";
            }
            detalhes.setText(msg);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Toast.makeText(getApplicationContext(), "Precisão: " + accuracy,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sensorEvent, list.get(idSensorSelecionado),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sensorEvent);
    }
}
