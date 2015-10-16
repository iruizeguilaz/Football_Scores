package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;


/**
 * Created by Ivan on 15/10/2015.
 */
public class TodayScoresWidgetProvider extends AppWidgetProvider {



    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {

        super.onReceive(context, intent);

        String accion = intent.getAction();

        if (accion.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
                || accion.equals(AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED)) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,
                    this.getClass()));

            for (int i = 0; i < appWidgetIds.length; ++i) {

                Intent myintent = new Intent(context, TodayScoresWidgetService.class);
                myintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
                myintent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.collection_widget_scores);

                Intent startMainActivity = new Intent(context, MainActivity.class);
                PendingIntent startMainActivityPending = PendingIntent.getActivity(context,
                        0, startMainActivity, PendingIntent.FLAG_UPDATE_CURRENT );
                remoteViews.setPendingIntentTemplate(R.id.listview_widget, startMainActivityPending);
                remoteViews.setRemoteAdapter(R.id.listview_widget, myintent);
                // set the empty view
                remoteViews.setEmptyView(R.id.listview_widget, R.id.emptyTextView);

                appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);

            }
        }

    }


}
