package example.ruanjian.stocksystem.adapter;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.activity.LoginActivity;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.manager.AlertDialogManager;
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
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(_loginActivity).inflate(R.layout.login_popup_account_item, null);
            viewHolder = new ViewHolder();
            viewHolder.deleteBtn = (Button) view.findViewById(R.id.accountItemDeleteBtn);
            viewHolder.accountNameTxt = (TextView) view.findViewById(R.id.accountItemNameTxt);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AccountInfo accountInfo = (AccountInfo) getItem(position);
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog alertDialog = AlertDialogManager.getAlertDialog(_loginActivity, _loginActivity.getString(R.string.deleteAccount));
                new DeleteAccountAsyncTask(_loginActivity, alertDialog).execute(accountInfo.get_accountName());
            }
        });
        viewHolder.accountNameTxt.setText(accountInfo.get_accountName());
        return view;
    }

    class ViewHolder
    {
        protected Button deleteBtn;
        protected TextView accountNameTxt;
    }


}
