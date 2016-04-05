package example.ruanjian.stocksystem.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.support.annotation.Nullable;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.adapter.HistoryAdapter;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.asyncTask.GetHistoryRecordAsyncTask;
import example.ruanjian.stocksystem.asyncTask.ClearHistoryRecordAsyncTask;

public class HistoryRecordFragment extends BaseFragment implements View.OnClickListener
{

    private View _view;

    private Button _cleanRecordBtn;
    private ListView _historyListView;

    private HistoryAdapter _historyAdapter;

    @Override
    public void loading()
    {
        if (isInited == true && isVisible == true)
        {
            new GetHistoryRecordAsyncTask((StockMainActivity)getActivity()).execute();
        }
    }

    public void refreshListView()
    {
        _historyAdapter.refreshList(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (_view == null)
        {
            isInited = true;
            _view = inflater.inflate(R.layout.history_record_fragment, null, false);
            initView(_view);
            loading();
        }
        return _view;
    }

    private void initView(View view)
    {
        _historyListView = (ListView) view.findViewById(R.id.history_fragment_ListView);

        _historyAdapter = new HistoryAdapter(getActivity());
        _historyListView.setAdapter(_historyAdapter);

        _cleanRecordBtn = (Button) view.findViewById(R.id.history_fragment_clearHistoryBtn);
        _cleanRecordBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        new ClearHistoryRecordAsyncTask((StockMainActivity)getActivity()).execute();
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
