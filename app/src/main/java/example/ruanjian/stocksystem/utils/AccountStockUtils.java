package example.ruanjian.stocksystem.utils;

import android.util.Log;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.info.AccountStockInfo;
import example.ruanjian.stocksystem.databases.sqlUtils.AccountStockSQLUtils;

public class AccountStockUtils
{
    public static AccountStockSQLUtils accountStockSQLUtils;

    private static HashMap<String, List<AccountStockInfo>>  _accountStockHashMap = null;

    public static boolean isEmptyByAccountName(String accountName)
    {
        if (_accountStockHashMap == null)
        {
            _accountStockHashMap = new HashMap<String, List<AccountStockInfo>>();
        }
        if (_accountStockHashMap.containsKey(accountName))
        {
            return _accountStockHashMap.get(accountName).isEmpty();
        }
        return true;
    }

    public static boolean isExistByStockNumber(String stockNumber)
    {
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        if (_accountStockHashMap == null)
        {
            _accountStockHashMap = new HashMap<String, List<AccountStockInfo>>();
            return false;
        }
        List<AccountStockInfo> accountStockInfoList = new ArrayList<AccountStockInfo>();
        if (_accountStockHashMap.containsKey(accountName))
        {
            accountStockInfoList = _accountStockHashMap.get(accountName);
            for (AccountStockInfo accountStockInfo:accountStockInfoList)
            {
                if (accountStockInfo.get_number().equals(stockNumber))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<AccountStockInfo> getListByAccountName(String accountName)
    {
        if (_accountStockHashMap == null)
        {
            _accountStockHashMap = new HashMap<String, List<AccountStockInfo>>();
        }
        if (_accountStockHashMap.containsKey(accountName))
        {
            return _accountStockHashMap.get(accountName);
        }
        return (new ArrayList<AccountStockInfo>());
    }

    public static void queryAccountStock()
    {
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        if (_accountStockHashMap == null)
        {
            _accountStockHashMap = new HashMap<String, List<AccountStockInfo>>();
        }
        String selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?";
        String[] selectionArgs = new String[]{accountName + ""};
        String orderBy = StockSystemConstant.RECORD_TIME_NAME + " desc";
        Cursor cursor = accountStockSQLUtils.query(null, selection, selectionArgs, "","",orderBy);
        if (cursor == null)
        {
            return;
        }
        _accountStockHashMap.remove(accountName);
        List<AccountStockInfo> accountStockInfoList = new ArrayList<AccountStockInfo>();
        while (cursor.moveToNext())
        {
            AccountStockInfo accountStockInfo = new AccountStockInfo();
            String stockNumber = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_NUMBER));
            int recordTime = cursor.getInt(cursor.getColumnIndex(StockSystemConstant.RECORD_TIME_NAME));
            String stockName = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_TITLE_NAME));
            accountStockInfo.set_accountName(accountName);
            accountStockInfo.set_number(stockNumber);
            accountStockInfo.set_stockName(stockName);
            accountStockInfo.set_recordTime(recordTime);
            accountStockInfoList.add(accountStockInfo);
            _accountStockHashMap.put(accountName, accountStockInfoList);
        }
    }

    public static void removeStockInfo(StockInfo stockInfo)
    {
        boolean isExist = isExistByStockNumber(stockInfo.get_number());
        if (isExist == false)
        {
            return;
        }
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        List<AccountStockInfo> accountStockInfoList = null;
        accountStockInfoList = _accountStockHashMap.get(accountName);
        for (AccountStockInfo accountStockInfo:accountStockInfoList)
        {
            if (accountStockInfo.get_number().equals(stockInfo.get_number()))
            {
                accountStockInfoList.remove(accountStockInfo);
                break;
            }
        }
        String whereClause = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?and " + StockSystemConstant.STOCK_NUMBER + "=?";
        String[] whereArgs = new String[]{accountName + "",stockInfo.get_number()};
        int result = accountStockSQLUtils.delete(whereClause, whereArgs);
        HistoryBrowsingUtils.saveRecord(stockInfo, StockSystemConstant.HISTORY_REMOVE_TYPE);
       /* if (result <= -1)
        {
            Log.v("removeStockInfo 失败  ", accountName + " 保存记录  " + stockInfo.getName());
        }
        else
        {
            Log.v("removeStockInfo 成功  ", accountName + " 保存记录  " + stockInfo.getName());
        }*/


    }

    public static void  plusStockInfo(StockInfo stockInfo)
    {
        boolean isExist = isExistByStockNumber(stockInfo.get_number());
        if (isExist == true)
        {
            return;
        }
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        List<AccountStockInfo> accountStockInfoList = new ArrayList<AccountStockInfo>();
        if (_accountStockHashMap.containsKey(accountName))
        {
            accountStockInfoList = _accountStockHashMap.get(accountName);
        }
        AccountStockInfo accountStockInfo = new AccountStockInfo();
        accountStockInfo.set_accountName(accountName);
        accountStockInfo.set_number(stockInfo.get_number());
        accountStockInfo.set_stockName(stockInfo.getName());
        accountStockInfo.set_recordTime((int) (System.currentTimeMillis() / 1000));
        accountStockInfoList.add(accountStockInfo);
        _accountStockHashMap.put(accountName, accountStockInfoList);
        int result = accountStockSQLUtils.insert(null, getContentValues(accountStockInfo));
        /*if (result <= -1)
        {
            Log.v("plusStockInfo 失败  ", accountName + " 保存记录  " + stockInfo.getName());
        }
        else
        {
            Log.v("plusStockInfo 成功  ", accountName + " 保存记录  " + stockInfo.getName());
        }*/
        HistoryBrowsingUtils.saveRecord(stockInfo, StockSystemConstant.HISTORY_ADD_TYPE);
    }

    private static ContentValues getContentValues(AccountStockInfo accountStockInfo)
    {
        ContentValues contentValue = new ContentValues();
        contentValue.put(StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME, accountStockInfo.get_accountName());
        contentValue.put(StockSystemConstant.STOCK_TITLE_NAME, accountStockInfo.get_stockName());
        contentValue.put(StockSystemConstant.STOCK_NUMBER, accountStockInfo.get_number());
        contentValue.put(StockSystemConstant.RECORD_TIME_NAME, accountStockInfo.get_recordTime());
        return contentValue;
    }

}
