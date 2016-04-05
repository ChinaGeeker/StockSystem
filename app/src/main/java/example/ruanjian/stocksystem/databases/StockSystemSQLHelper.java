package example.ruanjian.stocksystem.databases;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import example.ruanjian.stocksystem.utils.StockUtils;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;
import example.ruanjian.stocksystem.databases.sqlUtils.StockSQLUtils;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.databases.sqlUtils.AccountSQLUtils;
import example.ruanjian.stocksystem.databases.sqlUtils.AccountStockSQLUtils;
import example.ruanjian.stocksystem.databases.sqlUtils.HistoryBrowsingSQLUtils;

public class StockSystemSQLHelper extends SQLiteOpenHelper
{
    private SQLiteDatabase _database;
    private static StockSystemSQLHelper _instance;

    private StockSystemSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public static StockSystemSQLHelper getInstance(Context context)
    {
        if (_instance == null)
        {
            _instance = new StockSystemSQLHelper(context, StockSystemConstant.STOCK_SYSTEM_DATABASES_NAME, null, 1);
            StockUtils.stockSQLUtils = StockSQLUtils.getInstance();
            AccountUtils.accountSQLUtils = AccountSQLUtils.getInstance();
            AccountStockUtils.accountStockSQLUtils = AccountStockSQLUtils.getInstance();
            HistoryBrowsingUtils.historyBrowsingSQLUtils = HistoryBrowsingSQLUtils.getInstance();
        }
        return _instance;
    }


    public void close()
    {
        if (_database != null && (_database.isOpen() == true))
        {
            _database.close();
        }
        _instance = null;
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String stockSql = "create table " + StockSystemConstant.STOCK_TABLE_NAME + " ( "
                + StockSystemConstant.STOCK_NUMBER + " text primary key," + StockSystemConstant.STOCK_TITLE_NAME + " text,"
                + StockSystemConstant.STOCK_OPENING_PRICE + " text,"
                + StockSystemConstant.STOCK_CLOSING_PRICE + " text," + StockSystemConstant.STOCK_CURRENT_PRICE + " text,"
                + StockSystemConstant.STOCK_MAX_PRICE + " text," + StockSystemConstant.STOCK_MIN_PRICE + " text)";

        String accountSql =  "create table " + StockSystemConstant.ACCOUNT_TABLE_NAME + "(" + StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + " text primary" +
                " key, " + StockSystemConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME + " integer,"+ StockSystemConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME + " integer," + StockSystemConstant.ACCOUNT_COLUMN_ICON_PATH_NAME + " text,"
                + StockSystemConstant.ACCOUNT_COLUMN_PASSWORD_NAME + " text)";

        String historySql = "create table " + StockSystemConstant.HISTORY_BROWSING_TABLE_NAME + " ( "
                + StockSystemConstant.RECORD_DETAIL + " text," + StockSystemConstant.ACCOUNT_NAME + " text,"
                + StockSystemConstant.HISTORY_BROWSING_TIME + " text," +
                StockSystemConstant.STOCK_NUMBER + " text," + StockSystemConstant.STOCK_NAME + " text)";

        String accountStockSql = "create table " + StockSystemConstant.ACCOUNT_STOCK_TABLE_NAME + "(" + StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + " text, "
                + StockSystemConstant.STOCK_TITLE_NAME + " text, "+ StockSystemConstant.RECORD_TIME_NAME + " int,"+ StockSystemConstant.STOCK_NUMBER + " text)";

        try {
            db.execSQL(accountStockSql);
            db.execSQL(stockSql);
            db.execSQL(accountSql);
            db.execSQL(historySql);
        } catch (SQLException e)
        {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy)
    {
        Cursor cursor = null;
        try {
            _database = getReadableDatabase();
            cursor =  _database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (Exception e)
        {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
        return cursor;
    }


    public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
    {
        int result = -1;
        try {
            _database = getWritableDatabase();
            result = _database.update(table, values, whereClause, whereArgs);
        } catch (Exception e)
        {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
        return result;
    }


    public boolean isExist(String table, String[] columns, String selection,
                           String[] selectionArgs)
    {
        Cursor cursor = query(table, columns, selection, selectionArgs, "","","");
        if (cursor == null || cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }

    public int insert(String table,String nullColumnHack, ContentValues values)
    {
        _database = getWritableDatabase();
        if (_database == null)
        {
            return -1;
        }
        long result = _database.insert(table, nullColumnHack, values);
        return (int)result;
    }

    public int delete(String table, String whereClause, String[] whereArgs)
    {
        _database = getWritableDatabase();
        if (_database == null)
        {
            return -1;
        }
        return _database.delete(table, whereClause, whereArgs);
    }

}
