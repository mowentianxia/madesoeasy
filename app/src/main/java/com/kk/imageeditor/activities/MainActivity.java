package com.kk.imageeditor.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.controllor.MyPreference;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.filebrowser.DialogFileFilter;
import com.kk.imageeditor.filebrowser.OpenFileDialog;
import com.kk.imageeditor.filebrowser.SaveFileDialog;
import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.utils.VUiKit;

import java.io.File;

import static com.kk.imageeditor.Constants.SETTINGS_CATEGORY;
import static com.kk.imageeditor.Constants.SETTINGS_CATEGORY_STYLE;

public class MainActivity extends DrawerAcitvity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView headImageView;
    private TextView headTitleView;
    private TextView headAuthorView;
    private TextView headVerView;
    private TextView setfileView;
    private DrawerLayout mDrawerlayout;
    private long exitLasttime;
    private boolean firstResume = true;
    private MyPreference mMyPreference;
    private String mCurStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setExitAnimEnable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMyPreference = MyPreference.get(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener((v) -> {
//            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        });

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerlayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View head = navigationView.getHeaderView(0);
        headImageView = (ImageView) head.findViewById(R.id.imageView);
        headTitleView = (TextView) head.findViewById(R.id.titleView);
        headAuthorView = (TextView) head.findViewById(R.id.authorView);
        headVerView = (TextView) head.findViewById(R.id.versionView);
        setfileView = (TextView) head.findViewById(R.id.setname);
        //初始化
        initDrawer((ViewGroup) findViewById(R.id.drawer));
        checkAndCopyStyle();
    }

    private void checkAndCopyStyle() {
        ProgressDialog dialog = ProgressDialog.show(this, null, getString(R.string.copy_style_wait));
        VUiKit.defer().when(() -> {
            if (styleControllor.copyStyleFromAssets()) {
                resetData();
                getDefaultData().cleanCache();
            }
            String style = mMyPreference.getCurStyle();
            if (TextUtils.isEmpty(style)) {
                //加载默认style
                style = styleControllor.findDefaultStyle(pathConrollor.getStylePath());
            }
            return style;
        }).done((style) -> {
            dialog.dismiss();
//            styleControllor.setCurStyle(style);
            checkAndLoadStyle(style, false);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstResume) {
            if (!TextUtils.equals(mCurStyle, mMyPreference.getCurStyle())) {
                checkAndLoadStyle(mMyPreference.getCurStyle(), true);
            }
            setSetFile(getSetFile());
        }
        firstResume = false;
    }

    private void checkAndLoadStyle(String style, boolean nocache) {
        if (TextUtils.isEmpty(style)) {
            Toast.makeText(this, R.string.load_style_error, Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog dialog = ProgressDialog.show(this, null, getString(R.string.load_style_wait));
        VUiKit.defer().when(() -> {
            return loadStyle(style, nocache);
        }).done((error) -> {
            dialog.dismiss();
            if (error == Drawer.Error.None) {
                mCurStyle = style;
                mMyPreference.setCurStyle(style);
                initStyle(!nocache);
//                zoomFit();
            } else {
                Toast.makeText(this, getString(R.string.load_style_error) + "\n" + style + "\nerror code:" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFromSelect() {
        //选择一个存档打开
        final OpenFileDialog fileDialog = new OpenFileDialog(this);
        fileDialog.setCurPath(pathConrollor.getCurPath());
        fileDialog.setDialogFileFilter(new DialogFileFilter(false, false, Constants.SET_EX));
        fileDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), (dialog, which) -> {
            pathConrollor.setCurPath(fileDialog.getCurPath());
            File file = fileDialog.getSelectFile();
            loadSet(file);
        });

        fileDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                getString(android.R.string.cancel),
                (DialogInterface.OnClickListener) null);
        fileDialog.show();
    }

    private void saveSetInfo(boolean useold) {
        if (useold && !TextUtils.isEmpty(getSetFile())) {
            //直接保存
            saveSet(null);
        } else {
            //选择/输入一个文件保存
            final SaveFileDialog fileDialog = new SaveFileDialog(this);
            fileDialog.setCurPath(pathConrollor.getCurPath());
            fileDialog.setHideCreateButton(true);
            fileDialog.setEditText(getSaveFileName());
            fileDialog.setDialogFileFilter(new DialogFileFilter(false, false, Constants.SET_EX));
            fileDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    getString(android.R.string.ok), (dialog, which) -> {
                        pathConrollor.setCurPath(fileDialog.getCurPath());
                        File file = fileDialog.getSelectFile();
                        if (file != null && !file.isDirectory())
                            saveSet(file.getAbsolutePath());
                    });
            fileDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    getString(android.R.string.cancel),
                    (DialogInterface.OnClickListener) null);
            fileDialog.show();
        }
    }

    @Override
    protected void setStyleInfo(StyleInfo info) {
        super.setStyleInfo(info);
        if (info != null) {
            if (headImageView != null) {
                Bitmap icon = getDefaultData().readImage(info, info.getIcon(), 0, 0);
                BitmapUtil.destroy(headImageView.getDrawable());
                headImageView.setImageBitmap(icon);
            }
            if (headVerView != null) {
                headVerView.setText("" + info.getVersion());
            }
            if (headTitleView != null) {
                headTitleView.setText(info.getDesc());
            }
            if (headAuthorView != null) {
                headAuthorView.setText(info.getAuthor());
            }
            if (setfileView != null) {
                setfileView.setText(getSaveFileName());
            }
        }
    }

    @Override
    public void setSetFile(String setFile) {
        super.setSetFile(setFile);
        runOnUiThread(() -> {
            if (setfileView != null) {
                String file = getSetFile();
                if (mMyPreference.showFullSetName()) {
                    setfileView.setText(file);
                } else {
                    setfileView.setText(FileUtil.getFileName(file));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if (onQuit()) {
                super.onBackPressed();
            }
        }
    }

    private boolean onQuit() {
        if (System.currentTimeMillis() - exitLasttime <= 3000) {
            finish();
            return true;
        } else {
            Toast.makeText(this, R.string.back_tip, Toast.LENGTH_SHORT).show();
            exitLasttime = System.currentTimeMillis();
        }
        return false;
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
            } else {
                mDrawerlayout.closeDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean checkDrawer() {
        boolean rs = super.checkDrawer();
        if (rs) {
            Snackbar.make(mDrawerlayout, R.string.need_select_style, Snackbar.LENGTH_LONG)
                    .setAction(R.string.select_style, (v) -> {
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
        } else if (id == R.id.menu_zoom_out) {
            zoomOut();
            return true;
        } else if (id == R.id.action_preview) {
            preview();
        }

        return super.onOptionsItemSelected(item);
    }

    private void preview() {
        Toast.makeText(this, R.string.saving_image, Toast.LENGTH_SHORT).show();
        VUiKit.defer().when(() -> {
            Bitmap image = getDrawBitmap();
            if (image == null) {
                return null;
            }
            String file = new File(pathConrollor.getCachePath(), Constants.PREVIEW_NAME).getAbsolutePath();
            BitmapUtil.saveBitmap(image, file, 100);
            return file;
        }).done((image) -> {
            if (image != null) {
                PhotoViewActivity.showImage(this, image, getSaveFileName());
            } else {
                Snackbar.make(mDrawerlayout, R.string.image_is_null, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_zoom_fit, (v) -> {
                            zoomFit();
                        }).show();
            }
        });
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        Log.i("msoe", "requestCode="+requestCode+",resultCode="+resultCode+",data="+data);
//        if (requestCode == Constants.REQUEST_STYLE) {
//            if (data != null) {
//                String file = data.getStringExtra(Intent.EXTRA_TEXT);
//                if (!TextUtils.isEmpty(file)) {
//                    if (TextUtils.equals(styleControllor.getCurStyle(), file)) {
//                        return;
//                    }
//                    checkAndLoadStyle(file, true);
//                }
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
    private void openStyleList() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(SETTINGS_CATEGORY, SETTINGS_CATEGORY_STYLE);
        startActivity(intent);
    }

    private void showAboutInfo() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            //关于信息
            showDialog(getString(R.string.about), String.format(getString(R.string.about_info),
                    getString(R.string.app_name),
                    packageInfo.versionName),
                    null, null, null, null);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_reset:
                if (checkDrawer()) return true;
                showDialog(getString(R.string.dialog_title),
                        getString(R.string.clear_tip), (v, s) -> {
                            resetData();
                        });
                break;
            case R.id.nav_save_set:
                if (checkDrawer()) return true;
                saveSetInfo(true);
                break;
            case R.id.nav_save_as:
                if (checkDrawer()) return true;
                saveSetInfo(false);
                break;
            case R.id.nav_load_set:
                if (checkDrawer()) return true;
                openFromSelect();
                break;
            case R.id.nav_style_list:
                openStyleList();
                break;
            case R.id.nav_manage:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                showAboutInfo();
                break;
            case R.id.nav_quit:
                showDialog(getString(R.string.dialog_title), getString(R.string.quit_tip), (v, s) -> {
                    finish();
                });
                break;
        }
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
