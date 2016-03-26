package example.ruanjian.stocksystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.activity.SwitchAccountActivity;

public class ChangeAccountAdapter extends BaseAdapter
{
    private SwitchAccountActivity _changeAccountActivity;

    public ChangeAccountAdapter(SwitchAccountActivity changeAccountActivity)
    {
        this._changeAccountActivity = changeAccountActivity;
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
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(_changeAccountActivity).inflate(R.layout.change_account_item, null);
        ImageView iconImage = (ImageView) view.findViewById(R.id.changeAccount_item_image);
        TextView accountNameTxt = (TextView) view.findViewById(R.id.changeAccount_item_accountNameTxt);
        AccountInfo accountInfo = (AccountInfo) getItem(position);
        accountNameTxt.setText(accountInfo.get_accountName());
        Bitmap bitmap = AccountUtils.getBitmapByIconPath(accountInfo.get_userIconPath(), _changeAccountActivity);
        if (bitmap != null)
        {
             iconImage.setImageBitmap(StockSystemUtils.toRoundBitmap(bitmap));
        }
        return view;
    }



}
