package example.ruanjian.stocksystem.utils;

import android.net.Uri;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.content.ContentValues;
import android.graphics.BitmapFactory;

import java.util.List;
import java.util.Iterator;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;

import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.databases.sqlUtils.AccountSQLUtils;

public class AccountUtils
{
    public static AccountSQLUtils accountSQLUtils;

    public static AccountInfo curSelectAccountInfo = null;

    private static AccountInfo _curLoginAccountInfo = null;

    private static List<AccountInfo> _accountInfoList = new ArrayList<AccountInfo>();

    public static int delete(String accountName)
    {
        String selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=? ";
        String[] selectionArgs = new String[]{accountName};
        removeAccountInfo(accountName);
        return accountSQLUtils.delete(selection, selectionArgs);
    }

    public static void cleanCurAccount()
    {
        _curLoginAccountInfo = null;
    }

    public static void setCurLoginAccountInfo(AccountInfo accountInfo)
    {
        _curLoginAccountInfo = accountInfo;
    }


    public static AccountInfo getCurLoginAccountInfo()
    {
        return _curLoginAccountInfo;
    }


    public static List<AccountInfo> getAllAccountInfo()
    {
        if (_accountInfoList == null)
        {
            _accountInfoList = new ArrayList<AccountInfo>();
        }
        return _accountInfoList;
    }

    public static boolean isExist(String selection, String[] selectionArgs)
    {
        Cursor cursor = accountSQLUtils.query(null, selection, selectionArgs, "", "", "");
        if (cursor == null || cursor.getCount() <= 0)
        {
            return false;
        }
        return true;
    }


    public static void addAccountInfo( AccountInfo addAccountInfo)
    {
        if (_accountInfoList == null)
        {
            _accountInfoList = new ArrayList<AccountInfo>();
        }
        AccountInfo accountInfo = null;
        Iterator<AccountInfo> accontIterator = _accountInfoList.iterator();
        while (accontIterator.hasNext())
        {
            accountInfo = accontIterator.next();
            if (accountInfo.get_accountName().equals(addAccountInfo.get_accountName()))
            {
                return ;
            }
        }
        _accountInfoList.add(addAccountInfo);
    }


    public static int insertAccount(String nullColumnHack, ContentValues values)
    {
       return accountSQLUtils.insert(nullColumnHack, values);
    }


    public static AccountInfo getAccountInfoByAccountName(String accountName)
    {
        if (_accountInfoList == null)
        {
            return null;
        }
        AccountInfo accountInfo = null;
        Iterator<AccountInfo> accontIterator = _accountInfoList.iterator();
        while (accontIterator.hasNext())
        {
            accountInfo = accontIterator.next();
            if (accountInfo.get_accountName().equals(accountName))
            {
                return accountInfo;
            }
        }
        return null;
    }

    public static void queryAllAccountInfo()
    {
        if (_accountInfoList == null)
        {
            _accountInfoList = new ArrayList<AccountInfo>();
        }
        _accountInfoList.clear();
        String orderBy = StockSystemConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME + " desc";
        Cursor cursor = accountSQLUtils.query(null, null, null, null, null, orderBy);
        if (cursor == null || cursor.getCount() <= 0)
        {
            return;
        }
        boolean isFirst = true;
        while (cursor.moveToNext())
        {
            String accountName = cursor.getString(cursor.getColumnIndex(StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME));
            String accountPassword = cursor.getString(cursor.getColumnIndex(StockSystemConstant.ACCOUNT_COLUMN_PASSWORD_NAME));
            int loginTimes = cursor.getInt(cursor.getColumnIndex(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME));
            String iconUrl = cursor.getString(cursor.getColumnIndex(StockSystemConstant.ACCOUNT_COLUMN_ICON_PATH_NAME));
            int loginState = cursor.getInt(cursor.getColumnIndex(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME));
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.set_accountName(accountName);
            accountInfo.set_password(accountPassword);
            accountInfo.set_userIconPath(iconUrl);
            accountInfo.set_loginTime(loginTimes);
            accountInfo.set_loginState(loginState);
            _accountInfoList.add(accountInfo);
            if (loginState == StockSystemConstant.STATE_SUCCESS && (isFirst == true))
            {
                _curLoginAccountInfo = accountInfo;
            }
            isFirst = false;
        }
    }

    public static void exitAccount()
    {
        String accountName = _curLoginAccountInfo.get_accountName();
        String[] selectionArgs = new String[]{accountName};
        String selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME, StockSystemConstant.STATE_CANCELED);
        int resultIndex = accountSQLUtils.update(contentValues, selection, new String[]{accountName});
        /*if (resultIndex <= -1)
        {
            Log.v("测试  退出账号  ", "更新" + accountName + "账号登录状态失败");
        }
        else
        {
            Log.v("测试 退出账号   ", "更新" + accountName + "账号登录状态成功");
        }*/
        cleanCurAccount();
    }

    public static boolean updateLoginAccount(String accountNameStr, String passwordStr)
    {
        boolean isLoginSuccess = false;
        String selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?and " + StockSystemConstant.ACCOUNT_COLUMN_PASSWORD_NAME + "=?";
        String[] selectionArgs = new String[]{accountNameStr, passwordStr};
        boolean isExist = accountSQLUtils.isExist(selection, selectionArgs);
        if (isExist == true)
        {
            selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?";
            ContentValues contentValues = new ContentValues();
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_STATE_NAME, StockSystemConstant.STATE_SUCCESS);
            contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_LOGIN_TIME_NAME, (int)(System.currentTimeMillis() / 1000));
            int resultIndex = accountSQLUtils.update(contentValues, selection, new String[]{accountNameStr});
            /*if (resultIndex <= -1)
            {
                Log.v("测试", "更新" + accountNameStr + "账号登录状态失败");
            }
            else
            {
                Log.v("测试", "更新" + accountNameStr + "账号登录状态成功");
            }*/
            _curLoginAccountInfo = curSelectAccountInfo;
            isLoginSuccess = true;
        }
        return isLoginSuccess;
    }

    private static void removeAccountInfo(String accountName)
    {
        if (_curLoginAccountInfo != null)
        {
            if (_curLoginAccountInfo.get_accountName().equals(accountName))
            {
                _curLoginAccountInfo = null;
            }
        }
        if (curSelectAccountInfo != null)
        {
            if (curSelectAccountInfo.get_accountName().equals(accountName))
            {
                curSelectAccountInfo = null;
            }
        }
        AccountInfo accountInfo = null;
        Iterator<AccountInfo> accontIterator = _accountInfoList.iterator();
        while (accontIterator.hasNext())
        {
            accountInfo = accontIterator.next();
            if (accountInfo.get_accountName().equals(accountName))
            {
                accontIterator.remove();
                break;
            }
        }
    }


    public static Bitmap getBitmapByIconPath(String iconPath, Context context)
    {
        Bitmap bitmap = null;
        if (iconPath != StockSystemConstant.DEFAULT)
        {
            Uri uri = Uri.parse(iconPath);
            InputStream inputStream = null;
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static int updateIcon(String iconPath, Context context)
    {
        String accountName = _curLoginAccountInfo.get_accountName();
        String[] selectionArgs = new String[]{accountName};
        String selection = StockSystemConstant.ACCOUNT_COLUMN_ACCOUNT_NAME + "=?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(StockSystemConstant.ACCOUNT_COLUMN_ICON_PATH_NAME, iconPath);
        int resultIndex = accountSQLUtils.update(contentValues, selection, new String[]{accountName});
        if (resultIndex <= -1)
        {
//            Log.v(iconPath + "   测试    ", "更新  头像   " + accountName + "  失败");
        }
        else
        {
            _curLoginAccountInfo.set_userIconPath(iconPath);
            AccountInfo accountInfo = null;
            Iterator<AccountInfo> accontIterator = _accountInfoList.iterator();
            while (accontIterator.hasNext())
            {
                accountInfo = accontIterator.next();
                if (accountInfo.get_accountName().equals(accountName))
                {
                    accountInfo.set_userIconPath(iconPath);
                    break;
                }
            }
//            Log.v(iconPath + "  测试   ", "更新   头像" + accountName + "  成功");
        }
        return resultIndex;
    }



}
