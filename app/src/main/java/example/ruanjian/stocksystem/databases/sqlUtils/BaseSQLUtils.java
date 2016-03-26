package example.ruanjian.stocksystem.databases.sqlUtils;

import android.database.Cursor;
import android.content.ContentValues;

import example.ruanjian.stocksystem.utils.StockSystemUtils;

public class BaseSQLUtils
{
    protected String tableName = "";

    public BaseSQLUtils(String tableName)
    {
        this.tableName = tableName;
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        long result = StockSystemUtils.getStockSystemSQLHelper().update(tableName, values, whereClause, whereArgs);
        return (int)result;
    }


    public int insert(String nullColumnHack, ContentValues values)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        long result = StockSystemUtils.getStockSystemSQLHelper().insert(tableName, nullColumnHack, values);
        return (int)result;
    }



    public int delete(String whereClause, String[] whereArgs)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        int result = StockSystemUtils.getStockSystemSQLHelper().delete(tableName, whereClause, whereArgs);
        return result;
    }

    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return null;
        }
        Cursor resultCursor = StockSystemUtils.getStockSystemSQLHelper().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
        return resultCursor;
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
