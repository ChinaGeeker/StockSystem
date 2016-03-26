package example.ruanjian.stocksystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

import java.util.List;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.info.HistoryRecordInfo;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;

public class HistoryAdapter extends BaseAdapter
{
    private Context _context;

    private List<HistoryRecordInfo> _historyRecordInfos;

    public HistoryAdapter(Context _context)
    {
        this._context = _context;
        refreshList(false);
    }

    public void refreshList(boolean isDataChanged)
    {
        String accountNameStr = "";
        AccountInfo accountInfo =  AccountUtils.getCurLoginAccountInfo();

        if (accountInfo != null)
        {
            accountNameStr = accountInfo.get_accountName();
        }
        _historyRecordInfos =  HistoryBrowsingUtils.getRecordByAccountName(accountNameStr);
        if (isDataChanged == true)
        {
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount()
    {
        return _historyRecordInfos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return _historyRecordInfos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(_context).inflate(R.layout.histoty_item, null);
        TextView textView = (TextView) view.findViewById(R.id.history_item_Txt);
        HistoryRecordInfo historyRecordInfo = (HistoryRecordInfo) getItem(position);
        textView.setText(historyRecordInfo.get_recordDetail());
        return view;
    }


}
