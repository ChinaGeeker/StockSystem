package example.ruanjian.stocksystem.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.KeyEvent;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;

import java.util.List;
import java.util.ArrayList;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.fragment.SettingFragment;
import example.ruanjian.stocksystem.interce.IFragmentHandler;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.adapter.StockPagerAdapter;
import example.ruanjian.stocksystem.fragment.StockListFragment;
import example.ruanjian.stocksystem.manager.ActivityAppManager;
import example.ruanjian.stocksystem.service.RealTimeUpdateService;
import example.ruanjian.stocksystem.asyncTask.ExitAccountAsyncTask;
import example.ruanjian.stocksystem.fragment.HistoryRecordFragment;

public class StockMainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, IFragmentHandler,View.OnClickListener
{
    private Button _exitBtn;
    private ImageView _stockListBtn;
    private TextView _accountNameTxt;
    private ViewPager _stockViewPager;
    private ImageView _stockSettingBtn;
    private ImageView _stockHistoryBtn;

    private List<Fragment> _fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_main_activity);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            intent = new Intent();
            intent.setClass(this, StockInfoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        ActivityAppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onResume()
    {
        checkAccount();
        super.onResume();
    }

    public void checkAccount()
    {
        if (AccountUtils.getCurLoginAccountInfo() == null)
        {
            _accountNameTxt.setText(R.string.accountEmpty);
        }
        else
        {
            _accountNameTxt.setText(AccountUtils.getCurLoginAccountInfo().get_accountName());
        }
    }

    @Override
    public void handler(int type, Object obj)
    {
        switch (type)
        {
            case StockSystemConstant.TYPE_LIST:
                if (_stockViewPager.getCurrentItem() != type)
                {
                    _stockViewPager.setCurrentItem(type);
                }
                ((StockListFragment)(_fragmentList.get(type))).refreshListView();
                break;
            case StockSystemConstant.TYPE_HISTORY:
                if (_stockViewPager.getCurrentItem() != type)
                {
                    _stockViewPager.setCurrentItem(type);
                }
                ((HistoryRecordFragment)(_fragmentList.get(type))).refreshListView();
                break;
            case StockSystemConstant.TYPE_SETTING:
                if (_stockViewPager.getCurrentItem() != type)
                {
                    _stockViewPager.setCurrentItem(type);
                }
                break;
        }
    }

    public void initView()
    {
        _stockListBtn = (ImageView) findViewById(R.id.stock_listBtn);
        _stockHistoryBtn = (ImageView) findViewById(R.id.history_recordBtn);
        _stockSettingBtn = (ImageView) findViewById(R.id.stock_settingBtn);
        _stockListBtn.setOnClickListener(this);
        _stockHistoryBtn.setOnClickListener(this);
        _stockSettingBtn.setOnClickListener(this);

        _fragmentList = new ArrayList<Fragment>();
        _fragmentList.add(new StockListFragment());
        _fragmentList.add(new HistoryRecordFragment());
        _fragmentList.add(new SettingFragment());

        _stockViewPager = (ViewPager) findViewById(R.id.stock_main_viewPager);
        _stockViewPager.setAdapter(new StockPagerAdapter(getSupportFragmentManager(), _fragmentList));
        _stockViewPager.setCurrentItem(0);
        _stockViewPager.setOffscreenPageLimit(1);
        _stockViewPager.addOnPageChangeListener(this);

        _accountNameTxt = (TextView) findViewById(R.id.stock_accountNameTxt);
        _exitBtn = (Button) findViewById(R.id.stock_exitBtn);
        _exitBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        ActivityAppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v)
    {
        int viewId = v.getId();
        switch (viewId)
        {
            case R.id.stock_settingBtn:
                chooseTypeBtn(StockSystemConstant.TYPE_SETTING);
                break;
            case R.id.history_recordBtn:
                chooseTypeBtn(StockSystemConstant.TYPE_HISTORY);
                break;
            case R.id.stock_listBtn:
                chooseTypeBtn(StockSystemConstant.TYPE_LIST);
                break;
            case R.id.stock_exitBtn:
                new ExitAccountAsyncTask(StockMainActivity.this).execute();
                break;
        }
    }

    public void chooseTypeBtn(int typeIndex)
    {
        switch (typeIndex)
        {
            case StockSystemConstant.TYPE_SETTING:
                _stockListBtn.setAlpha(0f);
                _stockHistoryBtn.setAlpha(0f);
                _stockSettingBtn.setAlpha(1f);
                _stockViewPager.setCurrentItem(StockSystemConstant.TYPE_SETTING, false);
                break;
            case StockSystemConstant.TYPE_HISTORY:
                _stockListBtn.setAlpha(0f);
                _stockHistoryBtn.setAlpha(1f);
                _stockSettingBtn.setAlpha(0f);
                _stockViewPager.setCurrentItem(StockSystemConstant.TYPE_HISTORY, false);
                break;
            case StockSystemConstant.TYPE_LIST:
                _stockListBtn.setAlpha(1f);
                _stockHistoryBtn.setAlpha(0f);
                _stockSettingBtn.setAlpha(0f);
                _stockViewPager.setCurrentItem(StockSystemConstant.TYPE_LIST, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        if (position == StockSystemConstant.TYPE_LIST)
        {
            _stockListBtn.setAlpha(1 - positionOffset);
            _stockHistoryBtn.setAlpha(positionOffset);
            _stockSettingBtn.setAlpha(0f);
        }
        if (position == StockSystemConstant.TYPE_HISTORY)
        {
            _stockListBtn.setAlpha(0f);
            _stockSettingBtn.setAlpha(positionOffset);
            _stockHistoryBtn.setAlpha(1 - positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position)
    {
        chooseTypeBtn(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (KeyEvent.KEYCODE_BACK == keyCode)
        {
            ActivityAppManager.getInstance().exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
