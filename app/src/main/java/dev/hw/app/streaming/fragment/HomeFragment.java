package dev.hw.app.streaming.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.hw.app.streaming.R;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MaterialTabListener {

    public static final String KEY_BUNDLE = "key_home_fragment";

    //int representing our 0th tab corresponding to the Fragment where search results are dispalyed
    public static final int TAB_CLIP = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where box office hits are dispalyed
    public static final int TAB_STREAM = 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where upcoming movies are displayed
    public static final int TAB_CHAT = 2;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_COUNT = 3;
    private static final String TAG_FRAGMENT = "StrangerChatFragment";

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;
    View rootView;

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance(String key, int value) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        //put any extra arguments that you may want to supply to this fragment
        args.putInt(key, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tabHost = (MaterialTabHost) rootView.findViewById(R.id.tabHost);
        pager = (ViewPager) rootView.findViewById(R.id.pager);

        // init view pager
        adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });
        pager.setOffscreenPageLimit(2);
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }

        Bundle bundle = getArguments();
        int value = bundle.getInt(KEY_BUNDLE);
        pager.setCurrentItem(value);

        // Fixed API 16
//        tabHost.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        tabHost.requestLayout();
//                    }
//                });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Home fragment", "PAUSE");
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        Log.d("POSITION", materialTab.getPosition() + "");
        pager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            Fragment fragment = null;
//            L.m("getItem called for " + num);
            switch (num) {
                case TAB_CLIP:
                    fragment = ClipFragment.newInstance("","");
                    break;
                case TAB_STREAM:
                    fragment = StreamFragment.newInstance("", "");
                    break;
                case TAB_CHAT:
                    fragment = StrangerChatFragment.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }
    }
}
