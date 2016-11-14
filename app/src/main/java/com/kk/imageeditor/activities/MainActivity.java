package com.kk.imageeditor.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.imageeditor.R;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.draw.ImageLoader;
import com.kk.imageeditor.utils.VUiKit;

public class MainActivity extends DrawerAcitvity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView headImageView;
    private TextView headTitleView;
    private TextView headTextView;
    private TextView headAuthorView;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((v) -> {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headImageView = (ImageView) navigationView.findViewById(R.id.imageView);
        headTitleView = (TextView) navigationView.findViewById(R.id.titleView);
        headTextView = (TextView) navigationView.findViewById(R.id.textView);
        headAuthorView = (TextView) navigationView.findViewById(R.id.authorView);
        //初始化
        initDrawer((ViewGroup) findViewById(R.id.drawer));
        checkStyle();
    }

    private void checkStyle(){
        VUiKit.defer().when(()->{
            //复制assets

        }).done((rs)->{
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setStyleInfo(StyleInfo info) {
        super.setStyleInfo(info);
        if (info != null) {
            Bitmap icon = ImageLoader.getBitmapFormZip(info.filepath, info.getIcon(), 0, 0);
            headImageView.setImageBitmap(icon);
            headTitleView.setText(info.getName() + " " + info.getVersion());
            headAuthorView.setText(info.getAuthor());
            headTextView.setText(info.getDesc());
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.openDrawer(GravityCompat.START);
            }else{
                mDrawer.closeDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_zoom_in) {
            zoomIn();
            return true;
        }else if(id==R.id.menu_zoom_out){
            zoomOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
