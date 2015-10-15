package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by Ivan on 15/10/2015.
 */
public class TodayScoresWidgetService extends RemoteViewsService  {

    private static final String LOG_TAG = TodayScoresWidgetService.class.getSimpleName();
    private static final int ID_INDEX = 0;
    private static final int HOME_INDEX = 1;
    private static final int HOME_GOALS_INDEX =2;
    private static final int AWAY_INDEX = 3;
    private static final int AWAY_GOALS_INDEX = 4;
    private static final int DATE_INDEX = 5;

    private static final String[] SCORE_COLUMNS = {
            DatabaseContract.SCORES_TABLE + "." + DatabaseContract.scores_table._ID,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.DATE_COL
    };


    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        //   return new CollectionWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
        return new CollectionTodayScoresWidgetService();
    }

    class CollectionTodayScoresWidgetService implements
            RemoteViewsService.RemoteViewsFactory {

        private Cursor data = null;

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (data != null) {
                data.close();
            }
            Uri todaysScoresUri = DatabaseContract.scores_table.buildScoreWithDate();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            data = getContentResolver().query(todaysScoresUri,
                    SCORE_COLUMNS,
                    null,
                    new String[]{dateFormatter.format(c.getTime())},
                    DatabaseContract.scores_table.DATE_COL + " ASC");

            Log.d(LOG_TAG, "Cursor: " + data);

        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            return data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION ||
                    data == null || !data.moveToPosition(position)) {
                Log.e(LOG_TAG, "error");
                return null;
            }
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_todayscores);
            // Get all of the strings from the cursor
            String homeTeam = data.getString(HOME_INDEX);
            Integer homeGoals = data.getInt(HOME_GOALS_INDEX);
            String awayTeam = data.getString(AWAY_INDEX);
            Integer awayGoals = data.getInt(AWAY_GOALS_INDEX);
            String matchDate = data.getString(DATE_INDEX);
            Log.d(LOG_TAG,"Match Date: " +  matchDate + " Home team: " + homeTeam + " Home Score: " + homeGoals);
            Log.d(LOG_TAG,"Match Date: "+ matchDate + " Away team: " + awayTeam + " Away score: " + awayGoals);
            remoteViews.setTextViewText(R.id.home_name_widget, homeTeam);
            if (homeGoals > -1) {
                remoteViews.setTextViewText(R.id.score_textview_widget, homeGoals.toString());
            }
            remoteViews.setTextViewText(R.id.away_name_widget, awayTeam);
            if (awayGoals > -1) {
                remoteViews.setTextViewText(R.id.data_textview_widget, awayGoals.toString());
            }
            return remoteViews;

        }


        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            if (data.moveToPosition(i)){
                return data.getLong(ID_INDEX);
            }
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
