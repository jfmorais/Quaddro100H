package br.com.quaddro.quaddro100h.lab14.app.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;
import br.com.quaddro.quaddro100h.lab14.design.pattern.LocationAdapter;

public class GPSActivity extends QuaddroActivity {

    TextView latitudeView, longitudeView, enderecoView;

    String provedor;

    LocationManager lm;

    LocationListener ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gps_view);

        latitudeView = getViewById(R.id.lab14_latitude);
        longitudeView = getViewById(R.id.lab14_longitude);
        enderecoView = getViewById(R.id.lab14_endereco);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        ll = new LocationAdapter() {
            @Override
            public void onLocationChanged(Location location) {
                localizar(location);
            }
        };

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1000);
            return;
        }
        Criteria c = new Criteria();
        provedor = lm.getBestProvider(c, false);
        lm.requestLocationUpdates(provedor, 5000, 0, ll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 2000);
            return;
        }
        localizar(lm.getLastKnownLocation(provedor));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            boolean ok = false;
            for (int i : grantResults) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    ok = false;
                    break;
                } else {
                    ok = true;
                }
            }
            if (!ok) {
                Toast.makeText(this, "Sem permissão!", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            Criteria c = new Criteria();
            provedor = lm.getBestProvider(c, false);

            switch (requestCode) {
                case 1000:
                    lm.requestLocationUpdates(provedor, 5000, 0, ll);
                    break;
                case 2000:
                    localizar(lm.getLastKnownLocation(provedor));
                    break;
            }
        } catch (SecurityException cause) {
            Log.e(TAG, "OPS...", cause);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        lm.removeUpdates(ll);
    }

    private void localizar(Location location) {
        if (location != null) {
            double latitude, longitude;

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            latitudeView.setText("Latitude: " + latitude);
            longitudeView.setText("Longitude: " + longitude);

            try {
                // Pegar o endereço
                Geocoder gc = new Geocoder(this);
                List<Address> list = gc.getFromLocation(latitude, longitude, 1);
                Address a = list.get(0);
                enderecoView.setText(String.format(Locale.getDefault(),
                        "%s, %s, %s, %s, %s, %s",
                        a.getThoroughfare(),
                        a.getSubThoroughfare(),
                        a.getPostalCode(),
                        a.getSubLocality(),
                        a.getLocality(),
                        a.getCountryName()));
            } catch (Exception cause) {
                Log.e(TAG, "OPS...", cause);
            }
        }
    }
}
