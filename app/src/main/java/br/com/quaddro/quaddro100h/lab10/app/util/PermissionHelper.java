package br.com.quaddro.quaddro100h.lab10.app.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public final class PermissionHelper {

    private PermissionHelper() {
        super();
    }

    public static boolean check(Activity context, int requestCode,
                                String permission) {
        boolean ok = false;
        int result = ActivityCompat.checkSelfPermission(context, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            ok = true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context, permission)) {
                Toast.makeText(context, "Favor habilitar permissão",
                        Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(context, new String[] {
                    permission
            }, requestCode);
        }
        return ok;
    }
}
