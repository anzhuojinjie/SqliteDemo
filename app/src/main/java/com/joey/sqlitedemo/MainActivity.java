package com.joey.sqlitedemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = ((TextView) findViewById(R.id.tv_result));
        et = ((EditText) findViewById(R.id.et));
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                clearEditTextFocus();
                break;
            case R.id.btn_add:
                LianxirenBean lxr = new LianxirenBean();
                lxr.setName(et.getText().toString());
                lxr.setNumber("1525305213" + (int) (1 + Math.random() * (10 - 1 + 1)));
                lxr.setIntroduce("同学");
                (new LianxirenOperator(MainActivity.this)).add(lxr);
                break;
            case R.id.btn_del:
                (new LianxirenOperator(MainActivity.this)).delete(et.getText().toString());
                break;
            case R.id.btn_del_all:
                (new LianxirenOperator(MainActivity.this)).deleteAll();
                break;
            case R.id.btn_edit:
                LianxirenBean lxr2 = new LianxirenBean();
                lxr2.setName(et.getText().toString());
                lxr2.setNumber("110");
                lxr2.setIntroduce("这是报警热线");
                (new LianxirenOperator(MainActivity.this)).update(lxr2);
                break;
            case R.id.btn_query:
                LianxirenBean lxr1 = (new LianxirenOperator(MainActivity.this)).queryOne(et.getText().toString());
                if (null != lxr1 && !TextUtils.isEmpty(lxr1.getName()))
                    tvResult.setText(lxr1.toString());
                else
                    tvResult.setText("查无此人");
                break;
            case R.id.btn_query_all:
                List<LianxirenBean> list = (new LianxirenOperator(MainActivity.this)).queryMany();
                StringBuilder stringBuilder = new StringBuilder();
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        stringBuilder = stringBuilder.append(list.get(i).toString());
                    }
                    tvResult.setText(stringBuilder.toString());
                } else {
                    tvResult.setText("暂无数据");
                }

                break;
            default:
                break;
        }
    }


    private void clearEditTextFocus() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
    }
}
