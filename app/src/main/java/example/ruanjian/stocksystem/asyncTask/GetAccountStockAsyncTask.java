package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;

import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;


public class GetAccountStockAsyncTask extends AsyncTask<Void, Void, Void>{

    private StockMainActivity _stockMainActivity;

    public GetAccountStockAsyncTask(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (StockUtils.isEmpty() == true)
        {
            StockUtils.queryAllStock();
        }
        AccountStockUtils.queryAccountStock();
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        _stockMainActivity.handler(StockSystemConstant.TYPE_LIST, null);
    }


}