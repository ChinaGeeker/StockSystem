package example.ruanjian.stocksystem.utils;

import android.database.Cursor;
import android.content.ContentValues;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.info.HistoryRecordInfo;
import example.ruanjian.stocksystem.databases.sqlUtils.HistoryBrowsingSQLUtils;

public class HistoryBrowsingUtils
{

    public static HistoryBrowsingSQLUtils historyBrowsingSQLUtils;

    private static HashMap<String, List<HistoryRecordInfo>> _recordnfoHashMap = null;

    public static void clearRecord(String accountName)
    {
        if (_recordnfoHashMap == null)
        {
            return;
        }
        if (_recordnfoHashMap.containsKey(accountName) == true)
        {
            _recordnfoHashMap.remove(accountName);
            int result = historyBrowsingSQLUtils.clearRecord(accountName);
        }
    }

    public static List<HistoryRecordInfo> getRecordByAccountName(String accountName)
    {
        List<HistoryRecordInfo> historyRecordInfos = new ArrayList<HistoryRecordInfo>();
        if (_recordnfoHashMap != null && _recordnfoHashMap.containsKey(accountName) == true)
        {
            historyRecordInfos = _recordnfoHashMap.get(accountName);
        }
        return historyRecordInfos;
    }

    public static int saveRecord(StockInfo stockInfo, int historyType)
    {
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        String timeStr = StockSystemUtils.getCurTimeStringOne();
        String recordDetail = "";
        if (StockSystemConstant.HISTORY_VIEW_TYPE == historyType)
        {
            recordDetail = StockSystemConstant.HISTORY_BROWSING_VIEW;
        }
        if (StockSystemConstant.HISTORY_ADD_TYPE == historyType)
        {
            recordDetail = StockSystemConstant.HISTORY_BROWSING_ADD;
        }
        if (StockSystemConstant.HISTORY_REMOVE_TYPE == historyType)
        {
            recordDetail = StockSystemConstant.HISTORY_BROWSING_DELETE;
        }
        recordDetail = recordDetail.replace("$1", timeStr);
        recordDetail = recordDetail.replace("$2", stockInfo.getName());
        recordDetail = recordDetail.replace("$3", stockInfo.get_number());
        ContentValues contentValues = new ContentValues();
        contentValues.put(StockSystemConstant.ACCOUNT_NAME, accountName);
        contentValues.put(StockSystemConstant.RECORD_DETAIL, recordDetail);
        contentValues.put(StockSystemConstant.STOCK_NUMBER, stockInfo.get_number());
        contentValues.put(StockSystemConstant.STOCK_NAME, stockInfo.getName());
        contentValues.put(StockSystemConstant.HISTORY_BROWSING_TIME, timeStr);
        int result = historyBrowsingSQLUtils.insert(null, contentValues);
        /*if (result <= -1)
        {
            Log.v("saveRecord 失败  ", accountName + " 保存记录  " + stockInfo.getName());
        }
        else
        {
            Log.v("saveRecord 成功  ", accountName + " 保存记录  " + stockInfo.getName());
        }*/
        return result;
    }

    public static void queryHistoryRecordByAccountName(String accountName)
    {
        String selection = StockSystemConstant.ACCOUNT_NAME + "=?";
        String[] selectionArgs = new String[]{accountName};
        List<HistoryRecordInfo> historyRecordInfoList = new ArrayList<HistoryRecordInfo>();
        Cursor cursor = historyBrowsingSQLUtils.query(null, selection, selectionArgs, null, null, null);
        if (cursor == null)
        {
            return;
        }
        while (cursor.moveToNext())
        {
            HistoryRecordInfo historyRecordInfo = new HistoryRecordInfo();
            String stockNumber = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_NUMBER));
            historyRecordInfo.set_stockNumber(stockNumber);
            String stockTitleName = cursor.getString(cursor.getColumnIndex(StockSystemConstant.STOCK_NAME));
            historyRecordInfo.set_stockName(stockTitleName);
            String recordDetail = cursor.getString(cursor.getColumnIndex(StockSystemConstant.RECORD_DETAIL));
            historyRecordInfo.set_recordDetail(recordDetail);
            String historyTime = cursor.getString(cursor.getColumnIndex(StockSystemConstant.HISTORY_BROWSING_TIME));
            historyRecordInfo.set_historyTime(historyTime);
            historyRecordInfo.set_accountName(accountName);
            historyRecordInfoList.add(historyRecordInfo);
        }
        if (_recordnfoHashMap == null)
        {
            _recordnfoHashMap = new HashMap<String, List<HistoryRecordInfo>>();
        }
        if (_recordnfoHashMap.containsKey(accountName) == true)
        {
            _recordnfoHashMap.remove(accountName);
        }
        _recordnfoHashMap.put(accountName, historyRecordInfoList);
    }



}
