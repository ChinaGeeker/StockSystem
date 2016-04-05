package example.ruanjian.stocksystem.asyncTask;

import android.os.Bundle;
import android.os.AsyncTask;
import android.app.AlertDialog;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.manager.BroadcastManager;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;

public class AddStockAsyncTask extends AsyncTask<String, Void, Integer>
{
    private String _stockNumber = "";
    private StockMainActivity _stockMainActivity;

    public AddStockAsyncTask(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        _stockNumber = params[0];
        StockInfo stockInfo = StockUtils.getStockInfoByStockNumber(_stockNumber);
        if (stockInfo != null)
        {
            AccountStockUtils.plusStockInfo(stockInfo);
            return StockSystemConstant.STATE_SUCCESS;
        }
        return NetworkUtils.getStockInfo(_stockNumber);
    }

    @Override
    protected void onPostExecute(Integer resultIndex)
    {
        if (resultIndex == StockSystemConstant.STATE_CANCELED)
        {
            new AlertDialog.Builder(_stockMainActivity).setTitle(R.string.warn).setMessage(R.string.stockNoExist).create().show();
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putInt(StockSystemConstant.STOCK_MAIN_TYPE, StockSystemConstant.TYPE_LIST);
            BroadcastManager.getInstance().sendBroadcast(StockSystemConstant.STOCK_MAIN_ACTION, bundle);
        }
    }


}