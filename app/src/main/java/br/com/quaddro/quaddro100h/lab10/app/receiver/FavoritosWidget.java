package br.com.quaddro.quaddro100h.lab10.app.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab10.app.service.FavoritosService;

public class FavoritosWidget extends AppWidgetProvider {

    public interface Extras {
        String ACTION = "acao";
    }

    public interface Actions {
        String ANTERIOR = "antes";
        String PROXIMO = "depois";
        String EXCLUIR = "apagar";
        String SITE = "site";
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.favoritos_view);
        PendingIntent pi;
        Intent ii;

        for (int i = 0; i < appWidgetIds.length; i++) {
            ii = new Intent(Actions.ANTERIOR);
            ii.putExtra(Extras.ACTION, Actions.ANTERIOR);
            ii.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            pi = PendingIntent.getService(context, appWidgetIds[i], ii, 0);
            views.setOnClickPendingIntent(R.id.bt_anterior, pi);

            ii = new Intent(Actions.PROXIMO);
            ii.putExtra(Extras.ACTION, Actions.PROXIMO);
            ii.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            pi = PendingIntent.getService(context, appWidgetIds[i], ii, 0);
            views.setOnClickPendingIntent(R.id.bt_proximo, pi);

            ii = new Intent(Actions.SITE);
            ii.putExtra(Extras.ACTION, Actions.SITE);
            ii.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            pi = PendingIntent.getService(context, appWidgetIds[i], ii, 0);
            views.setOnClickPendingIntent(R.id.tv_link, pi);
        }

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Intent i = new Intent(context, FavoritosService.class);
        i.putExtra(Extras.ACTION, Actions.EXCLUIR);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.startService(i);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context, FavoritosService.class));
    }
}
