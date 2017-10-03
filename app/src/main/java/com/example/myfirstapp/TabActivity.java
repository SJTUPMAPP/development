package com.example.myfirstapp;


import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;


public class TabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private RecyclerView Rv;
        private ArrayList<HashMap<String,Object>> listItem;
        private MyAdapter myAdapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            initData();
            // 绑定数据到RecyclerView
            Rv = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            //使用线性布局
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            Rv.setLayoutManager(layoutManager);
            Rv.setHasFixedSize(true);

            //用自定义分割线类设置分割线
            Rv.addItemDecoration(new DividerItemDecoration(getActivity()));

            //为ListView绑定适配器
            myAdapter = new MyAdapter(getActivity(),listItem);
            Rv.setAdapter(myAdapter);
            return rootView;
        }


//        init data for the timeline
        public void initData(){
            listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/

            HashMap<String, Object> map1 = new HashMap<String, Object>();
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            HashMap<String, Object> map3 = new HashMap<String, Object>();
            HashMap<String, Object> map4 = new HashMap<String, Object>();
            HashMap<String, Object> map5 = new HashMap<String, Object>();
            HashMap<String, Object> map6 = new HashMap<String, Object>();

            map1.put("ItemTitle", "美国谷歌公司已发出");
            map1.put("ItemText", "发件人:谷歌 CEO Sundar Pichai");
            listItem.add(map1);

            map2.put("ItemTitle", "国际顺丰已收入");
            map2.put("ItemText", "等待中转");
            listItem.add(map2);

            map3.put("ItemTitle", "国际顺丰转件中");
            map3.put("ItemText", "下一站中国");
            listItem.add(map3);

            map4.put("ItemTitle", "中国顺丰已收入");
            map4.put("ItemText", "下一站广州华南理工大学");
            listItem.add(map4);

            map5.put("ItemTitle", "中国顺丰派件中");
            map5.put("ItemText", "等待派件");
            listItem.add(map5);

            map6.put("ItemTitle", "华南理工大学已签收");
            map6.put("ItemText", "收件人:Carson");
            listItem.add(map6);
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "今日";
                case 1:
                    return "本周";
                case 2:
                    return "本月";
            }
            return null;
        }
    }

}
