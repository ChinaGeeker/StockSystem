package example.ruanjian.stocksystem.fragment;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.support.annotation.Nullable;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.info.AccountStockInfo;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.adapter.StockListAdapter;
import example.ruanjian.stocksystem.interce.IFragmentHandler;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;
import example.ruanjian.stocksystem.activity.StockInfoActivity;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.asyncTask.AddStockAsyncTask;
import example.ruanjian.stocksystem.asyncTask.GetAccountStockAsyncTask;

public class StockListFragment extends BaseFragment implements View.OnClickListener
{

    private View _view;
    private TextView _warnTxt;
    private ListView _listView;
    private Button _addStockBtn;

    private EditText _stockCodeTxt;

    private IFragmentHandler iFragmentHandler;

    private StockListAdapter _stockListAdapter;

    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void loading()
    {
        if (isInited == true || isVisible == true)
        {
            new GetAccountStockAsyncTask((StockMainActivity)getActivity()).execute();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void refreshListView()
    {
        if (_stockListAdapter != null)
        {
            _stockListAdapter.refreshList(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (_view == null)
        {
            _view = inflater.inflate(R.layout.stock_list_fragment, null, false);
            isInited = true;
            _warnTxt = (TextView) _view.findViewById(android.R.id.empty);
            _stockListAdapter = new StockListAdapter(getActivity(), _warnTxt);

            _listView = (ListView) _view.findViewById(android.R.id.list);
            _listView.setAdapter(_stockListAdapter);
            _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if (iFragmentHandler != null)
                    {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), StockInfoActivity.class);
                        AccountStockInfo accountStockInfo = (AccountStockInfo) parent.getItemAtPosition(position);
                        StockInfo stockInfo = StockUtils.getStockInfoByStockNumber(accountStockInfo.get_number());
                        HistoryBrowsingUtils.saveRecord(stockInfo, StockSystemConstant.HISTORY_VIEW_TYPE);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(StockSystemConstant.STOCK_INFO, stockInfo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });
            _addStockBtn = (Button) _view.findViewById(R.id.stock_list_fragment_addStockbtn);
            _addStockBtn.setOnClickListener(this);
            _stockCodeTxt = (EditText) _view.findViewById(R.id.stock_list_fragment_inputTxt);
        }
        return _view;

    }

    @Override
    public void onClick(View v)
    {
        String stockCodeStr = _stockCodeTxt.getText().toString();
        if (stockCodeStr.equals(""))
        {
            Toast.makeText(getActivity(), R.string.inputStockCode, Toast.LENGTH_LONG).show();
            return;
        }
        if (AccountStockUtils.isExistByStockNumber(stockCodeStr))
        {
            Toast.makeText(getActivity(), R.string.existStock, Toast.LENGTH_LONG).show();
            return;
        }
        if (StockSystemUtils.isConnectedNetWork(getActivity()) == true)
        {
            new AddStockAsyncTask((StockMainActivity)getActivity()).execute(stockCodeStr);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            iFragmentHandler = (IFragmentHandler)getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
        } catch (Exception e) {
            e.printStackTrace();
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


}
