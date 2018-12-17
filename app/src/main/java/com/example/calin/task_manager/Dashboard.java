package com.example.calin.task_manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import groupRoutes.DeleteUserGroup;
import groupRoutes.GetUsersBasicInfo;
import groupRoutes.UpdateGroup;
import userRoutes.GetUser;

public class Dashboard extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        GetUser.GetUser();
       // System.out.println("pula..........................................");
        //Map<String, String> user = GetUsersBasicInfo.GetUsersBasicInfo("test");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mSectionsPagerAdapter.addFragment(new Fragmenti());
        mSectionsPagerAdapter.addFragment(new Fragmentii());
        mSectionsPagerAdapter.addFragment(new Fragmentiii());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
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

        return super.onOptionsItemSelected(item);
    }
    //TODO - DASHBOARD - Implement global dashboard methods for Spinners/TextViews
    //
    ///TODO - FRAGMENT 1 - SETTINGS - Add logout option
    //User Settings Fragment - Stanga
    public static class Fragmenti extends Fragment{
        public Fragmenti(){
        }
        public Fragmenti newInstance1() {
            Fragmenti fragment = new Fragmenti();
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment1, container, false);
            System.out.println(GeneralInfo.user);
            final Spinner exitGroup = setArrayToSpinner((ArrayList<String>)GeneralInfo.user.get("groupNames"),R.id.exitGroupSpinner,rootView);
            exitGroup.setSelection(0);
            Spinner updateGroup = setArrayToSpinner((ArrayList<String>)GeneralInfo.user.get("groupNames"),R.id.updateGroupSpinner,rootView);
            updateGroup.setSelection(0);
            final Spinner updateGroupUsers = setMapToSpinner ((Map<String,String>)GetUsersBasicInfo.GetUsersBasicInfo(updateGroup.getSelectedItem().toString()),R.id.updateGroupUser,rootView);
            updateGroupUsers.setSelection(0);
            TextView username = setText((String)GeneralInfo.user.get("username"),R.id.usernameOutput,rootView,"Username");
            TextView email = setText((String)GeneralInfo.user.get("email"),R.id.emailOutput,rootView,"Email");;
            TextView fullname = setText((String)GeneralInfo.user.get("fullName"),R.id.fullnameOutput,rootView,"FullName");;
            Button exitGroupButton = rootView.findViewById(R.id.exitGroupButton);
            exitGroupButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    DeleteUserGroup.DeleteUserGroup(exitGroup.getSelectedItem().toString(),(String)GeneralInfo.user.get("username"));
                }


            });
            Button updateGroupButton = rootView.findViewById(R.id.updateGroupButton);
            exitGroupButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    UpdateGroup.UpdateGroup(updateGroupUsers.getSelectedItem().toString(),(List<String>)GeneralInfo.user.get("username"));
                }


            });
            return rootView;
        }

        private void refreshActivity(Activity v){
            v.finish();
            startActivity(v.getIntent());
        }
        private void setSpinners(){
            ///TODO - FRAGMENT 1 - SETTINGS - set all spinners from Fragment 1

        }
        private void setTexts(){
            ///TODO - FRAGMENT 1 - SETTINGS - set all Texts from Fragment 1
        }
        private Spinner setArrayToSpinner(ArrayList<String> items, int id, View view) {
            Spinner spinnerToSet = view.findViewById(id);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
            spinnerToSet.setAdapter(adapter);
            return spinnerToSet;
        }

        private Spinner setMapToSpinner(Map<String,String> users, int id, View view) {
            String[] list=users.keySet().toArray(new String[users.keySet().size()]);
            Spinner spinnerToSet = view.findViewById(id);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
            spinnerToSet.setAdapter(adapter);
            return spinnerToSet;
        }

        private TextView setText(String generic, int id, View view, String Type){
            TextView textPut = view.findViewById(id);
            textPut.setText("Your " + Type +" is " +generic);
            return textPut;
        }
    }
    ///TODO - FRAGMENT 2 - TASKVIEW - Add Spinner for group select /// Add Users in group (Fullname/Username)
    ///TODO - FRAGMENT 2 - TASKVIEW - Add Task Panel /// Tasks Received /// Tasks Given
    ///TODO - FRAGMENT 2 - TASKVIEW - Add Create Group Button
    //Dashboard segment - Middle
    public static class Fragmentii extends Fragment{
        public Fragmentii(){
        }
        public static Fragmentii newInstance1() {
            Fragmentii fragment = new Fragmentii();
            return fragment;
        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment2, container, false);

            Button buttonGeneric = (Button)rootView.findViewById(R.id.buttonGenericId);

            buttonGeneric.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    GetUser.GetUser();
//                    List<String> names = new ArrayList<String>();
//                    names.add("calin");
//                    names.add("ana");
//                    DeleteUserGroup.DeleteUserGroup("nothing", "ana");
//                    System.out.println(GeneralInfo.user);
                    //pui ce vrei
                    //orice vrei sa faci find by id, chestii de genul, te legi de rootView.(etc)
                    //tot ce tine de instantiere on view, on create, ma anunti
                    //Ai in fragment2.xml view-ul
                }
            });
            return rootView;
        }
    }

    //Create Task Fragment - Dreapta
    ///TODO - FRAGMENT 3 - CREATE TASK - Add "Hello user" /// Add Task Title /// Add input textString /// Add spinner for users + button
    ///TODO - FRAGMENT 3 - CREATE TASK - Add deadline checkbox + spinner /// Add priority checkbox + spinner
    ///TODO - FRAGMENT 3 - CREATE TASK - Add Create Task Button /// Add select group button on which to create task
    public static class Fragmentiii extends Fragment{
        public Fragmentiii(){
        }
        public static Fragmentiii newInstance1() {
            Fragmentiii fragment = new Fragmentiii();
            return fragment;
        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment3, container, false);
            return rootView;
        }
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
