package example.ruanjian.stocksystem.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.info.AccountStockInfo;

public class AccountStockUtils
{
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

    public static int deleteByAccountName(String accountName)
    {
        String whereClause = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?";
        String[] whereArgs = new String[]{accountName + ""};
//        int resultIndex = accountStockSQLUtils.delete(whereClause, whereArgs);
        _accountStockHashMap.remove(accountName);
        return 1;
    }


    public static List<AccountStockInfo> getListByAccountName(String accountName)
    {
        if (_accountStockHashMap == null)
        {
            _accountStockHashMap = new HashMap<String, List<AccountStockInfo>>();
        }
        List<AccountStockInfo> accountStockInfoList = null;
        if (_accountStockHashMap.containsKey(accountName))
        {
            accountStockInfoList = _accountStockHashMap.get(accountName);
            AccountStockInfo accountStockInfo = new AccountStockInfo();

            accountStockInfo.set_stockName("12456试试的");
            accountStockInfo.set_number("sh10025");
//            accountStockInfoList.add(accountStockInfo);
            return accountStockInfoList;
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
        _accountStockHashMap.remove(accountName);
        String parameterData = StockSystemConstant.ACCOUNT_NAME + "=" + AccountUtils.getCurLoginAccountInfo().get_accountName();
        String resultObj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.QUERY_ACCOUNT_STOCK_URL, parameterData).toString();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resultObj);
//            _registerResult = jsonObject.getInt(StockSystemConstant.RETURN_TYPE);
//            _registerMessage = jsonObject.getString(StockSystemConstant.RETURN_MESSAGE);
            JSONArray contentJsoArr = jsonObject.getJSONArray(StockSystemConstant.RETURN_CONTENT);
            List<AccountStockInfo> accountStockInfoList = new ArrayList<AccountStockInfo>();
            for (int index = 0; index < contentJsoArr.length(); index++)
            {
                JSONObject contentJson =  contentJsoArr.getJSONObject(index);
                AccountStockInfo accountStockInfo = new AccountStockInfo();

                String stockNumber = contentJson.getString(StockSystemConstant.STOCK_NUMBER);
                int recordTime = contentJson.getInt(StockSystemConstant.RECORD_TIME_NAME);
                String stockName = contentJson.getString(StockSystemConstant.STOCK_TITLE_NAME);
                accountStockInfo.set_accountName(accountName);
                accountStockInfo.set_number(stockNumber);
                accountStockInfo.set_stockName(stockName);
                accountStockInfo.set_recordTime(recordTime);
                accountStockInfoList.add(accountStockInfo);
                _accountStockHashMap.put(accountName, accountStockInfoList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        HistoryBrowsingUtils.saveRecord(stockInfo, StockSystemConstant.HISTORY_REMOVE_TYPE);
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
//        int result = accountStockSQLUtils.insert(null, getContentValues(accountStockInfo));
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

}
