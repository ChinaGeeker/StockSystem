package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.manager.BroadcastManager;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class ClearHistoryRecordAsyncTask extends AsyncTask<String, Void, Void>
{
    private StockMainActivity _stockMainActivity;

    public ClearHistoryRecordAsyncTask(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String accountNameStr = AccountUtils.getCurLoginAccountInfo().get_accountName();
        HistoryBrowsingUtils.clearRecord(accountNameStr);
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(StockSystemConstant.STOCK_MAIN_TYPE, StockSystemConstant.TYPE_HISTORY);
        BroadcastManager.getInstance().sendBroadcast(StockSystemConstant.STOCK_MAIN_ACTION, bundle);
        Toast.makeText(_stockMainActivity, R.string.historyCleanEmpty, Toast.LENGTH_LONG).show();
    }


}
