package example.ruanjian.stocksystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import java.util.List;
import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.info.AccountStockInfo;
import example.ruanjian.stocksystem.utils.AccountStockUtils;

public class StockListAdapter extends BaseAdapter
{

    private List<AccountStockInfo> _accountStockInfoList;

    protected Context context;
    private TextView _warnTxt;

    public StockListAdapter(Context context, TextView warnTxt)
    {
        _warnTxt = warnTxt;
        this.context = context;
        refreshList(false);
    }

    public void refreshList(boolean isDataChanged)
    {
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        _accountStockInfoList = AccountStockUtils.getListByAccountName(accountName);
        if (isDataChanged == true)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount()
    {
        int len = _accountStockInfoList.size();
        if (len >= 1)
        {
            _warnTxt.setVisibility(View.INVISIBLE);
        }
        else
        {
            _warnTxt.setVisibility(View.VISIBLE);
        }
        return len;
    }

    @Override
    public Object getItem(int position) {
        return _accountStockInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.stock_list_item, null,false);
            viewHolder.stockTxt = (TextView) view.findViewById(R.id.stockItemTxt);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }
        AccountStockInfo accountStockInfo = (AccountStockInfo) getItem(position);
        viewHolder.stockTxt.setText(accountStockInfo.get_stockName());
        return view;
    }


    class ViewHolder
    {
        TextView stockTxt;
    }


}
