package com.bwie.wang.bwm.Until;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;

/**
 * @author lenovo
 *         自定义View
 *         加减数量
 */
public class Shop_Car_Add_Reduce_View extends RelativeLayout implements View.OnClickListener {
    private EditText mEditText;
    private TextView mJianText;
    private TextView mAddText;
    private TextView mNumEdit;

    public Shop_Car_Add_Reduce_View(Context context) {
        super(context);
        init(context);
    }

    public Shop_Car_Add_Reduce_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Shop_Car_Add_Reduce_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context;
        //获取展示视图
        View view = View.inflate(context, R.layout.shop_car_add_reduce_view, null);
        //获取资源ID
        mJianText = (TextView) view.findViewById(R.id.jian_car);
        mAddText = (TextView) view.findViewById(R.id.add_car);
        mNumEdit = (TextView) view.findViewById(R.id.edit_shop_car);
        mJianText.setOnClickListener(this);
        mAddText.setOnClickListener(this);
        addView(view);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    int num;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_car:
                num++;
                mNumEdit.setText(num + "");
                break;
            case R.id.jian_car:
                if (num > 1) {
                    num--;
                } else {
                    Toast.makeText(context, "默认为1", Toast.LENGTH_SHORT).show();
                }
                mNumEdit.setText(num + "");
                break;
            default:
                break;
        }
    }
}