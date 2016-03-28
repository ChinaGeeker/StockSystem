package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import example.ruanjian.stocksystem.R;
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
        _stockMainActivity.handler(StockSystemConstant.TYPE_HISTORY, null);
        Toast.makeText(_stockMainActivity, R.string.historyCleanEmpty, Toast.LENGTH_LONG).show();
    }


}
