package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

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
                // This intent will start the CollectionWidgetService
                // this intent will have the appwidgetId for an extra
                Intent myintent = new Intent(context, TodayScoresWidgetService.class);
                myintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
                myintent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
                // RemoteViews object for the collection widget's layout
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_todayscores);
                // Now connect the remoteviews object to use a remoteviews adapter.
                // this connects to a RemoteViewsService, described by the intent above.
                remoteViews.setRemoteAdapter(R.id.collectionListViewId, myintent);
                // set the empty view
                remoteViews.setEmptyView(R.id.collectionListViewId, R.id.emptyTextView);

                appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);

            }
        }

    }


}
