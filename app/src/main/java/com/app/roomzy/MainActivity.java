package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.app.roomzy.Adapter.HomePageApdater;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomePageApdater homePageApdater;
    ActivityMainBinding mainBinding;
    private MenuItem prevMenuItem;
    CartDBManager cartDBManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        homePageApdater = new HomePageApdater(getSupportFragmentManager());
        mainBinding.viewpager.setAdapter(homePageApdater);
        mainBinding.viewpager.setOffscreenPageLimit(0);
        mainBinding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        Handler handler = new Handler();
        cartDBManager = new CartDBManager(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainBinding.splashLayout.setVisibility(View.GONE);
            }
        },3500);
//        mainBinding.viewpager.setPageTransformer(true,new ZoomOutTransformation());
        handleBottomNav();
    }
    private void handleBottomNav(){
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {
                    case R.id.home:
                        mainBinding.viewpager.setCurrentItem(0);
                        mainBinding.title.setText("Trang Chủ");
                        break;

                    case R.id.proposal:
                        mainBinding.viewpager.setCurrentItem(1);
                        mainBinding.title.setText("Đề xuất cho bạn ❤");
                        break;

                    case R.id.search:
                        mainBinding.viewpager.setCurrentItem(2);
                        mainBinding.title.setText("Tìm kiếm");
                        break;

                    case R.id.profile:
                        mainBinding.viewpager.setCurrentItem(3);
                        mainBinding.title.setText("Thông tin cá nhân");
                        break;

                }
                return false;
            }
        });
        mainBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    mainBinding.bottomNavigation.getMenu().getItem(0).setChecked(false);

                mainBinding.bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mainBinding.bottomNavigation.getMenu().getItem(position);

                switch(prevMenuItem.getItemId()) {
                    case R.id.home:
                        mainBinding.viewpager.setCurrentItem(0);
                        mainBinding.title.setText("Trang Chủ");
                        break;
                    case R.id.proposal:
                        mainBinding.viewpager.setCurrentItem(1);
                        mainBinding.title.setText("Đề xuất cho bạn ❤");
                        break;
                    case R.id.search:

                        mainBinding.viewpager.setCurrentItem(2);
                        mainBinding.title.setText("Tìm kiếm");
                        break;

                    case R.id.profile:
                        mainBinding.viewpager.setCurrentItem(3);
                        mainBinding.title.setText("Thông tin cá nhân");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}