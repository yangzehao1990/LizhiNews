package com.example.lizhinews.ui;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lizhinews.adapter.TopAdapter.MainFragmentAdapter;
import com.example.lizhinews.fragment.JiangsuFragment;
import com.example.lizhinews.fragment.TopFragment;
import com.example.lizhinews.utils.NetUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private HorizontalScrollView mHorizontalScrollView;
    private RadioGroup mRadioGroup;
    private View mGuide;
    private ViewPager mViewpager;
    private LinearLayout.LayoutParams mParams;
    private ArrayList<Fragment> mFragments;
 //   private ImageView mAddImage;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetUtils.ShowDialog(MainActivity.this);
        initId();
        initUI();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 初始化UI
     */
    private void initUI()
    {
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setBackgroundColor(Color.WHITE);
        mToolbar.setTitleTextColor(Color.BLACK);
        mToolbar.setLogo(R.mipmap.newiconnoti);
        //  mDrawerLayout.openDrawer(GravityCompat.START);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                mDrawerLayout, mToolbar, R.string.open, R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                // invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                //invalidateOptionsMenu();
            }
        };

        mActionBarDrawerToggle.syncState();
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mViewpager.addOnPageChangeListener(this);

        mFragments = new ArrayList<>();
        mFragments.add(new TopFragment());
        mFragments.add(new JiangsuFragment());

        mFragments.add(new JiangsuFragment());
        mFragments.add(new JiangsuFragment());

        mFragments.add(new JiangsuFragment());
        mFragments.add(new JiangsuFragment());

        mFragments.add(new JiangsuFragment());
        mFragments.add(new JiangsuFragment());

        mFragments.add(new JiangsuFragment());
        mFragments.add(new JiangsuFragment());

//        mFragments.add(new GuoneiFragment());
//        mFragments.add(new GuojiFragment());
//        mFragments.add(new ZaixianchangFragment());
//        mFragments.add(new LizhipaiFragment());
//        mFragments.add(new DujiayifanFragment());
//        mFragments.add(new TiyuFragment());
//        mFragments.add(new JunshiFragment());
//        mFragments.add(new KejiFragment());

        mViewpager.setAdapter(new MainFragmentAdapter(
                getSupportFragmentManager(), MainActivity.this, mFragments));


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.main_top_id:
                        SelectItem(0, group);
                        break;

                    case R.id.main_jiangsu_id:
                        SelectItem(1, group);
                        break;

                    case R.id.main_guonei_id:
                        SelectItem(2, group);
                        break;

                    case R.id.main_guoji_id:
                        SelectItem(3, group);
                        break;

                    case R.id.main_zaixianchang_id:
                        SelectItem(4, group);
                        break;

                    case R.id.main_lizhipai_id:
                        SelectItem(5, group);
                        break;

                    case R.id.main_dujiayifan_id:
                        SelectItem(6, group);
                        break;

                    case R.id.main_tiyu_id:
                        SelectItem(7, group);
                        break;

                    case R.id.main_junshi_id:
                        SelectItem(8, group);
                        break;

                    case R.id.main_keji_id:
                        SelectItem(9, group);
                        break;
                }
            }
        });


//        mAddImage.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    /**
     * 根据被选中的RadioButton改变字体以及颜色
     *
     * @param position   每一个RadioButton的索引
     * @param radioGroup 导航栏
     */
    private void SelectItem(int position, RadioGroup radioGroup)
    {
        mViewpager.setCurrentItem(position);
        for (int i = 0; i < mFragments.size(); i++)
        {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (i == position)
            {
                radioButton.setTextColor(Color.RED);
                radioButton.setTextSize(18);
                radioButton.getPaint().setFakeBoldText(true);
            } else
            {
                radioButton.setTextColor(Color.BLACK);
                radioButton.setTextSize(16);
                radioButton.getPaint().setFakeBoldText(false);
            }
        }
    }

    /**
     * 初始化ID
     */
    private void initId()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_id);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.main_horizontalscrollview_id);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_id);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_id);
        mRadioGroup = (RadioGroup) findViewById(R.id.main_radoigroup_id);
     //   mAddImage = (ImageView) findViewById(R.id.main_add_id);
        mGuide = findViewById(R.id.main_guide_id);
        mViewpager = (ViewPager) findViewById(R.id.main_viewpager_id);
        mParams = (LinearLayout.LayoutParams) mGuide.getLayoutParams();
    }

    /**
     * 导航条的位置
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        if (position == 0)
        {
            mParams.setMargins((int) (mParams.width * positionOffset), 0, 0, 0);
        } else
        {
            mParams.setMargins((int) (mParams.width * position + mParams.width * positionOffset), 0, 0, 0);
        }
        mGuide.setLayoutParams(mParams);
    }

    /**
     * 导航条根据偏移量移动的位置
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position)
    {
        RadioButton childView = (RadioButton) mRadioGroup.getChildAt(position);
        childView.setChecked(true);
        int left = childView.getLeft();
        int right = childView.getRight();
        int width = getWindow().getDecorView().getWidth();
        int x = left - width / 2 + (right - left) / 2;
        mHorizontalScrollView.scrollTo(x, 0);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    /**
     * 两次返回键退出
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else
            {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.lizhinews.ui/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.lizhinews.ui/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
