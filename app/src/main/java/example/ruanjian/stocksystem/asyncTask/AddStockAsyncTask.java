package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.app.AlertDialog;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.databases.sqlUtils.StockSQLUtils;

public class AddStockAsyncTask extends AsyncTask<String, Void, Integer>
{
    private String _stockNumber = "";
    private StockSQLUtils _stockSQLUtils;
    private StockMainActivity _stockMainActivity;

    public AddStockAsyncTask(StockMainActivity stockMainActivity)
    {
        this._stockMainActivity = stockMainActivity;
        this._stockSQLUtils = StockUtils.stockSQLUtils;
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
        int resultIndex = StockSystemConstant.STATE_SUCCESS;
        String dataUrlStr = StockSystemConstant.QUERY_URL + _stockNumber;
        try {
            URL url = new URL(dataUrlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StockSystemConstant.CHRSET_NAME_GBK);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(temp);
            }
            resultIndex =  splitStockData(stringBuffer.toString());
        } catch (UnknownHostException e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            e.printStackTrace();
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
        } catch (MalformedURLException e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        } catch (IOException e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        }catch (Exception e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        }
        return resultIndex;
    }

    private int splitStockData(String stockData)
    {
        int resultIndex = StockSystemConstant.STATE_SUCCESS;
        try {
//                String strBuf = "var hq_str_sys_auth='FAILED'";
            Pattern pattern = Pattern.compile(StockSystemConstant.STOCK_DATA_REX);
            Matcher matcher = pattern.matcher(stockData);
            if (matcher.find() == true)
            {
                String result = matcher.group(2);
                String stockNumber = matcher.group(1);
                if (result.equals(StockSystemConstant.FAILED) || result.equals(""))
                {
                    return StockSystemConstant.STATE_CANCELED;
                }
                StockInfo stockInfo = StockUtils.analysisStockData(result, stockNumber);
                StockUtils.addStockInfo(stockInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            resultIndex = StockSystemConstant.STATE_CANCELED;
        }
        return resultIndex;
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
            _stockMainActivity.handler(StockSystemConstant.TYPE_LIST, null);
        }
    }


}