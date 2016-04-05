package example.ruanjian.stocksystem.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.asyncTask.RegisterAccountAsyncTask;

public class RegisterActivity extends BaseActivity {

    private Button _registerBtn;
    private EditText _passwordTxt;
    private EditText _accountNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
    }

    private void initView()
    {
        _registerBtn = (Button) findViewById(R.id.startRegisterBtn);
        _passwordTxt = (EditText) findViewById(R.id.register_passwordTxt);
        _accountNameTxt = (EditText) findViewById(R.id.register_account_numberTxt);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterAccountAsyncTask(RegisterActivity.this).execute(_accountNameTxt.getText().toString(), _passwordTxt.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
