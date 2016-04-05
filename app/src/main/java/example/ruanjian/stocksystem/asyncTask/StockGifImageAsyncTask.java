package example.ruanjian.stocksystem.asyncTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class StockGifImageAsyncTask extends AsyncTask<String, Void, Bundle>
{
    @Override
    protected Bundle doInBackground(String... params)
    {
        String stockNumber = params[0];
        return NetworkUtils.getStockGifImage(stockNumber);
    }

    @Override
    protected void onPostExecute(Bundle bundle)
    {
        Intent intent = new Intent();
        intent.setAction(StockSystemConstant.STOCK_GIF_IMAGE_ACTION);
        intent.putExtras(bundle);
        StockSystemApplication.getInstance().sendBroadcast(intent);
    }



}
