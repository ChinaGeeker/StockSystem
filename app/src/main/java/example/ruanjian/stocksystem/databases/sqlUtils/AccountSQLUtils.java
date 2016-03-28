package example.ruanjian.stocksystem.databases.sqlUtils;

import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class AccountSQLUtils extends BaseSQLUtils
{
    private static AccountSQLUtils _instance;

    public static AccountSQLUtils getInstance()
    {
        if (_instance == null)
        {
            _instance = new AccountSQLUtils();
        }
        return _instance;
    }

    public AccountSQLUtils()
    {
        super(StockSystemConstant.ACCOUNT_TABLE_NAME);
    }


//    public int updateData(ContentValues values, String whereClause, String[] whereArgs)
//    {
//        if (StockSystemUtils.getStockSystemSQLHelper() == null)
//        {
//            return -1;
//        }
//        long result = StockSystemUtils.getStockSystemSQLHelper().update(StockSystemConstant.ACCOUNT_TABLE_NAME, values, whereClause, whereArgs);
//        return (int)result;

        /*_sqlDatabase = getWritableDatabase();
        if (_sqlDatabase == null)
        {
            return -1;
        }
        long result = _sqlDatabase.update(AccountConstant.ACCOUNT_TABLE_NAME, values,  whereClause,  whereArgs);
        return (int)result;*/
//    }

//    public AccountInfo getLastLoginAccountInfo()
//    {
//        String selection = AccountConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME + "=?";
//        String[] selectionArgs = new String[]{UtilsConstant.STATE_SUCCESS + ""};
//        String orderBy = AccountConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME + " desc";
//        AccountInfo accountInfo = null;
//        Cursor cursor = query(null, selection, selectionArgs, "","",orderBy);
//        if (cursor == null || cursor.getCount() <= 0)
//        {
//            Log.v("LastLoginAccountInfo  ", "没有找到登录过的账号");
//            return null;
//        }
//        while (cursor.moveToNext())
//        {
//            String accountName = cursor.getString(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_ACCOUNT_NAME));
//            String accountPassword = cursor.getString(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_PASSWORD_NAME));
//            int loginTimes = cursor.getInt(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME));
//            String iconUrl = cursor.getString(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_ICON_PATH_NAME));
//            int loginState = cursor.getInt(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME));
//            accountInfo = new AccountInfo();
//            accountInfo.set_accountName(accountName);
//            accountInfo.set_password(accountPassword);
//            accountInfo.set_userIconPath(iconUrl);
//            accountInfo.set_loginTime(loginTimes);
//            accountInfo.set_loginState(loginState);
//            Log.v("LastLoginAccountInfo  ", accountName + " accountName; " + accountPassword + " accountPassword");
//        }
//        Log.v("LastLoginAccountInfo  ", "已经找到登录过的账号");
//        return accountInfo;
//    }



//    public List<AccountInfo> getAllAccountInfo()
//    {
//        List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
//        String orderBy = AccountConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME + " desc";
//        Cursor cursor = query(null, null, null, "","", orderBy);
//        if (cursor == null || cursor.getCount() <= 0)
//        {
//            Log.v("getAllAccountInfo  ", "可惜没有搜索到 所有的账号");
//            return accountInfos;
//        }
//        while (cursor.moveToNext())
//        {
//            String accountName = cursor.getString(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_ACCOUNT_NAME));
//            String accountPassword = cursor.getString(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_PASSWORD_NAME));
//            int loginTimes = cursor.getInt(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME));
//            String iconUrl = cursor.getString(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_ICON_PATH_NAME));
//            int loginState = cursor.getInt(cursor.getColumnIndex(AccountConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME));
//            AccountInfo accountInfo = new AccountInfo();
//            accountInfo.set_accountName(accountName);
//            accountInfo.set_password(accountPassword);
//            accountInfo.set_userIconPath(iconUrl);
//            accountInfo.set_loginTime(loginTimes);
//            accountInfo.set_loginState(loginState);
//            Log.v("ShowAccountInfo  ", accountName + " accountName; " + accountPassword + " accountPassword");
//
//            accountInfos.add(accountInfo);
//        }
//        Log.v("getAllAccountInfo  ", "已经 搜索到 所有的账号");
//        return accountInfos;
//    }


  /*  public int insert(String nullColumnHack, ContentValues values)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        long result = StockSystemUtils.getStockSystemSQLHelper().insert(StockSystemConstant.ACCOUNT_TABLE_NAME, nullColumnHack, values);
        return (int)result;

       *//* _sqlDatabase = getWritableDatabase();
        if (_sqlDatabase == null)
        {
            return -1;
        }
        long result = _sqlDatabase.insert(AccountConstant.ACCOUNT_TABLE_NAME, nullColumnHack, values);
        return (int)result;*//*
    }
*/

 /*   public int delete(String whereClause, String[] whereArgs)
    {
        if (StockSystemUtils.getStockSystemSQLHelper() == null)
        {
            return -1;
        }
        int result = StockSystemUtils.getStockSystemSQLHelper().delete(StockSystemConstant.ACCOUNT_TABLE_NAME, whereClause, whereArgs);
        return result;
        *//*_sqlDatabase = getWritableDatabase();
        if (_sqlDatabase == null)
        {
            return -1;
        }
        int result = _sqlDatabase.delete(AccountConstant.ACCOUNT_TABLE_NAME, whereClause, whereArgs);
        return (int)result;*//*
    }
*/
//    public Cursor query(String[] columns, String selection,
//                        String[] selectionArgs, String groupBy, String having,
//                        String orderBy)
//    {
//
//        if (StockSystemUtils.getStockSystemSQLHelper() == null)
//        {
//            return null;
//        }
//        Cursor resultCursor = StockSystemUtils.getStockSystemSQLHelper().query(StockSystemConstant.ACCOUNT_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
//        return resultCursor;
////        _sqlDatabase = getReadableDatabase();
////        if (_sqlDatabase == null)
////        {
////            return null;
////        }
////        Cursor resultCursor = _sqlDatabase.query(AccountConstant.ACCOUNT_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
////        return resultCursor;
//    }


  /*  public boolean isExist(String selection,
                           String[] selectionArgs)
    {
        Cursor cursor = query(null, selection, selectionArgs, "","","");
        if (cursor == null || cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }*/


}
