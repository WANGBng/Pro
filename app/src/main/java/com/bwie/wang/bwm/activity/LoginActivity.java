package com.bwie.wang.bwm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.fragment.FindFragment;
import com.bwie.wang.bwm.fragment.HomeFragment;
import com.bwie.wang.bwm.fragment.ListFragment;
import com.bwie.wang.bwm.fragment.MainFragment;
import com.bwie.wang.bwm.fragment.ShopCarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.Login_View)
    ViewPager mViewPager;
    @BindView(R.id.Radio_Shop_Car)
    RadioButton mRadioShopCar;
    @BindView(R.id.Radio_Group)
    public RadioGroup mRadioGroup;
    private List<Fragment> mjihe;
    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);

        boolean netIsConnection = isNetConnection();
        if (netIsConnection) {
            // 获取网络数据
            initViewPager();
        } else {
            // 弹出对话框
            new AlertDialog
                    .Builder(this)
                    .setTitle("提示！")
                    .setMessage("网络未连接，是否打开网络")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        initRadio();
    }
    /**
     *  添加fragment、适配器
     */
    private void initViewPager() {
        mjihe=new ArrayList<>();
        //添加fragment
        mjihe.add(new HomeFragment());
        mjihe.add(new FindFragment());
        mjihe.add(new ShopCarFragment());
        mjihe.add(new ListFragment());
        mjihe.add(new MainFragment());
        //适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mjihe.get(i);
            }

            @Override
            public int getCount() {
                return mjihe.size();
            }
        });
    }

    /**
     *  切换按钮，fragmnt改变
     */
    private void initRadio() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Radio_Home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.Radio_Find:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.Radio_Shop_Car:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.Radio_List:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.Radio_Main:
                        mViewPager.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    private boolean isNetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this,"我没网络",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return networkInfo.isAvailable();
        }
    }
}