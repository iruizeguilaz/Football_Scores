package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{
    public static int selected_match_id;
    public static int current_fragment = 2;
    public final String LOG_TAG = MainActivity.class.getName();
    private final String save_tag = App.getContext().getString(R.string.Save_Text);
    private PagerFragment my_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            my_main = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, my_main)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Intent start_about = new Intent(this,AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(App.getContext().getString(R.string.Pager_Current),my_main.mPagerHandler.getCurrentItem());
        outState.putInt(App.getContext().getString(R.string.Selected_match),selected_match_id);
        getSupportFragmentManager().putFragment(outState,App.getContext().getString(R.string.My_main),my_main);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        current_fragment = savedInstanceState.getInt(App.getContext().getString(R.string.Pager_Current));
        selected_match_id = savedInstanceState.getInt(App.getContext().getString(R.string.Selected_match));
        my_main = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState,App.getContext().getString(R.string.My_main));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
