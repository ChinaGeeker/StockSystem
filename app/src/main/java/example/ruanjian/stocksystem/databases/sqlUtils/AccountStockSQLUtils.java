package example.ruanjian.stocksystem.databases.sqlUtils;

import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class AccountStockSQLUtils extends BaseSQLUtils
{
    private static AccountStockSQLUtils _accountStockSQLUtils;

    public static AccountStockSQLUtils getInstance()
    {
        if (_accountStockSQLUtils == null)
        {
            _accountStockSQLUtils = new AccountStockSQLUtils();
        }
        return _accountStockSQLUtils;
    }

    public AccountStockSQLUtils()
    {
        super(StockSystemConstant.ACCOUNT_STOCK_TABLE_NAME);
    }

    /*public int delete(String whereClause, String[] whereArgs)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        int result = StockSystemUtils.getStockSystemSQLHelper().delete(StockSystemConstant.ACCOUNT_STOCK_TABLE_NAME, whereClause, whereArgs);
        return result;
    }*/

    /*public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return null;
        }
        Cursor resultCursor = StockSystemUtils.getStockSystemSQLHelper().query(StockSystemConstant.ACCOUNT_STOCK_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return resultCursor;
    }*/

    /*public int insert(String nullColumnHack, ContentValues values)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        int result = StockSystemUtils.getStockSystemSQLHelper().insert(StockSystemConstant.ACCOUNT_STOCK_TABLE_NAME, nullColumnHack, values);
        return result;
    }*/


}
