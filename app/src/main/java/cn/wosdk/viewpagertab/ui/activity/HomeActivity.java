package cn.wosdk.viewpagertab.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wosdk.viewpagertab.R;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_two)
    Button btnTwo;
    @BindView(R.id.btn_three)
    Button btnThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_one)
    void newActivity(View view) {

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_two)
    void twoActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, TwoActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.btn_three)
    void threeActivity(View view) {

        Intent intent = new Intent(HomeActivity.this, ThreeActivity.class);
        startActivity(intent);
    }
}
