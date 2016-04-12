package example.ruanjian.stocksystem.utils;

import android.net.Uri;
import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.content.ContentValues;
import android.graphics.BitmapFactory;

import java.util.List;
import java.util.Iterator;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;

import example.ruanjian.stocksystem.info.AccountInfo;

public class AccountUtils
{
    public static AccountInfo curSelectAccountInfo = null;

    private static AccountInfo _curLoginAccountInfo = null;

    private static List<AccountInfo> _accountInfoList = new ArrayList<AccountInfo>();

    public static int curLoginPosition = 0;

    public static void cleanCurAccount()
    {
        _curLoginAccountInfo = null;
    }

    public static void setCurLoginAccountInfo(AccountInfo accountInfo)
    {
        _curLoginAccountInfo = accountInfo;
        int len = _accountInfoList.size();
        for (int index = 0; index < len; index++)
        {
            AccountInfo tempAccountInfo = _accountInfoList.get(index);
            if (accountInfo.get_accountName().equals(tempAccountInfo.get_accountName()))
            {
                curLoginPosition = index;
                return ;
            }
        }
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

    public static void addAccountInfo( AccountInfo addAccountInfo)
    {
        if (_accountInfoList == null)
        {
            _accountInfoList = new ArrayList<AccountInfo>();
        }
        AccountInfo accountInfo;
        Iterator<AccountInfo> accountIterator = _accountInfoList.iterator();
        while (accountIterator.hasNext())
        {
            accountInfo = accountIterator.next();
            if (accountInfo.get_accountName().equals(addAccountInfo.get_accountName()))
            {
                return ;
            }
        }
        _accountInfoList.add(addAccountInfo);
    }

    public static AccountInfo getAccountInfoByAccountName(String accountName)
    {
        if (_accountInfoList == null)
        {
            return null;
        }
        AccountInfo accountInfo;
        Iterator<AccountInfo> accountIterator = _accountInfoList.iterator();
        while (accountIterator.hasNext())
        {
            accountInfo = accountIterator.next();
            if (accountInfo.get_accountName().equals(accountName))
            {
                return accountInfo;
            }
        }
        return null;
    }

    public static void exitAccount()
    {
        cleanCurAccount();
    }


    private static void updateLoginState(int loginState, String accountName)
    {
        if (accountName == null)
        {
            return;
        }
        AccountInfo tempAccountInfo;
        Iterator<AccountInfo> accountIterator = _accountInfoList.iterator();
        while (accountIterator.hasNext())
        {
            tempAccountInfo = accountIterator.next();
            if (tempAccountInfo.get_accountName().equals(accountName))
            {
                tempAccountInfo.set_loginState(loginState);
            }
        }

    }


    public static void switchLoginAccount(String accountName)
    {
        if (_curLoginAccountInfo != null)
        {
            String curAccountName = _curLoginAccountInfo.get_accountName();
            updateLoginState(StockSystemConstant.STATE_CANCELED, curAccountName);
        }
        updateLoginState(StockSystemConstant.STATE_SUCCESS, accountName);

    }

    public static void removeAccountInfo(String accountName)
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
        AccountInfo accountInfo;
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
        if (iconPath.equals(StockSystemConstant.DEFAULT) == false)
        {
            Uri uri = Uri.parse(iconPath);
            InputStream inputStream;
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

        int resultIndex = 1;
//        int resultIndex = accountSQLUtils.update(contentValues, selection, selectionArgs);
        if (resultIndex >= 0)
        {
            _curLoginAccountInfo.set_userIconPath(iconPath);
            AccountInfo accountInfo;
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
