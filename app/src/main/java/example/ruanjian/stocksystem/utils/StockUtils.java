package example.ruanjian.stocksystem.utils;

import android.database.Cursor;
import android.content.ContentValues;

import java.util.HashMap;

import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.databases.sqlUtils.StockSQLUtils;


public class StockUtils
{
    public static StockSQLUtils stockSQLUtils;

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

    public static void queryAllStock()
    {
        if (_stockInfoHashMap == null)
        {
            _stockInfoHashMap = new HashMap<String, StockInfo>();
        }
        Cursor cursor = stockSQLUtils.query(null, null, null, null, null, null);
       if (cursor == null)
       {
           return;
       }
        while (cursor.moveToNext())
        {
            StockInfo stockInfo = new StockInfo();
            String stockNumber = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_NUMBER));
            stockInfo.set_number(stockNumber);
            String stockTitleName = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_TITLE_NAME));
            stockInfo.setName(stockTitleName);
            String stockOpeningPrice = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_OPENING_PRICE));
            stockInfo.set_openingPrice(stockOpeningPrice);
            String stockClosingPrice = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_CLOSING_PRICE));
            stockInfo.setClosing_price(stockClosingPrice);
            String stockCurPrice = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_CURRENT_PRICE));
            stockInfo.set_currentPrice(stockCurPrice);
            String stockMaxPrice = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_MAX_PRICE));
            stockInfo.set_maxPrice(stockMaxPrice);
            String stockMinPrice = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_MIN_PRICE));
            stockInfo.set_maxPrice(stockMinPrice);
            _stockInfoHashMap.put(stockNumber,stockInfo);
        }
    }

    private static ContentValues getContentValueByStockInfo(StockInfo stockInfo)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StockSystemConstant.STOCK_NUMBER, stockInfo.get_number());
        contentValues.put(StockSystemConstant.STOCK_TITLE_NAME, stockInfo.getName());
        contentValues.put(StockSystemConstant.STOCK_OPENING_PRICE, stockInfo.get_openingPrice());
        contentValues.put(StockSystemConstant.STOCK_CLOSING_PRICE, stockInfo.getClosing_price());
        contentValues.put(StockSystemConstant.STOCK_CURRENT_PRICE, stockInfo.get_currentPrice());
        contentValues.put(StockSystemConstant.STOCK_MAX_PRICE, stockInfo.get_maxPrice());
        contentValues.put(StockSystemConstant.STOCK_MIN_PRICE, stockInfo.get_minPrice());
        return contentValues;
    }

    public static void addStockInfo(StockInfo stockInfo)
    {
        if (stockInfo == null)
        {
            return;
        }
        if (isExistStock(stockInfo.get_number()) == true)
        {
            updateStockData(stockInfo);
            return;
        }
        ContentValues contentValues = getContentValueByStockInfo(stockInfo);
        Cursor cursor = stockSQLUtils.query(new String[]{StockSystemConstant.STOCK_NUMBER}, StockSystemConstant.STOCK_NUMBER + "=?",
                new String[]{stockInfo.get_number()}, null, null, null);
        if (cursor == null || cursor.moveToNext() == false)
        {
            // 数据库中没有此数据
            stockSQLUtils.insert(null, contentValues);
            AccountStockUtils.plusStockInfo(stockInfo);
        }
        _stockInfoHashMap.put(stockInfo.get_number(), stockInfo);
    }

    public static void updateStockData(StockInfo stockInfo)
    {
        stockSQLUtils.update(getContentValueByStockInfo(stockInfo), StockSystemConstant.STOCK_NUMBER + "=?", new String[]{stockInfo.get_number()});
        _stockInfoHashMap.put(stockInfo.get_number(), stockInfo);
    }


}
