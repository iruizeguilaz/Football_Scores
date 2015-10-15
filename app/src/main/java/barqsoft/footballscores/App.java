package barqsoft.footballscores;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ivan on 15/10/2015.
 *
 * this is used to access to resources easyly
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
