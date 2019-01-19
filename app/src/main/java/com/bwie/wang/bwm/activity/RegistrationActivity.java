package com.bwie.wang.bwm.activity;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.bean.login.RegistBean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegistrationActivity extends AppCompatActivity implements IView {
    /**
     * 手机号
     */
    @BindView(R.id.Regist_Number)
    EditText mRegistNumber;
    /**
     * 验证码
     */
    @BindView(R.id.Code)
    EditText mCode;
    /**
     * 登录密码
     */
    @BindView(R.id.Regist_Password)
    EditText mRegistPassword;
    @BindView(R.id.Regist_Eye)
    ImageView mRegistEye;
    /**
     * 已有账户？立即登陆
     */
    @BindView(R.id.Regist_Login)
    TextView mRegistLogin;
    /**
     * 注册
     */
    @BindView(R.id.Regist)
    public Button mRegist;
    IPrenserterImp mIPrenserterImp;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //绑定
        unbinder = ButterKnife.bind(this);
        mIPrenserterImp=new IPrenserterImp(this);
        //沉浸式
        //initimmersive();
    }

    /**
     *  点击注册按钮
     */
    @OnClick(R.id.Regist)
    public void setRegist(){
        HashMap<String, String> map = new HashMap<>();
        //得到输入框的值
        String input_Number = mRegistNumber.getText().toString();
        String input_Password = mRegistPassword.getText().toString();
        if (!input_Number.matches("[1][3458]\\d{9}")){
            Toast.makeText(this,"手机格式不对",Toast.LENGTH_LONG).show();
        }
        if (input_Password.length()<=0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
        }
        if (input_Number.matches("[1][3458]\\d{9}")&&input_Password.length()>0) {
            map.put("phone", input_Number);
            map.put("pwd", input_Password);
            //网络请求
            mIPrenserterImp.startRequest(map, APis.REGIST_NET, RegistBean.class);
        }
    }

    /**
     *  得到请求的数据
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof RegistBean){
            RegistBean registBean=(RegistBean)o;
            String message = registBean.getMessage();
            if (message.equals("注册成功")){
                String name = mRegistNumber.getText().toString();
                String password = mRegistPassword.getText().toString();
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new EventBusMassage(name, password));
                finish();
            }else if (message.equals("密码不能为空")){
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                return;
            }else if (message.equals("该手机号已注册，不能重复注册！")){
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                return;
            }else if (message.equals("手机号格式错误")){
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }


    /**
     * 密文明文更改
     */
    // 输入框密码是否是隐藏的，默认为true
    private boolean isHideFirst = true;
    @OnClick(R.id.Regist_Eye)
    public void LoginEye(){
        if (isHideFirst == true) {
            mRegistEye.setImageResource(R.mipmap.login_icon_eye_n_hdhpi);
            //密文
            HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
            mRegistPassword.setTransformationMethod(method1);
            isHideFirst = false;
        } else {
            mRegistEye.setImageResource(R.mipmap.login_icon_eye_n_hdhpi);
            //密文
            TransformationMethod method = PasswordTransformationMethod.getInstance();
            mRegistPassword.setTransformationMethod(method);
            isHideFirst = true;
        }
        // 光标的位置
        int index = mRegistPassword.getText().toString().length();
        mRegistPassword.setSelection(index);

    }

    /**
     *  已有账号，立即登录
     */
    @OnClick(R.id.Regist_Login)
    public void setRegistLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mIPrenserterImp!=null){
            mIPrenserterImp.onDelet();
        }
    }
}