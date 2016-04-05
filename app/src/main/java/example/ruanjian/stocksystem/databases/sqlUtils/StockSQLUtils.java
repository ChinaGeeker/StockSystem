package example.ruanjian.stocksystem.databases.sqlUtils;

import example.ruanjian.stocksystem.utils.StockSystemConstant;


public class StockSQLUtils extends BaseSQLUtils
{

    private static StockSQLUtils _instance;


    public static StockSQLUtils getInstance()
    {
        if (_instance == null)
        {
            _instance = new StockSQLUtils();
        }
        return _instance;
    }

    public StockSQLUtils()
    {
        super(StockSystemConstant.STOCK_TABLE_NAME);
    }


    /*@Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " + StockConstant.STOCK_TABLE_NAME + " ( "
                + StockConstant.STOCK_NUMBER + " text primary key," + StockConstant.STOCK_TITLE_NAME + " text,"
                + StockConstant.STOCK_SHOW + " int," + StockConstant.STOCK_OPENING_PRICE + " text,"
                + StockConstant.STOCK_CLOSING_PRICE + " text," + StockConstant.STOCK_CURRENT_PRICE + " text,"
                + StockConstant.STOCK_MAX_PRICE + " text," + StockConstant.STOCK_MIN_PRICE + " text)";
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

        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return null;
        }
        Cursor cursor =  StockSystemUtils.getStockSystemSQLHelper().query(StockSystemConstant.STOCK_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
//        Cursor cursor = null;
//        try {
//            _database = getReadableDatabase();
//            cursor =  _database.query(StockConstant.STOCK_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            UtilsConstant.printLog(UtilsConstant.LOG_ERROR, "", e);
//        }
//        return cursor;
    }*/



  /*  public int update(ContentValues values, String whereClause, String[] whereArgs)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        int result = StockSystemUtils.getStockSystemSQLHelper().update(StockSystemConstant.STOCK_TABLE_NAME, values, whereClause, whereArgs);
        return result;

//        int result = -1;
//        try {
//            _database = getWritableDatabase();
//            result = _database.update(StockConstant.STOCK_TABLE_NAME, values, whereClause, whereArgs);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            UtilsConstant.printLog(UtilsConstant.LOG_ERROR, "", e);
//        }
//        return result;
    }*/

   /* public long insert(String nullColumnHack, ContentValues values)
    {

        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }

        int result = StockSystemUtils.getStockSystemSQLHelper().insert(StockSystemConstant.STOCK_TABLE_NAME, nullColumnHack, values);
        return result;
       *//* long result = -1;
        try {
            _database = getWritableDatabase();
            result = _database.insert(StockConstant.STOCK_TABLE_NAME, nullColumnHack, values);
        } catch (Exception e)
        {
            e.printStackTrace();
            UtilsConstant.printLog(UtilsConstant.LOG_ERROR, "", e);
        }
        return result;*//*
    }*/


}