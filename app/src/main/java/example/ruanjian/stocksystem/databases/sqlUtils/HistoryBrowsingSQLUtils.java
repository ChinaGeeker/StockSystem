package example.ruanjian.stocksystem.databases.sqlUtils;

import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.application.StockSystemApplication;

public class HistoryBrowsingSQLUtils extends BaseSQLUtils
{
    private static HistoryBrowsingSQLUtils _historyBrowsingSQLUtils;

    public static HistoryBrowsingSQLUtils getInstance()
    {
        if (_historyBrowsingSQLUtils == null)
        {
            _historyBrowsingSQLUtils = new HistoryBrowsingSQLUtils();
        }
        return _historyBrowsingSQLUtils;
    }

    public HistoryBrowsingSQLUtils()
    {
        super(StockSystemConstant.HISTORY_BROWSING_TABLE_NAME);
    }


  /*  @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " + HistoryBrowsingConstant.HISTORY_BROWSING_TABLE_NAME + " ( "
                + HistoryBrowsingConstant.RECORD_DETAIL + " text," + HistoryBrowsingConstant.ACCOUNT_NAME + " text,"
                + HistoryBrowsingConstant.HISTORY_BROWSING_TIME + " text," +
                HistoryBrowsingConstant.STOCK_NUMBER + " text," + HistoryBrowsingConstant.STOCK_NAME + " text)";
        try {
            db.execSQL(sql);
            Log.v("onCreate 数据库 ", "   " + sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
            UtilsConstant.printLog(UtilsConstant.LOG_ERROR, "", e);
        }
    }*/


   /* public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy)
    {
        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return null;
        }
        Cursor resultCursor =  StockSystemApplication.getInstance().getStockSystemSQLHelper().query(StockSystemConstant.HISTORY_BROWSING_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return resultCursor;

//        Cursor cursor = null;
//        try {
//            _historyDatabase = getReadableDatabase();
//            cursor =  _historyDatabase.query(HistoryBrowsingConstant.HISTORY_BROWSING_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            UtilsConstant.printLog(UtilsConstant.LOG_ERROR, "", e);
//        }
//        return cursor;
    }*/

   /* @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }*/

    public int clearRecord(String accountName)
    {
        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        String whereClause = StockSystemConstant.ACCOUNT_NAME + "=?";
        String[] whereArgs = new String[]{accountName};
        return delete(whereClause, whereArgs);

        /*_historyDatabase = getWritableDatabase();
        if (_historyDatabase == null)
        {
            return -1;
        }
        String whereClause = HistoryBrowsingConstant.ACCOUNT_NAME + "=?";
        String[] whereArgs = new String[]{accountName};
        int result = _historyDatabase.delete(HistoryBrowsingConstant.HISTORY_BROWSING_TABLE_NAME, whereClause, whereArgs);
        return result;*/
    }



   /* public int insert(String nullColumnHack, ContentValues values)
    {

        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        int result = StockSystemApplication.getInstance().getStockSystemSQLHelper().insert(StockSystemConstant.HISTORY_BROWSING_TABLE_NAME, nullColumnHack, values);
        return result;
        *//*_historyDatabase = getWritableDatabase();
        if (_historyDatabase == null)
        {
            Log.v("saveRecord   ", " 没找到  数据库  ");
            return -1;
        }
        long result = _historyDatabase.insert(HistoryBrowsingConstant.HISTORY_BROWSING_TABLE_NAME, nullColumnHack, values);
        return (int)result;*//*
    }*/

    /*public int saveRecord(StockInfo stockInfo, int showState)
    {
        _historyDatabase = getWritableDatabase();
        if (_historyDatabase == null)
        {
            Log.v("saveRecord   ", " 没找到  数据库  ");
            return -1;
        }
        String accountName = AccountConstant.getCurLoginAccountInfo().get_accountName();
        String timeStr = UtilsConstant.getCurTimeStringOne();
        String recordDetail = HistoryBrowsingConstant.HISTORY_BROWSING_VIEW;
        if (UtilsConstant.STATE_CANCELED == showState)
        {
            recordDetail = HistoryBrowsingConstant.HISTORY_BROWSING_DELETE;
        }
        recordDetail = recordDetail.replace("$1", timeStr);
        recordDetail = recordDetail.replace("$2", stockInfo.getName());
        recordDetail = recordDetail.replace("$3", stockInfo.get_number());
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryBrowsingConstant.ACCOUNT_NAME, accountName);
        contentValues.put(HistoryBrowsingConstant.RECORD_DETAIL, recordDetail);
        contentValues.put(HistoryBrowsingConstant.STOCK_NUMBER, stockInfo.get_number());
        contentValues.put(HistoryBrowsingConstant.STOCK_NAME, stockInfo.getName());
        contentValues.put(HistoryBrowsingConstant.HISTORY_BROWSING_TIME, timeStr);
        long result = _historyDatabase.insert(HistoryBrowsingConstant.HISTORY_BROWSING_TABLE_NAME, null, contentValues);
        if (result <= -1)
        {
            Log.v("saveRecord 失败  ", accountName + " 保存记录  " + stockInfo.getName());
        }
        else
        {
            Log.v("saveRecord 成功  ", accountName + " 保存记录  " + stockInfo.getName());
        }
       return (int)result;
    }*/


}
