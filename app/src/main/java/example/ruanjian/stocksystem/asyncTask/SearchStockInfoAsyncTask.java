package example.ruanjian.stocksystem.asyncTask;

import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockInfoActivity;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;

public class SearchStockInfoAsyncTask extends AsyncTask<String, Void, Void>
{
    private String _stockNumber = "";
    private Context _context;
    private StockInfo _stockInfo;


    public SearchStockInfoAsyncTask(Context context)
    {
        this._context = context;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        _stockNumber = params[0];
        _stockInfo = StockUtils.getStockInfoByStockNumber(_stockNumber);

        if (_stockInfo == null)
        {
            String parameterData = StockSystemConstant.STOCK_NUMBER + "=" + _stockNumber;
            Object obj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.SEARCH_STOCK_URL, parameterData).toString();
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                JSONObject contentJson = jsonObject.getJSONObject(StockSystemConstant.RETURN_CONTENT);
                _stockInfo = StockUtils.getStockInfoByJsonObject(contentJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (_stockInfo != null)
            {
                HistoryBrowsingUtils.saveRecord(_stockInfo, StockSystemConstant.HISTORY_VIEW_TYPE);
                StockUtils.searchPlusStockInfo(_stockInfo);
            }
        }
        else
        {
            HistoryBrowsingUtils.saveRecord(_stockInfo, StockSystemConstant.HISTORY_VIEW_TYPE);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void resultIndex)
    {
        if (_stockInfo == null)
        {
            Toast.makeText(_context, "数据库 不存在此 股票信息 , 请联系开发人员", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(_context, StockInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StockSystemConstant.STOCK_INFO, _stockInfo);
        intent.putExtras(bundle);
        _context.startActivity(intent);
    }

}
