package com.bwie.wang.bwm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.MineCallBack;
import com.bwie.wang.bwm.Until.OkHttpUntils;
import com.bwie.wang.bwm.activity.find.MyCallBack;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wangbingjun
 */
public class ProfileActivity extends AppCompatActivity implements IView{
    @BindView(R.id.Profile_head_Image)
    SimpleDraweeView mProfileHeadImage;
    @BindView(R.id.Profile_Name)
    TextView mProfileName;
    @BindView(R.id.Profile_Password)
    TextView mProfilePassword;
    @BindView(R.id.back_but)
    Button back_but;

    private PopupWindow mPopupWindow;
    private TextView mModify;
    private TextView mPhotograph;
    Unbinder unbinder;
    View view;
    View self;

    EditText oldName;

    MineCallBack mineCallBack;
    String profitNickname;

    IPrenserterImp iPrenserterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);
        //赋值
        initVelue();

        //设置popupwindow
        initPopup();
        //从相册修改
        initModify();

//        iPrenserterImp= new IPrenserterImp();
    }

    /**
     * 获取值
     */
    private void initVelue() {
        //得到传的值
        Intent intent = getIntent();

        String profit_headPic = intent.getStringExtra("Profit_headPic");

        profitNickname = intent.getStringExtra("Profit_nickName");
        String profit_password = intent.getStringExtra("Profit_password");
//        赋值
        Uri parse = Uri.parse(profit_headPic);
        mProfileHeadImage.setImageURI(parse);
        mProfileName.setText(profitNickname);
        mProfilePassword.setText(profit_password);
    }

    /**
     * 设置popupwindow
     */
    private void initPopup() {
        //加载popupWindow的子布局

        view = View.inflate(this, R.layout.profileactivity_head_popupwindow, null);

        self = View.inflate(this, R.layout.alertdialog_update_name_layout, null);
        //通过子布局中的到ID//获取popupWindow中的控件
        mModify = (TextView) view.findViewById(R.id.Modify_Head);
        mPhotograph = (TextView) view.findViewById(R.id.Photograph);
        oldName = (EditText) findViewById(R.id.oldName);

        //1.创建popupwindow   contentView 子布局  width,宽   height 高 int width = 400;
        int width = 400;
        mPopupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置焦点
        mPopupWindow.setFocusable(true);
        //设置背景  ColorDrawable(int) 颜色背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
        //设置是否可以触摸
        mPopupWindow.setTouchable(true);
    }

    /**
     * 点击头像，弹出pubwindow
     */
    @OnClick({R.id.Profile_head_Image, R.id.Profile_Name,R.id.back_but})
    public void headImage(View v) {
        switch (v.getId()) {
            case R.id.Profile_head_Image:
                mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                break;
            case R.id.Profile_Name:
                Toast.makeText(this, "ssss", Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("修改昵称")
                        .setView(self)
                        .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (mineCallBack!=null){

                                }
                            }
                        }).create().show();
                break;
            case R.id.back_but:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
    /**
     * 从相册修改
     */
    public void initModify() {
        mModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpick(v);
                mPopupWindow.dismiss();
            }
        });
//      这是相机
        mPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用android自带的照相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                startActivityForResult(intent, 1);
                mPopupWindow.dismiss();
            }
        });


    }

    //打开相册
    public void openpick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //设置图片的格式
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //调取裁剪
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //得到相册图片的路径
            Uri uri = data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //将图片进行返回
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);

        }else {
            if (requestCode == 1) {
                // 拍照

                Bundle extras = data.getExtras();
                Bitmap photoBit = (Bitmap) extras.get("data");
                Bitmap option = BitmapOption.bitmapOption(photoBit, 5);
//                Profile_head_Image.setImageBitmap(option);

//                mProfileHeadImage.setImageURI();

                saveBitmap2file(option, "image/*");
                final File file = new File("/sdcard/" + "image/*");

                Log.e("TAG", "file333333333333333   " + file.getName());
                //开始联网上传的操作

            }
        }

        setImageInt(requestCode, resultCode, data);
    }

    private void setImageInt(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            mProfileHeadImage.setImageBitmap(bitmap);
            // 将图片保存到......

        }
    }

    static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

    @Override
    public void setData(Object o) {
//        setImageInt(requestCode, resultCode, data);
    }

    @Override
    public void setError(String error) {

    }

    public static class BitmapOption {

        private static final BitmapOption bitmapOption = new BitmapOption();

        private BitmapOption() {
        }

        public static BitmapOption getBitmapOption() {
            return bitmapOption;
        }


        public static Bitmap bitmapOption(Bitmap image, int size) {

            int btm=1024;

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 85, out);
            float zoom = (float) Math.sqrt(size * 1024 / (float) out.toByteArray().length);
            Matrix matrix = new Matrix();
            matrix.setScale(zoom, zoom);
            Bitmap result = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
            while (out.toByteArray().length > size * btm) {
                System.out.println(out.toByteArray().length);
                matrix.setScale(0.9f, 0.9f);
                result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
                out.reset();
                result.compress(Bitmap.CompressFormat.JPEG, 85, out);
            }
            return result;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}