package example.ruanjian.stocksystem.utils;

import android.database.Cursor;
import android.content.ContentValues;
import android.util.Log;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.info.StockInfo;
import example.ruanjian.stocksystem.info.HistoryRecordInfo;
import example.ruanjian.stocksystem.databases.sqlUtils.HistoryBrowsingSQLUtils;

public class HistoryBrowsingUtils
{

    public static HistoryBrowsingSQLUtils historyBrowsingSQLUtils;

    private static HashMap<String, List<HistoryRecordInfo>> _recordInfoHashMap = null;

    public static void clearRecord(String accountName)
    {
        if (_recordInfoHashMap == null)
        {
            return;
        }
        if (_recordInfoHashMap.containsKey(accountName) == true)
        {
            _recordInfoHashMap.remove(accountName);
            /*int result =*/ historyBrowsingSQLUtils.clearRecord(accountName);
        }
    }

    public static List<HistoryRecordInfo> getRecordByAccountName(String accountName)
    {
        List<HistoryRecordInfo> historyRecordInfos = new ArrayList<HistoryRecordInfo>();
        if (_recordInfoHashMap != null && _recordInfoHashMap.containsKey(accountName) == true)
        {
            historyRecordInfos = _recordInfoHashMap.get(accountName);
        }
        return historyRecordInfos;
    }

    public static int saveRecord(StockInfo stockInfo, int historyType)
    {
        String accountName = AccountUtils.getCurLoginAccountInfo().get_accountName();
        String timeStr = StockSystemApplication.getInstance().getCurTimeStringOne();
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
        return historyBrowsingSQLUtils.insert(null, contentValues);
        /*if (result <= -1)
        {
            Log.v("saveRecord 失败  ", accountName + " 保存记录  " + stockInfo.getName());
        }
        else
        {
            Log.v("saveRecord 成功  ", accountName + " 保存记录  " + stockInfo.getName());
        }*/
    }


    public static int deleteHistoryRecordByAccountName(String accountName)
    {
        clearRecord(accountName);
        String whereClause = StockSystemConstant.ACCOUNT_NAME + "=?";
        String[] whereArgs = new String[]{accountName};
        int result = historyBrowsingSQLUtils.delete(whereClause, whereArgs);

        if (result <= -1)
        {
            Log.v("测试", "delete   历史记录  删除账号失败");
        }
        else
        {
            Log.v("测试", "delete 历史记录  删除账号成功g");
        }



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
        if (_recordInfoHashMap == null)
        {
            _recordInfoHashMap = new HashMap<String, List<HistoryRecordInfo>>();
        }
        if (_recordInfoHashMap.containsKey(accountName) == true)
        {
            _recordInfoHashMap.remove(accountName);
        }
        _recordInfoHashMap.put(accountName, historyRecordInfoList);
    }



}
