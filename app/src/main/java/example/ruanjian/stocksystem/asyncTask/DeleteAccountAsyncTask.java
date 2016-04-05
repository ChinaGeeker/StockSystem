package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.app.AlertDialog;

import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.activity.LoginActivity;
import example.ruanjian.stocksystem.utils.AccountStockUtils;
import example.ruanjian.stocksystem.manager.AlertDialogManager;
import example.ruanjian.stocksystem.utils.HistoryBrowsingUtils;

public class DeleteAccountAsyncTask extends AsyncTask<String, Void, Integer>
{
    private String _accountName = "";
    private AlertDialog _alertDialog;
    private LoginActivity _loginActivity;

    public DeleteAccountAsyncTask(LoginActivity loginActivity, AlertDialog alertDialog)
    {
        this._alertDialog = alertDialog;
        this._loginActivity = loginActivity;
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        _accountName = params[0];
        int resultIndex = AccountUtils.delete(_accountName);
        AccountStockUtils.deleteByAccountName(_accountName);
        HistoryBrowsingUtils.deleteHistoryRecordByAccountName(_accountName);
        return resultIndex;
    }

    @Override
    protected void onPostExecute(Integer resultIndex)
    {
       /* if (resultIndex <= -1)
        {
            Log.v("测试", "删除账号失败");
        }
        else
        {
            Log.v("测试", "删除账号成功g");
        }*/
        AlertDialogManager.closeAlertDialog(_alertDialog);
        AccountInfo curAccountInfo = AccountUtils.curSelectAccountInfo;
        if (curAccountInfo != null && curAccountInfo.get_accountName().equals(_accountName))
        {
            AccountUtils.curSelectAccountInfo = null;
        }
        _loginActivity.refreshLogin();
    }


}
