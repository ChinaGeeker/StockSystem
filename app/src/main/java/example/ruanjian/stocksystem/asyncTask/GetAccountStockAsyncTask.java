package example.ruanjian.stocksystem.asyncTask;

import android.os.Bundle;
import android.os.AsyncTask;

import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.manager.BroadcastManager;
import example.ruanjian.stocksystem.utils.StockSystemConstant;


public class GetAccountStockAsyncTask extends AsyncTask<Void, Void, Void>{


    public GetAccountStockAsyncTask()
    {
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
        Bundle bundle = new Bundle();
        bundle.putInt(StockSystemConstant.STOCK_MAIN_TYPE, StockSystemConstant.TYPE_LIST);
        BroadcastManager.getInstance().sendBroadcast(StockSystemConstant.STOCK_MAIN_ACTION, bundle);
    }


}