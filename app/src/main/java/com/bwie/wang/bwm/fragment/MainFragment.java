package com.bwie.wang.bwm.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.activity.ProfileActivity;
import com.bwie.wang.bwm.activity.WalletActivity;
import com.bwie.wang.bwm.activity.find.FindActivity;
import com.bwie.wang.bwm.activity.find.FindAddressActivity;
import com.bwie.wang.bwm.bean.Main_Profile_Bean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements IView {
    private View view;
    @BindView(R.id.Main_Name)
    TextView mMainName;
    /**
     * 个人资料
     */
    @BindView(R.id.Main_Profile_Text)
    TextView mMainProfileText;
    /**
     * 我的圈子
     */
    @BindView(R.id.Main_Find_Text)
    TextView mMainFindText;
    /**
     * 我的足迹
     */
    @BindView(R.id.Main_Foot_Text)
    TextView mMainFootText;
    /**
     * 我的钱包
     */
    @BindView(R.id.Main_Wallet_Text)
    TextView mMainWalletText;
    /**
     * 我的收货地址
     */
    @BindView(R.id.Main_Address_Text)
    TextView mMainAddressText;
    /**
     * 我的头像
     */
    @BindView(R.id.Main_Head_Image)
    SimpleDraweeView mMainHeadImage;
    IPrenserterImp mIPrenserterImp;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        mIPrenserterImp = new IPrenserterImp(this);
        //获得头像与名字
        initHead_Name();
        return view;
    }

    /**
     * 我的圈子
     */
    @OnClick({R.id.Main_Profile_Text, R.id.Main_Find_Text, R.id.Main_Wallet_Text, R.id.Main_Address_Text})
    public void initFind(View view) {
        switch (view.getId()) {
            case R.id.Main_Profile_Text:
                /**个人点击事件
                 */
                //网络请求
                mIPrenserterImp.startGet(APis.MAIN_PROFILE, Main_Profile_Bean.class);
                break;
            case R.id.Main_Find_Text:
                Intent intent_FindActivity = new Intent(getActivity(), FindActivity.class);
                startActivity(intent_FindActivity);
                break;
            case R.id.Main_Wallet_Text:
                Intent intent_WalletActivity = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent_WalletActivity);
                break;
            case R.id.Main_Address_Text:
                /**我的地址*/
                Intent intent_FindAddressActivity = new Intent(getActivity(), FindAddressActivity.class);
                startActivity(intent_FindAddressActivity);
                break;
            default:
                break;
        }
    }

    /**
     * 获取登录成功后的头像和名字
     */
    private void initHead_Name() {
        Intent intent = getActivity().getIntent();
        String headPic = intent.getStringExtra("headPic");
        String nickName = intent.getStringExtra("nickName");

        Uri parse = Uri.parse(headPic);
        mMainHeadImage.setImageURI(parse);
        mMainName.setText(nickName);
    }

    /**
     * 获得数据
     *
     * @param
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Main_Profile_Bean) {
            Main_Profile_Bean mainProfileBean = (Main_Profile_Bean) o;
            //获取到头像，昵称，密码传值
            Main_Profile_Bean.ResultBean profileBeanResult = mainProfileBean.getResult();

            String headPic = profileBeanResult.getHeadPic();
            String nickName = profileBeanResult.getNickName();
            String password = profileBeanResult.getPassword();
            //传值条状到我的信息页面
            Intent intentProfit = new Intent(getActivity(), ProfileActivity.class);
            Intent profit_headPic = intentProfit.putExtra("Profit_headPic", headPic);
            Intent profit_nickName = intentProfit.putExtra("Profit_nickName", nickName);
            Intent profit_password = intentProfit.putExtra("Profit_password", password);
            //跳转到个人信息
            getActivity().startActivity(intentProfit);
        }
    }

    /**
     * 数据获取失败
     *
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "主人,自己的页面出错了哟", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mIPrenserterImp.onDelet();
    }
}