package example.ruanjian.stocksystem.fragment;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.support.annotation.Nullable;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.widget.LetterSideBar;
import example.ruanjian.stocksystem.interce.ILetterChange;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.interce.IFragmentHandler;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.asyncTask.AddStockAsyncTask;
import example.ruanjian.stocksystem.adapter.StockLetterListAdapter;
import example.ruanjian.stocksystem.asyncTask.GetAccountStockAsyncTask;

public class StockListFragment extends BaseFragment implements View.OnClickListener, ILetterChange
{

    private View _view;
    private TextView _warnTxt;
    private ListView _listView;
    private TextView _letterTxt;

    private Button _addStockBtn;

    private EditText _stockCodeTxt;
    private LetterSideBar _letterSideBar;
    private IFragmentHandler iFragmentHandler;


    private StockLetterListAdapter _stockLetterListAdapter;

    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void loading()
    {
        if (isInited == true && isVisible == true)
        {
            new GetAccountStockAsyncTask().execute();
        }
    }


    @Override
    public void sectionChange(int sectionIndex)
    {
        int position = _stockLetterListAdapter.getPositionForSection(sectionIndex);
        if(position >= 0)
        {
            _listView.setSelection(position);
        }
    }

   /* @Override
    public void letterChange(String letter)
    {
        int position = _stockLetterListAdapter.getPositionByLetter(letter);
        if(position >= 0)
        {
            _listView.setSelection(position);
        }
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void refreshListView()
    {
        if (_stockLetterListAdapter != null)
        {
            _stockLetterListAdapter.refreshList(true);
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
            _stockLetterListAdapter = new StockLetterListAdapter(getActivity(), _warnTxt);

            _listView = (ListView) _view.findViewById(android.R.id.list);
            _listView.setAdapter(_stockLetterListAdapter);
            _addStockBtn = (Button) _view.findViewById(R.id.stock_list_fragment_addStockbtn);
            _addStockBtn.setOnClickListener(this);
            _stockCodeTxt = (EditText) _view.findViewById(R.id.stock_list_fragment_inputTxt);

            _letterTxt = (TextView) _view.findViewById(R.id.stock_list_fragment_letterTxt);
            _letterSideBar = (LetterSideBar) _view.findViewById(R.id.stock_list_fragment_SideBar);
            _letterSideBar.setTextView(_letterTxt);
            _letterSideBar.setILetterChange(this);
            loading();
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
        if (StockSystemApplication.getInstance().isConnectedNetWork() == true)
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
        Log.v("onStop    "," :: StockListFragment ");
        super.onStop();
    }

    @Override
    public void onResume()
    {
        Log.v("onResume    "," :: StockListFragment ");
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
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        } catch (Exception e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
    }


    @Override
    public void onDestroyView()
    {
        Log.v("onDestroyView    "," :: StockListFragment ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


}
