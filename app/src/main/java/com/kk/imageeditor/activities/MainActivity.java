package com.kk.imageeditor.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kk.imageeditor.R;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.draw.ImageLoader;
import com.kk.imageeditor.utils.VUiKit;

public class MainActivity extends DrawerAcitvity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQUEST_STYLE = 0x1000+1;
    private ImageView headImageView;
    private TextView headTitleView;
    private TextView headTextView;
    private TextView headAuthorView;
    private DrawerLayout mDrawerlayout;
    private long exitLasttime;

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

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerlayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headImageView = (ImageView) navigationView.findViewById(R.id.imageView);
        headTitleView = (TextView) navigationView.findViewById(R.id.titleView);
        headTextView = (TextView) navigationView.findViewById(R.id.textView);
        headAuthorView = (TextView) navigationView.findViewById(R.id.authorView);
        //初始化
        initDrawer((ViewGroup) findViewById(R.id.drawer));
        checkAndLoadStyle();
    }

    private void checkAndLoadStyle(){
        VUiKit.defer().when(()->{
            //复制assets
            //加载style
        }).done((rs)->{

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //非刚启动，如果是没加载的情况，加载style
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
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - exitLasttime <= 3000){
                super.onBackPressed();
                finish();
            }else{
                Toast.makeText(this, R.string.quit_info, Toast.LENGTH_SHORT).show();
                exitLasttime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerlayout.openDrawer(GravityCompat.START);
            }else{
                mDrawerlayout.closeDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean checkDrawer() {
        boolean rs= super.checkDrawer();
        if(!rs){
            Snackbar.make(mDrawerlayout, R.string.need_select_style, Snackbar.LENGTH_LONG)
                    .setAction(R.string.select_style, (v)->{
                        openStyleList();
                    }).show();
        }
        return rs;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_zoom_in) {
            zoomIn();
            return true;
        }else if(id==R.id.menu_zoom_out){
            zoomOut();
            return true;
        }else if(id == R.id.action_preview){
            Bitmap image= getDrawBitmap();
            if(image == null){
                Snackbar.make(mDrawerlayout, R.string.image_is_null, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_zoom_out, (v)->{
                            zoomOut();
                        }).show();
                return true;
            }
            PhotoViewActivity.showImage(this, image, getSaveFileName());
        }

        return super.onOptionsItemSelected(item);
    }

    private void openStyleList(){
        Intent intent=new Intent(this, StyleListActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, REQUEST_STYLE);
    }
    private void openFromSelect(){
        //TODO:
        //选择一个存档打开
    }
    private void saveSetInfo(){
        //TODO:
        if(!TextUtils.isEmpty(getSetFile())){
            //直接保存
            saveSet(null);
        }else{
            //选择/输入一个文件保存
        }
    }

    private void showAboutInfo(){
        //TODO:
        //关于信息
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_reset:
                resetData();
                break;
            case R.id.nav_save_set:
                if(!checkDrawer())return true;
                saveSetInfo();
                break;
            case R.id.nav_load_set:
                if(!checkDrawer())return true;
                openFromSelect();
                break;
            case R.id.nav_style_list:
                openStyleList();
                break;
            case R.id.nav_manage:
                Intent intent=new Intent(this, SettingsActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.nav_about:
                showAboutInfo();
                break;
        }
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
