package com.bwie.wang.bwm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.wang.bwm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WalletActivity extends AppCompatActivity {

    @BindView(R.id.my_waller_price)
    TextView myWallerPrice;
    @BindView(R.id.my_wallet_list_view)
    ListView myWalletListView;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        unbinder = ButterKnife.bind(this);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
