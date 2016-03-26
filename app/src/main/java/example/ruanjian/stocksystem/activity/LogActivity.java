package example.ruanjian.stocksystem.activity;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.utils.StockSystemUtils;
import example.ruanjian.stocksystem.manager.ActivityAppManager;
import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class LogActivity extends Activity implements View.OnClickListener
{
    private TextView _logTxt;
    private Button _cleanLogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);
        _cleanLogBtn = (Button) findViewById(R.id.cleanLogBtn);
        _logTxt = (TextView) findViewById(R.id.log_layout_logTxt);

        _cleanLogBtn.setOnClickListener(this);

        ActivityAppManager.getInstance().addActivity(this);
        initView();
    }

    private void initView()
    {
        try {
            FileInputStream fileInputStream = openFileInput(StockSystemConstant.LOG_FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = "";
            StringBuffer stringBuffer = new StringBuffer();
            while((temp = bufferedReader.readLine()) != null)
            {
//                stringBuffer.append(UtilsConstant.LINE_BREAK);
                stringBuffer.append(temp);
            }
            if (stringBuffer.toString().equals("") == false)
            {
                _logTxt.setText(stringBuffer.toString());
            }
        } catch (FileNotFoundException e) {
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        }catch (IOException e) {
            StockSystemUtils.printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        }
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
            case R.id.cleanLogBtn:
                StockSystemUtils.cleanLog();
                _logTxt.setText(R.string.noLog);
                break;
            default:
                break;
        }
    }

}
