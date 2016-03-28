package example.ruanjian.stocksystem;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.graphics.drawable.BitmapDrawable;

import example.ruanjian.stocksystem.info.AccountInfo;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.adapter.AccountAdapter;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.activity.RegisterActivity;
import example.ruanjian.stocksystem.manager.ActivityAppManager;
import example.ruanjian.stocksystem.activity.StockMainActivity;
import example.ruanjian.stocksystem.databases.StockSystemSQLHelper;
import example.ruanjian.stocksystem.asyncTask.LoginAccountAsyncTask;
import example.ruanjian.stocksystem.asyncTask.GetAllAccountAsyncTask;

public class LoginActivity extends Activity implements View.OnClickListener
{
    /*1、第一次需要输入账户、密码登陆（可以本地注册），第二次默认登陆，具有切换账号的功能
    2、记录当前用户的股票游览记录，实时更新当前的股票行情，需要显示股票代号、名称、价格、涨幅度
    3、用户可以选择照片或者拍摄照片做为自己的头像，（圆形）
    4、页面有3个Tab栏，当前所选股票、历史游览记录、个人设置
    5、需要加入日志系统，供BUG分析，以文件形式存储，可以定时删除日志或屏蔽日志
    6、当股票波动大于5%，需要有通知和震动提醒
    7 需要根据当前的网络类型，让用户选择是否停止更新数据
    最好是能看到设计模式的影子  UI更新 不推荐用广播 封装service层*/
    private Button _loginBtn;
    private Button _accountBtn;
    private ImageView _iconImage;
    private View _popupWindowView;
    private EditText _passwordTxt;
    private ListView _popupListView;
    private EditText _accountNameTxt;
    private TextView _loginRegisterTxt;
    private AccountAdapter _accountAdapter;
    private ProgressDialog _progressDialog;
    private PopupWindow _accountPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        StockSystemUtils.context = this;
        ActivityAppManager.getInstance().addActivity(this);
        StockSystemUtils.initUtils(StockSystemSQLHelper.getInstance(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
        int accountLen = AccountUtils.getAllAccountInfo().size();
        if (accountLen <= 0)
        {
            showProgressDialog();
        }
        else
        {
            refreshLogin();
        }
    }

    private void initView()
    {
        _accountAdapter = new AccountAdapter(this);
        _loginBtn = (Button) findViewById(R.id.loginBtn);
        _accountBtn = (Button) findViewById(R.id.login_accountBtn);
        _iconImage = (ImageView) findViewById(R.id.login_iconImage);
        _passwordTxt = (EditText) findViewById(R.id.login_passwordTxt);
        _accountNameTxt = (EditText) findViewById(R.id.login_accountTxt);
        _loginRegisterTxt = (TextView) findViewById(R.id.login_registerTxt);

        _loginBtn.setOnClickListener(this);
        _accountBtn.setOnClickListener(this);
        _loginRegisterTxt.setOnClickListener(this);
    }

    private void updateData(AccountInfo accountInfo)
    {
        if(accountInfo == null)
        {
            _passwordTxt.setText("");
            _accountNameTxt.setText("");
            return;
        }
        _passwordTxt.setText(accountInfo.get_password());
        _accountNameTxt.setText(accountInfo.get_accountName());
        _accountNameTxt.setSelection(accountInfo.get_accountName().length());

        Bitmap bitmap = AccountUtils.getBitmapByIconPath(accountInfo.get_userIconPath(), this);
        if (bitmap != null)
        {
            _iconImage.setImageBitmap(StockSystemUtils.toRoundBitmap(bitmap));
        }
        else
        {
            BitmapDrawable bitmapDrawable = null;
            int sdkVerion = Build.VERSION.SDK_INT;
            if (sdkVerion >= 21)
            {
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.icon, null);
            }
            else
            {
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.icon);
            }
            _iconImage.setImageBitmap(StockSystemUtils.toRoundBitmap(bitmapDrawable.getBitmap()));
        }
    }

    private void showPopupWindow()
    {
        if (_accountPopupWindow == null)
        {
            _popupWindowView = LayoutInflater.from(this).inflate(R.layout.login_popup_window, null);
            _popupListView = (ListView) _popupWindowView.findViewById(R.id.login_listView);
            _popupListView.setAdapter(_accountAdapter);

            _popupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   AccountInfo accountInfo = (AccountInfo) parent.getItemAtPosition(position);
                   AccountUtils.curSelectAccountInfo = accountInfo;
                   updateData(accountInfo);
                    _accountPopupWindow.dismiss();
                }
            });

            _accountPopupWindow = new PopupWindow(_popupWindowView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            _accountPopupWindow.setFocusable(true);
            _accountPopupWindow.setTouchable(true);
            _accountPopupWindow.setOutsideTouchable(true);
            _accountPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        _accountAdapter.notifyDataSetChanged();
        _accountPopupWindow.showAsDropDown(_accountNameTxt, 0, 0);
    }

    public void refreshLogin()
    {
        if (_progressDialog != null)
        {
            _progressDialog.dismiss();
        }
        if (_accountPopupWindow != null)
        {
            _accountPopupWindow.dismiss();
        }
        if (AccountUtils.getCurLoginAccountInfo() == null)
        {
            updateData(AccountUtils.curSelectAccountInfo);
            if (AccountUtils.getAllAccountInfo() != null && AccountUtils.getAllAccountInfo().size() >= 1 && (AccountUtils.curSelectAccountInfo == null))
            {
                AccountInfo accountInfo = AccountUtils.getAllAccountInfo().get(0);
                AccountUtils.curSelectAccountInfo = accountInfo;
                updateData(accountInfo);
            }
        }
        else
        {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, StockMainActivity.class);
            Intent deintent = getIntent();
            Bundle bundle = deintent.getExtras();
            if (bundle != null)
            {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        if (_progressDialog != null)
        {
            _progressDialog.dismiss();
        }
        StockSystemSQLHelper.getInstance(this).close();
        ActivityAppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    private void showProgressDialog()
    {
        if (_progressDialog == null)
        {
            _progressDialog = new ProgressDialog(this);
        }
        _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage(getResources().getString(R.string.app_name));
        _progressDialog.setTitle(R.string.login_info);
        _progressDialog.setCancelable(true);
        _progressDialog.show();
        new GetAllAccountAsyncTask(this).execute();
    }

    @Override
    protected void onResume()
    {
        StockSystemUtils.cancelAllNotifition();
        super.onResume();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v)
    {
        int viewId = v.getId();
        Intent intent = new Intent();
        switch (viewId)
        {
            case R.id.login_accountBtn:
                showPopupWindow();
                break;
            case R.id.login_registerTxt:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:
                if (_accountNameTxt.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this,  R.string.accountNotEmpty, Toast.LENGTH_LONG).show();
                    return;
                }
                if (_passwordTxt.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, R.string.passwordNotEmpty, Toast.LENGTH_LONG).show();
                    return;
                }
                String accountNameStr = _accountNameTxt.getText().toString();
                String passwordStr = _passwordTxt.getText().toString();
                new LoginAccountAsyncTask(this).execute(accountNameStr, passwordStr);
                break;
        }
    }


}
