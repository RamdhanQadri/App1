package com.example.user.ssiapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MenuUtama extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        Toolbar tB = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tB);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("List Problem"));
        tabLayout.addTab(tabLayout.newTab().setText("Respon FLM"));
        tabLayout.addTab(tabLayout.newTab().setText("Progres FLM"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_flm:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_rpl:
                startActivity(new Intent(this, MainActivityRpl.class));
                return true;
            case R.id.action_maps:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.action_refresh:
                startActivity(new Intent(this, MenuUtama.class));
                finish();
                //return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}
