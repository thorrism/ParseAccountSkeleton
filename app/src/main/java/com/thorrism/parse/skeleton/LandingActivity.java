package com.thorrism.parse.skeleton;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;
import com.thorrism.R;

public class LandingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setupToolbar();
        setupNavDrawer();
        setupNavigation();
    }

    private void setupToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
        }
    }

    private void setupNavDrawer() {
        if (mToolbar != null) {
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            //set name for current user
            TextView drawerNameView = (TextView) findViewById(R.id.profile_name);
            drawerNameView.setText(ParseUser.getCurrentUser().getUsername());

            //setup navigation listener
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.mipmap.ic_menu);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    private void setupNavigation(){
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch(menuItem.getItemId()){
                    default:
                        mDrawerLayout.closeDrawers();
                        return true;
                }
            }
        });
    }

    public void onBackPressed(){
        return;
    }
}
