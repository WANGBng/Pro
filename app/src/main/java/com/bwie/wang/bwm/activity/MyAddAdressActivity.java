package com.bwie.wang.bwm.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.bean.login.RegistBean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;

public class MyAddAdressActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private EditText mAddressee;
    private EditText mAddPhone;
    private EditText mAddRegion;
    private ImageView mChoseRegion;
    private EditText mAddDetailedAddress;
    private EditText mAllItemCode;
    /**
     * 保存并使用
     */
    private Button mAddSaveUse;
    private CityPicker mCityPicker;
    IPrenserterImp mIPrenserterImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add__adress);
        initView();
        mIPrenserterImp=new IPrenserterImp(this);
    }

    private void initView() {
        mAddressee = (EditText) findViewById(R.id.Addressee);
        mAddPhone = (EditText) findViewById(R.id.AddPhone);
        mAddRegion = (EditText) findViewById(R.id.AddRegion);
        mChoseRegion = (ImageView) findViewById(R.id.ChoseRegion);
        mChoseRegion.setOnClickListener(this);
        mAddDetailedAddress = (EditText) findViewById(R.id.AddDetailedAddress);
        mAllItemCode = (EditText) findViewById(R.id.AddItemCode);
        mAddSaveUse = (Button) findViewById(R.id.Add_Save_use);
        mAddSaveUse.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ChoseRegion:
                //选择所在地区
                initChoseRegion();
                mCityPicker.show();
                break;
            case R.id.Add_Save_use:
                initSave();
                break;
        }
    }

    private void initSave() {
        //得到输入的值:收件人、手机号、所在地区、详细地址、邮政编码
        String inputAddressee = mAddressee.getText().toString();
        String inputPhone = mAddPhone.getText().toString();
        String inputAddRegion = mAddRegion.getText().toString();
        String inputAddDetailedAddress = mAddDetailedAddress.getText().toString();
        String inputCode = mAllItemCode.getText().toString();

        String REGEX_PHONE="[1][3458]\\d{9}";
        String REGEX_CODE="[0-9]{6}";
        if (!inputPhone.matches(REGEX_PHONE)){
            Toast.makeText(this, "手机格式不对", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!inputCode.matches(REGEX_CODE)){
            Toast.makeText(this, "邮编格式不对", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!inputAddressee.matches("") &&inputPhone.matches(REGEX_PHONE) &&!inputAddRegion.matches("") &&!inputAddDetailedAddress.matches("") &&inputCode.matches(REGEX_CODE)){
            //将地址保存
            HashMap<String, String> map = new HashMap<>();
            map.put("realName",inputAddRegion);
            map.put("phone",inputPhone);
            map.put("address",inputAddressee+inputAddDetailedAddress);
            map.put("zipCode",inputCode);
            // map存值，进行网络请求
            mIPrenserterImp.startRequest(map, APis.ADD_ADRESS,RegistBean.class);
        }
    }

    /**
     *  第三方选择所在地区：
     *      1.导入依赖： compile 'liji.library.dev:citypickerview:1.1.0'
     */
    private void initChoseRegion() {
        //开头标题
//滑轮文字的颜色
//省滑轮是否循环显示
//市滑轮是否循环显示
//地区（县）滑轮是否循环显示
//滑轮显示的item个数
//滑轮item间距
        mCityPicker = new CityPicker.Builder(this)
                .textSize(20)
                //开头标题
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滑轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省滑轮是否循环显示
                .provinceCyclic(false)
                //市滑轮是否循环显示
                .cityCyclic(false)
                //地区（县）滑轮是否循环显示
                .districtCyclic(false)
                //滑轮显示的item个数
                .visibleItemsCount(7)
                //滑轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        //监听
        mCityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... strings) {
                //省
                String province = strings[0];
                //市
                String city = strings[1];
                //区。县。（两级联动，必须返回空）
                String district = strings[2];
                //邮证编码
                String code = strings[3];
                mAddRegion.setText(province + city + district+code);
            }

            @Override
            public void onCancel() {

            }
        });
    }


    @Override
    public void setData(Object o) {

    }

    @Override
    public void setError(String error) {

    }
}
