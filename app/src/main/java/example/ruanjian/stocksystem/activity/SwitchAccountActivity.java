package example.ruanjian.stocksystem.activity;

import android.view.View;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView;


import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.adapter.ChangeAccountAdapter;

public class SwitchAccountActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView _accountListView;
    private ChangeAccountAdapter _changeAccountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_account_activity);
        initView();
    }

    private void initView()
    {
        _changeAccountAdapter = new ChangeAccountAdapter(this);
        _accountListView = (ListView) findViewById(R.id.changeAccount_listView);
        _accountListView.setAdapter(_changeAccountAdapter);
        _accountListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        AccountInfo accountInfo = (AccountInfo) parent.getItemAtPosition(position);
        AccountUtils.updateLoginAccount(accountInfo.get_accountName(), accountInfo.get_password());
        AccountUtils.setCurLoginAccountInfo(accountInfo);
        finish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


}
