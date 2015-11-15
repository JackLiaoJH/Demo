package com.liaojh.animationdemo;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private LinearLayout ll;
    private TextView tv;
    private ImageView imageView;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = (TextView) findViewById(R.id.tv);
        imageView = (ImageView) findViewById(R.id.imageView);
        iv = (ImageView) findViewById(R.id.iv);


        //布局动画(添加view的时候动画效果)   也可以在ViewGroup的xml中添加 android:animateLayoutChanges="true"
        ll= (LinearLayout) findViewById(R.id.ll);
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1);
        sa.setDuration(2000);
        final LayoutAnimationController lac = new LayoutAnimationController(sa,0.5f);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        ll.setLayoutAnimation(lac);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                TextView tv = new TextView(MainActivity.this);
//                tv.setText("ietm " + new Random().nextInt(100));
//                ll.addView(tv);

//                testAnim();

//                testCustomerAnim();

                //使用SVG开启动画
                Drawable drawable = iv.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void testCustomerAnim()
    {
//        TVOffAnimation animation = new TVOffAnimation();
//        animation.setDuration(2000);

        My3DAnimation animation = new My3DAnimation();
        animation.setRotateY(50);
        imageView.startAnimation(animation);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void testAnim()
    {
        ObjectAnimator oa = ObjectAnimator.ofFloat(tv, "translationX", 0, 100)
                                          .setDuration(2000);
        //弹性
//        OvershootInterpolator interpolator = new OvershootInterpolator(5);
        //多次回弹(自由落体)
//        BounceInterpolator interpolator = new BounceInterpolator();
        //往回弹,然后加速
        AnticipateInterpolator interpolator = new AnticipateInterpolator(6);
        oa.setRepeatCount(3);
        oa.setInterpolator(interpolator);
//        oa.setRepeatMode(ObjectAnimator.REVERSE);
        oa.start();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara)
        {
            // Handle the camera action
        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
