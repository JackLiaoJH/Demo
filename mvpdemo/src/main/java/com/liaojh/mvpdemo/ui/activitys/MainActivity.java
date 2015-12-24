package com.liaojh.mvpdemo.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liaojh.mvpdemo.R;
import com.liaojh.mvpdemo.presenter.UserPresenter;
import com.liaojh.mvpdemo.ui.commom.BaseActivity;
import com.liaojh.mvpdemo.ui.views.IUserView;


public class MainActivity extends BaseActivity implements IUserView, View.OnClickListener {

    private EditText mEtName;
    private EditText mEtSex;
    private Button mBtnSave, mBtnRead;

    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserPresenter = new UserPresenter(this, this);
        mEtName = findView(R.id.et_name);
        mEtSex = findView(R.id.et_sex);
        mBtnSave = findView(R.id.btn_save);
        mBtnRead = findView(R.id.btn_read);

        mBtnSave.setOnClickListener(this);
        mBtnRead.setOnClickListener(this);
    }

    @Override
    public String getName() {
        return mEtName.getText().toString();
    }

    @Override
    public String getSex() {
        return mEtSex.getText().toString();
    }

    @Override
    public void setName(String name) {
        mEtName.setText(name);
    }

    @Override
    public void setSex(String sex) {
        mEtSex.setText(sex);
    }

    @Override
    public void clear() {
        mEtName.setText("");
        mEtSex.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                mUserPresenter.saveUser();
                break;
            case R.id.btn_read:
                mUserPresenter.getUser();
                break;
        }
    }
}
