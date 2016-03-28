package example.ruanjian.stocksystem.adapter;

import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.LoginActivity;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.asyncTask.DeleteAccountAsyncTask;

public class AccountAdapter extends BaseAdapter
{
    private LoginActivity _loginActivity;

    public AccountAdapter(LoginActivity loginActivity)
    {
        this._loginActivity = loginActivity;
    }

    @Override
    public int getCount()
    {
        return AccountUtils.getAllAccountInfo().size();
    }

    @Override
    public Object getItem(int position)
    {
        return AccountUtils.getAllAccountInfo().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(_loginActivity).inflate(R.layout.login_popup_account_item, null);
        Button deleteBtn = (Button) view.findViewById(R.id.accountItemDeleteBtn);
        final AccountInfo accountInfo = (AccountInfo) getItem(position);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                new DeleteAccountAsyncTask(_loginActivity).execute(accountInfo.get_accountName());
            }
        });
        TextView accountNameTxt = (TextView) view.findViewById(R.id.accountItemNameTxt);
        accountNameTxt.setText(accountInfo.get_accountName());
        return view;
    }
}
