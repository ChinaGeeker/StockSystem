package example.ruanjian.stocksystem.adapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;
import android.content.Context;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.activity.StockInfoActivity;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.info.AccountStockInfo;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.utils.StockUtils;

public class StockLetterListAdapter extends BaseAdapter implements SectionIndexer
{

    private Context _context;
    private TextView _warnTxt;


    private List<String> _letterArr = new ArrayList<>();
    private List<AccountStockInfo> _accountStockInfoList;

    public StockLetterListAdapter(Context context, TextView warnTxt)
    {
        _warnTxt = warnTxt;
        this._context = context;
        refreshList(false);
    }

    public void refreshList(boolean isDataChanged)
    {
        _letterArr.clear();
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        _accountStockInfoList = AccountStockUtils.getListByAccountName(accountName);
        Collections.sort(_accountStockInfoList, new Comparator<AccountStockInfo>() {
            @Override
            public int compare(AccountStockInfo lhs, AccountStockInfo rhs) {
                return lhs.get_letter().compareToIgnoreCase(rhs.get_letter());
            }
        });
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
        final AccountStockInfo accountStockInfo = (AccountStockInfo) getItem(position);
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(_context).inflate(R.layout.stock_list_letter_item, null,false);
            viewHolder.stockTxt = (TextView) view.findViewById(R.id.stockList_item_stockTxt);
            viewHolder.letterTxt = (TextView) view.findViewById(R.id.stockList_item_letterTxt);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }
        viewHolder.stockTxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(_context, StockInfoActivity.class);
                StockInfo stockInfo = StockUtils.getStockInfoByStockNumber(accountStockInfo.get_number());
                HistoryBrowsingUtils.saveRecord(stockInfo, StockSystemConstant.HISTORY_VIEW_TYPE);
                Bundle bundle = new Bundle();
                bundle.putSerializable(StockSystemConstant.STOCK_INFO, stockInfo);
                intent.putExtras(bundle);
                _context.startActivity(intent);
            }
        });
        String stockName = accountStockInfo.get_stockName();
        viewHolder.stockTxt.setText(stockName);
        String letterStr = accountStockInfo.get_letter();
        addLetter(letterStr);
        int firstIndex = getFirstLetterPosition(letterStr, position);
        if (firstIndex != position)
        {
            viewHolder.letterTxt.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.letterTxt.setVisibility(View.VISIBLE);
            viewHolder.letterTxt.setText(letterStr);
        }
        return view;
    }

    private int getPositionByLetter(String letter)
    {
        if (letter.equals(StockSystemConstant.LETTER_ARRAY[0]))
        {
            return 0;
        }
        int index = _letterArr.indexOf(letter);
        if(index <= -1)
        {
            return index;
        }
        return getFirstLetterPosition(letter, -1);
    }

    private void addLetter(String letter) {
        int index = _letterArr.indexOf(letter);
        if (index <= -1)
        {
            _letterArr.add(letter);
        }
    }

    private int getFirstLetterPosition(String letter, int position)
    {
        int len = _accountStockInfoList.size();
        if (position <= 0)
        {
            position = len;
        }
        for (int index = 0; index < position; index++)
        {
            AccountStockInfo accountStockInfo = _accountStockInfoList.get(index);
            if (accountStockInfo.get_letter().equals(letter))
            {
                return index;
            }
        }
        return position;
    }

    class ViewHolder
    {
        TextView stockTxt;
        TextView letterTxt;
    }

    @Override
    public int getPositionForSection(int sectionIndex)
    {
        String letter = (String) getSections()[sectionIndex];
        return getPositionByLetter(letter);
    }

    @Override
    public Object[] getSections()
    {
        return StockSystemConstant.LETTER_ARRAY;
    }

    @Override
    public int getSectionForPosition(int position)
    {
        return -1; //目前用不到
    }


}