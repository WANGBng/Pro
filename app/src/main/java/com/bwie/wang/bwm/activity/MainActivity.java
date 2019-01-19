package com.bwie.wang.bwm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.bean.login.LoginBean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wangbingjun
 */
public class MainActivity extends AppCompatActivity implements IView{
    /**
     * 手机号
     */
    @BindView(R.id.Login_Number)
     EditText mLoginNumber;
    /**
     * 登录密码
     */
    @BindView(R.id.Login_Password)
    EditText mLoginPassword;
    @BindView(R.id.Login_Eye)
    ImageView mLoginEye;
    /**
     * 记住密码
     */
    @BindView(R.id.Remeber_Password)
    CheckBox mRemeberPassword;
    /**
     * 快速注册
     */
    @BindView(R.id.Rapid_registration)
    TextView mRapidRegistration;
    /**
     * 登录
     */
    @BindView(R.id.Login)
    Button mLogin;

    IPrenserterImp mIPrenserterImp;
    private SharedPreferences shard;
    private SharedPreferences.Editor editor;
    LoginBean mLoginBean;
    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        //得到SharedPreferences
        shard=getSharedPreferences("uuu", MODE_PRIVATE);
        //得到editor
        editor=shard.edit();
        //记住密码的状态
        initRemeber();
    }

    /**
     * 密文明文更改
     */
    // 输入框密码是否是隐藏的，默认为true

    private boolean isHideFirst = true;
    @OnClick(R.id.Login_Eye)
    public void LoginEye(){
        if (isHideFirst == true) {
            mLoginEye.setImageResource(R.mipmap.login_icon_eye_n_hdhpi);
            //密文
            HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
            mLoginPassword.setTransformationMethod(method1);
            isHideFirst = false;
        } else {
            mLoginEye.setImageResource(R.mipmap.login_icon_eye_n_hdhpi);
            //密文
            TransformationMethod method = PasswordTransformationMethod.getInstance();
            mLoginPassword.setTransformationMethod(method);
            isHideFirst = true;
        }
        // 光标的位置
        int index = mLoginPassword.getText().toString().length();
        mLoginPassword.setSelection(index);

    }



    /**
     *  选中登录按钮
     */
    @OnClick(R.id.Login)
    public void Login(){
        initNet();

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void message(EventBusMassage eventBusMassage){
        mLoginNumber.setText(eventBusMassage.phone);
        mLoginPassword.setText(eventBusMassage.pwd);
    }
    /**
     * 对输入的数据请求网络
     */
    private void initNet() {

        HashMap<String, String> map = new HashMap<>();
        //得到输入框的值
        String input_Number = mLoginNumber.getText().toString();
        String input_Password = mLoginPassword.getText().toString();
        String REGEX="[1][3458]\\d{9}";
        if (!input_Number.matches(REGEX)){
            Toast.makeText(this,"手机格式不对",Toast.LENGTH_LONG).show();
        }
        if (input_Password.length()<=0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
        }
        if (input_Number.matches(REGEX)&&input_Password.length()>0){
            //进行网络请求
            map.put("phone",input_Number);
            map.put("pwd",input_Password);
            //P层
            mIPrenserterImp=new IPrenserterImp(this);
            mIPrenserterImp.startRequest(map, APis.MAIN_LOGIN,LoginBean.class);
        }

    }

    /**得到数据
     * @param o
     */
    @SuppressLint("ApplySharedPref")
    @Override
    public void setData(Object o) {
        if (o instanceof LoginBean){
            LoginBean loginBean=(LoginBean)o;
            mLoginBean=loginBean;
            SharedPreferences spDemo = getSharedPreferences("spDemo", MODE_PRIVATE);
            //将userID、sessionId保存
            spDemo.edit().putString("userId",loginBean.getResults().getUserId())
                    .putString("sessionId",loginBean.getResults().getSessionId())
                    .commit();
            String message = loginBean.getMessage();
            if (message.equals("登录成功")){
                //此时可以做跳转操作
                //判断是否记住按钮--跳转
                initIntent();

            }else if (message.equals("请输入密码")){
                Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            }else if (message.equals("请输入手机号")){
                Toast.makeText(this,"请输入手机号",Toast.LENGTH_LONG).show();
            } else  {
                Toast.makeText(this,"请输入",Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    /**
     *  判断是否勾选自己密码，并跳转
     */
    private void initIntent() {
        //如果记住密码勾选
        if(mRemeberPassword.isChecked()){
            //获得输入框的值
            String name = mLoginNumber.getText().toString();
            String pass = mLoginPassword.getText().toString();
            //将值存入到shard
            editor.putString("name", name);
            editor.putString("pass", pass);
            //存入一个勾选了的状态值
            editor.putBoolean("ji_ischecked", true);
            //提交，此时，输入的值存到shared中，再下次启动时，将值从shared中取出来放到输入框
            //且记住账号为选中状态，
            editor.commit();
        }else{
            editor.clear();
            editor.commit();
        }
        //跳转、传值
        Intent it=new Intent(MainActivity.this,LoginActivity.class);
        it.putExtra("headPic",mLoginBean.getResults().getHeadPic());
        it.putExtra("nickName",mLoginBean.getResults().getNickName());
        //登录凭证
        it.putExtra("sex",mLoginBean.getResults().getSex());
        startActivity(it);
        finish();
    }

    /**
     *  记住勾选
     */
    private void initRemeber() {
        boolean ji_checked = shard.getBoolean("ji_ischecked", false);
        if(ji_checked){
            //获取到shared的值
            String ji_name = shard.getString("name", null);
            String ji_pass = shard.getString("pass", null);
            //放入到输入框
            mLoginNumber.setText(ji_name);
            mLoginPassword.setText(ji_pass);
            //记住账号选中
            mRemeberPassword.setChecked(true);
        }
    }
    /**
     *  请求失败
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(this,"网络请求失败",Toast.LENGTH_LONG).show();
    }

    /**
     *  快速注册
     */
    @OnClick(R.id.Rapid_registration)
    public void regist(){
        //跳转到注册页
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        // 一定要判断一下
        if (mIPrenserterImp!=null){
            mIPrenserterImp.onDelet();
        }
        EventBus.getDefault().unregister(this);

    }
}