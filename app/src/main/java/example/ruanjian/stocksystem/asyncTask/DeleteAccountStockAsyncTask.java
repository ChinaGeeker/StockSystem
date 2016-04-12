package example.ruanjian.stocksystem.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;

import org.json.JSONObject;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.manager.BroadcastManager;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class DeleteAccountStockAsyncTask extends AsyncTask<String, Void, Void>
{

    private Context _context;

    public DeleteAccountStockAsyncTask(Context context)
    {
        _context = context;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String accountNameStr = AccountUtils.getCurLoginAccountInfo().get_accountName();
        String stockNumber = params[0];

        String parameterData = StockSystemConstant.ACCOUNT_NAME + "=" + accountNameStr+"&"
                + StockSystemConstant.STOCK_NUMBER + "=" + stockNumber;
        NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.DELETE_ACCOUNT_STOCK_URL, parameterData).toString();

        /*String resultObj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.DELETE_ACCOUNT_STOCK_URL, parameterData).toString();
        try {
            JSONObject jsonObject = new JSONObject(resultObj);
            _loginResult = jsonObject.getInt(StockSystemConstant.RETURN_TYPE);
            _registerMessage = jsonObject.getString(StockSystemConstant.RETURN_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        ((Activity)_context).finish();
        Bundle bundle = new Bundle();
        bundle.putInt(StockSystemConstant.STOCK_MAIN_TYPE, StockSystemConstant.TYPE_LIST);
        BroadcastManager.getInstance().sendBroadcast(StockSystemConstant.STOCK_MAIN_ACTION, bundle);
    }


}