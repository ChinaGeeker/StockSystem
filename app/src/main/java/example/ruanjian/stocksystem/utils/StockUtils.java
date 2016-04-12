package example.ruanjian.stocksystem.utils;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import example.ruanjian.stocksystem.info.StockInfo;


public class StockUtils
{
    private static HashMap<String, StockInfo> _stockInfoHashMap = null;

    public static StockInfo analysisStockData(String result, String stockNumber)
    {
        String[] stockInfoDataArr = result.split(",");
        StockInfo stockInfo = new StockInfo();
        stockInfo.set_number(stockNumber);
        stockInfo.setName(stockInfoDataArr[StockSystemConstant.NAME]);
        stockInfo.set_currentPrice(stockInfoDataArr[StockSystemConstant.CURRENT_PRICE]);
        stockInfo.setClosing_price(stockInfoDataArr[StockSystemConstant.CLOSING_PRICE]);
        stockInfo.set_openingPrice(stockInfoDataArr[StockSystemConstant.OPENING_PRICE]);
        stockInfo.set_maxPrice(stockInfoDataArr[StockSystemConstant.MAX_PRICE]);
        stockInfo.set_minPrice(stockInfoDataArr[StockSystemConstant.MIN_PRICE]);
        return stockInfo;
    }


    public static StockInfo getStockInfoByJsonObject(JSONObject stockInfoJson)
    {
        if (stockInfoJson == null)
        {
            return null;
        }
        StockInfo stockInfo = null;
        try {
            stockInfo = new StockInfo();
            stockInfo.set_number(stockInfoJson.getString(StockSystemConstant.STOCK_NUMBER));
            stockInfo.setName(stockInfoJson.getString(StockSystemConstant.STOCK_NAME));
            stockInfo.set_currentPrice(stockInfoJson.getString(StockSystemConstant.STOCK_CURRENT_PRICE));
            stockInfo.setClosing_price(stockInfoJson.getString(StockSystemConstant.STOCK_CLOSING_PRICE));
            stockInfo.set_openingPrice(stockInfoJson.getString(StockSystemConstant.STOCK_OPENING_PRICE));
            stockInfo.set_maxPrice(stockInfoJson.getString(StockSystemConstant.STOCK_MAX_PRICE));
            stockInfo.set_minPrice(stockInfoJson.getString(StockSystemConstant.STOCK_MIN_PRICE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stockInfo;
    }

    public static StockInfo getStockInfoByStockNumber(String stockNumber)
    {
        if (_stockInfoHashMap == null)
        {
            _stockInfoHashMap = new HashMap<String, StockInfo>();
            return null;
        }
        StockInfo returnStockInfo = null;
        for (StockInfo stockInfo:_stockInfoHashMap.values())
        {
            if (stockInfo.get_number().equals(stockNumber))
            {
                returnStockInfo = stockInfo;
                break;
            }
        }
        return returnStockInfo;
    }

    public static boolean isExistStock( String stockNumber)
    {
        if (_stockInfoHashMap == null)
        {
            _stockInfoHashMap = new HashMap<String, StockInfo>();
            return false;
        }
       return _stockInfoHashMap.containsKey(stockNumber);
    }

    public static boolean isEmpty()
    {
        if (_stockInfoHashMap == null)
        {
            _stockInfoHashMap = new HashMap<String, StockInfo>();
        }
        return _stockInfoHashMap.isEmpty();
    }

    public static void searchPlusStockInfo(StockInfo stockInfo)
    {
        if (stockInfo == null)
        {
            return;
        }
        _stockInfoHashMap.put(stockInfo.get_number(), stockInfo);
    }

    public static void addStockInfo(StockInfo stockInfo)
    {
        if (stockInfo == null)
        {
            return;
        }
        String parameterData = StockSystemConstant.STOCK_NAME + "=" + stockInfo.getName()+"&"
                + StockSystemConstant.STOCK_NUMBER + "=" + stockInfo.get_number()+"&"
                + StockSystemConstant.STOCK_CLOSING_PRICE + "=" + stockInfo.getClosing_price()+"&"
                + StockSystemConstant.STOCK_CURRENT_PRICE + "=" + stockInfo.getCurrentPrice()+"&"
                + StockSystemConstant.STOCK_MAX_PRICE + "=" + stockInfo.get_maxPrice()+"&"
                + StockSystemConstant.STOCK_MIN_PRICE + "=" + stockInfo.get_minPrice()+"&"
                + StockSystemConstant.STOCK_OPENING_PRICE + "=" + stockInfo.get_openingPrice();
        if (isExistStock(stockInfo.get_number()) == true)
        {
            updateStockData(stockInfo);
            return;
        }
        String resultObj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.ADD_STOCK_URL, parameterData).toString();
        parameterData = StockSystemConstant.STOCK_NAME + "=" + stockInfo.getName()+"&"
                + StockSystemConstant.STOCK_NUMBER + "=" + stockInfo.get_number()+"&"
                + StockSystemConstant.ACCOUNT_NAME + "=" + AccountUtils.getCurLoginAccountInfo().get_accountName();

        resultObj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.ADD_ACCOUNT_STOCK_URL, parameterData).toString();

        _stockInfoHashMap.put(stockInfo.get_number(), stockInfo);
    }

    public static void updateStockData(StockInfo stockInfo)
    {
        if (stockInfo == null)
        {
            return;
        }
        String parameterData = StockSystemConstant.STOCK_NAME + "=" + stockInfo.getName()+"&"
                + StockSystemConstant.STOCK_NUMBER + "=" + stockInfo.get_number()+"&"
                + StockSystemConstant.STOCK_CLOSING_PRICE + "=" + stockInfo.getClosing_price()+"&"
                + StockSystemConstant.STOCK_CURRENT_PRICE + "=" + stockInfo.getCurrentPrice()+"&"
                + StockSystemConstant.STOCK_MAX_PRICE + "=" + stockInfo.get_maxPrice()+"&"
                + StockSystemConstant.STOCK_MIN_PRICE + "=" + stockInfo.get_minPrice()+"&"
                + StockSystemConstant.STOCK_OPENING_PRICE + "=" + stockInfo.get_openingPrice();
        String resultObj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.UPDATE_STOCK_URL, parameterData).toString();
        _stockInfoHashMap.put(stockInfo.get_number(), stockInfo);
    }


}
