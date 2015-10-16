package barqsoft.footballscores.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import barqsoft.footballscores.App;
import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;

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
    private static final int TIME_INDEX = 5;

    private static final String[] SCORE_COLUMNS = {
            DatabaseContract.SCORES_TABLE + "." + DatabaseContract.scores_table._ID,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.TIME_COL
    };


    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
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

            final long token = Binder.clearCallingIdentity();
            try {
                Uri todaysScoresUri = DatabaseContract.scores_table.buildScoreWithDate();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                data = getContentResolver().query(todaysScoresUri,
                        SCORE_COLUMNS,
                        null,
                        new String[]{dateFormatter.format(c.getTime())},
                        DatabaseContract.scores_table.DATE_COL + " ASC");
            } finally {
                Binder.restoreCallingIdentity(token);
            }

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
                return null;
            }
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_todayscores);

            Intent fillInIntent = new Intent();
                        fillInIntent.putExtra("EXTRA_ITEM", position);
                        remoteViews.setOnClickFillInIntent(R.id.linearlayaout_widget, fillInIntent);

            // Get all of the strings from the cursor
            String homeTeam = data.getString(HOME_INDEX);
            Integer homeGoals = data.getInt(HOME_GOALS_INDEX);
            String awayTeam = data.getString(AWAY_INDEX);
            Integer awayGoals = data.getInt(AWAY_GOALS_INDEX);
            String matchDate = data.getString(TIME_INDEX);
            remoteViews.setTextViewText(R.id.home_name_widget, homeTeam);
            if (homeGoals > -1 && awayGoals > -1) {
                remoteViews.setTextViewText(R.id.score_textview_widget, homeGoals.toString() +
                        App.getContext().getString(R.string.test_scorenull) +  awayGoals.toString());
            }
            remoteViews.setTextViewText(R.id.away_name_widget, awayTeam);
            remoteViews.setTextViewText(R.id.matchDate_textview_widget, matchDate);
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
