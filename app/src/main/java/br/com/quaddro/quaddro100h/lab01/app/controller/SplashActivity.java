package br.com.quaddro.quaddro100h.lab01.app.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab10.app.util.PermissionHelper;

public class SplashActivity extends QuaddroActivity {

    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_view);

        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarFavoritos();
    }

    private void carregarFavoritos() {
        try (FileOutputStream fos = openFileOutput("favoritos.txt",
                Context.MODE_PRIVATE);
             PrintWriter data = new PrintWriter(fos)) {
            data.println("www.quaddro.com.br");
            data.println("www.google.com.br");
            data.println("www.android.com");
            data.println("developer.android.com");
        } catch (IOException cause) {
            Log.e(TAG, "OPS...", cause);
        }

        boolean isOK = PermissionHelper.check(this, 1000,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isOK) {
            tratarArquivo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tratarArquivo();
                }
                break;
        }
    }

    private void tratarArquivo() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(
                        getApplicationContext(), MainActivity.class));
            }
        }, 5000);
        try {
            File sd = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File arq = new File(sd, "favoritosSD.txt");
            InputStream is; // bin
            OutputStream os; // bin
            Reader r; // txt
            Writer w; // txt

            Log.d(TAG, arq.getAbsolutePath());

            try (FileWriter des = new FileWriter(arq);
                 BufferedWriter dataOut = new BufferedWriter(des);
                 FileInputStream ori = openFileInput("favoritos.txt");
                 DataInputStream dataIn = new DataInputStream(ori)) {

                String line;
                while (dataIn.available() > 0) {
                    line = dataIn.readLine();
                    Log.d(TAG, line);
                    dataOut.write(line);
                }

                dataOut.flush();
            }
        } catch (Exception cause) {
            Log.e(TAG, "OPS...", cause);
        }
    }
}
