package dev.hw.app.streaming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.fragment.ClipFragment;
import dev.hw.app.streaming.fragment.FragmentDrawer;
import dev.hw.app.streaming.fragment.HomeFragment;
import dev.hw.app.streaming.fragment.MessagesFragment;
import dev.hw.app.streaming.fragment.StrangerChatFragment;
import dev.hw.app.streaming.helper.SessionManager;

/**
 * Main activity for app
 *
 * @author huyuit
 * @version 1.0
 * @since 11-05-2015
 */

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

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
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance(HomeFragment.KEY_BUNDLE, HomeFragment.TAB_CLIP);
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = HomeFragment.newInstance(HomeFragment.KEY_BUNDLE, HomeFragment.TAB_STREAM);
                title = getString(R.string.title_home);
                break;
            case 2:
                fragment = HomeFragment.newInstance(HomeFragment.KEY_BUNDLE, HomeFragment.TAB_CHAT);
                title = getString(R.string.title_home);
                break;
            case 3:
                fragment = HomeFragment.newInstance(HomeFragment.KEY_BUNDLE, HomeFragment.TAB_CLIP);
                title = getString(R.string.title_home);
                break;
            case 4:
                fragment = new ClipFragment();
                title = getString(R.string.title_friends);
                break;
            case 5:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            case 6:
                fragment = new StrangerChatFragment();
                title = getString(R.string.title_stranger_chat);
                break;
            case 7:
                this.logOut();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * Log out function
     */

    private void logOut() {
        // Remove session
        SessionManager session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            session.setLogout();
        } else {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.msg_error_logout),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
        // Move to login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}
