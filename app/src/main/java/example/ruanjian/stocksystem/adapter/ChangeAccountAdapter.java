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
import example.ruanjian.stocksystem.activity.SwitchAccountActivity;
import example.ruanjian.stocksystem.application.StockSystemApplication;

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
        return AccountUtils.getAllAccountInfo().size() - 1;//自己不算在内
    }

    @Override
    public Object getItem(int position)
    {
        int index = position;
        if (position >= AccountUtils.curLoginPosition)
        {
            index++;
        }
        return AccountUtils.getAllAccountInfo().get(index);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder;
        View view;
        if (convertView == null)
        {
            view = LayoutInflater.from(_changeAccountActivity).inflate(R.layout.change_account_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iconImage = (ImageView) view.findViewById(R.id.changeAccount_item_image);
            viewHolder.accountNameTxt = (TextView) view.findViewById(R.id.changeAccount_item_accountNameTxt);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AccountInfo accountInfo = (AccountInfo) getItem(position);
        viewHolder.accountNameTxt.setText(accountInfo.get_accountName());
        Bitmap bitmap = AccountUtils.getBitmapByIconPath(accountInfo.get_userIconPath(), _changeAccountActivity);
        if (bitmap != null)
        {
            viewHolder.iconImage.setImageBitmap(StockSystemApplication.getInstance().toRoundBitmap(bitmap));
        }
        else
        {
            viewHolder.iconImage.setImageResource(R.drawable.icon);
        }
        return view;
    }


    class ViewHolder
    {
        protected ImageView iconImage;
        protected TextView accountNameTxt;
    }


}
