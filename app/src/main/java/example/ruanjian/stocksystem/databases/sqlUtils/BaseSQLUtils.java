package example.ruanjian.stocksystem.databases.sqlUtils;

import android.database.Cursor;
import android.content.ContentValues;

import example.ruanjian.stocksystem.application.StockSystemApplication;

public class BaseSQLUtils
{
    protected String tableName = "";

    public BaseSQLUtils(String tableName)
    {
        this.tableName = tableName;
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs)
    {
        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        long result = StockSystemApplication.getInstance().getStockSystemSQLHelper().update(tableName, values, whereClause, whereArgs);
        return (int)result;
    }

    public int insert(String nullColumnHack, ContentValues values)
    {
        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        long result = StockSystemApplication.getInstance().getStockSystemSQLHelper().insert(tableName, nullColumnHack, values);
        return (int)result;
    }



    public int delete(String whereClause, String[] whereArgs)
    {
        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        return StockSystemApplication.getInstance().getStockSystemSQLHelper().delete(tableName, whereClause, whereArgs);
    }

    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy)
    {
        if (StockSystemApplication.getInstance().getStockSystemSQLHelper() == null)
        {
            return null;
        }
        return StockSystemApplication.getInstance().getStockSystemSQLHelper().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


    public boolean isExist(String selection,
                           String[] selectionArgs)
    {
        Cursor cursor = query(null, selection, selectionArgs, "", "", "");
        if (cursor == null || cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }




}
