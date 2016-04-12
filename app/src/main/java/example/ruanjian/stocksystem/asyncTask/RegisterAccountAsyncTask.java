package example.ruanjian.stocksystem.asyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import example.ruanjian.stocksystem.utils.NetworkUtils;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.activity.RegisterActivity;

public class RegisterAccountAsyncTask extends AsyncTask<String, Void, Void>
{

    private int _registerResult = 0;

    private String _registerMessage = "";
    private RegisterActivity _registerActivity;

    public RegisterAccountAsyncTask(RegisterActivity registerActivity)
    {
        this._registerActivity = registerActivity;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String accountNameStr = params[0];
        String accountPasswordStr = params[1];
        String parameterData = StockSystemConstant.ACCOUNT_NAME + "=" + accountNameStr+"&"
                + StockSystemConstant.ACCOUNT_COLUMN_PASSWORD_NAME + "=" + accountPasswordStr;
        Object obj = NetworkUtils.sendHttpUrlConnecttionByPost(StockSystemConstant.REGISTER_URL, parameterData);
        try {
            JSONObject jsonObject = new JSONObject(obj.toString());
            _registerResult = jsonObject.getInt(StockSystemConstant.RETURN_TYPE);
            _registerMessage = jsonObject.getString(StockSystemConstant.RETURN_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result)
    {
        Toast.makeText(_registerActivity, _registerMessage, Toast.LENGTH_LONG).show();
        if (_registerResult == StockSystemConstant.STATE_SUCCESS)
        {
            _registerActivity.finish();
        }
    }


}