package example.ruanjian.stocksystem.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.info.StockInfo;

public class NetworkUtils
{

    public static int getStockInfo(String stockNumber)
    {
        int resultIndex = StockSystemConstant.STATE_SUCCESS;
        String dataUrlStr = StockSystemConstant.QUERY_URL + stockNumber;
        try {
            URL url = new URL(dataUrlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StockSystemConstant.CHARSET_NAME_GBK);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(temp);
            }
            httpURLConnection.disconnect();
            resultIndex =  splitStockData(stringBuffer.toString());

        } catch (UnknownHostException e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        } catch (MalformedURLException e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        } catch (IOException e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        }catch (Exception e) {
            resultIndex = StockSystemConstant.STATE_CANCELED;
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
            e.printStackTrace();
        }
        return resultIndex;
    }

    private static int splitStockData(String stockData)
    {
        int resultIndex = StockSystemConstant.STATE_SUCCESS;
        try {
//                String strBuf = "var hq_str_sys_auth='FAILED'";
            Pattern pattern = Pattern.compile(StockSystemConstant.STOCK_DATA_REX);
            Matcher matcher = pattern.matcher(stockData);
            if (matcher.find() == true)
            {
                String result = matcher.group(2);
                String stockNumber = matcher.group(1);
                if (result.equals(StockSystemConstant.FAILED) || result.equals(""))
                {
                    return StockSystemConstant.STATE_CANCELED;
                }
                StockInfo stockInfo = StockUtils.analysisStockData(result, stockNumber);
                StockUtils.addStockInfo(stockInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
            resultIndex = StockSystemConstant.STATE_CANCELED;
        }
        return resultIndex;
    }


    public static Object sendHttpUrlConnecttionByGet(String url)
    {
        String retultObj = "{" + StockSystemConstant.RETURN_TYPE + ":" + StockSystemConstant.STATE_CANCELED + "," + StockSystemConstant.RETURN_MESSAGE  + ":$}";
        try {
            URL registerUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) registerUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StockSystemConstant.CHARSET_NAME_UTF8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String tempStr ;
            while ((tempStr = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(tempStr);
            }
            retultObj = stringBuffer.toString();
            inputStream.close();
            inputStreamReader.close();
            httpURLConnection.disconnect();
        } catch (UnknownHostException e) {
            retultObj = retultObj.replace("$","网络链接失败，请检查网络设置");
            e.printStackTrace();
        } catch (ConnectException e) {
            retultObj = retultObj.replace("$","服务器异常，请稍后重试");
            e.printStackTrace();
        } catch (SocketException e) {
            retultObj = retultObj.replace("$","服务器异常，请稍后重试");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            retultObj = retultObj.replace("$","开发者的问题");
            e.printStackTrace();
        } catch (IOException e) {
            retultObj = retultObj.replace("$","开发者的问题");
            e.printStackTrace();
        } catch (Exception e) {
            retultObj = retultObj.replace("$","开发者的问题");
            e.printStackTrace();
        }
        return retultObj;
    }



    public static Object sendHttpUrlConnecttionByPost(String url, String parameterData)
    {
        String retultObj = "{" + StockSystemConstant.RETURN_TYPE + ":" + StockSystemConstant.STATE_CANCELED + "," + StockSystemConstant.RETURN_MESSAGE  + ":$}";
        try {
            URL registerUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) registerUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.getOutputStream().write(parameterData.getBytes());
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StockSystemConstant.CHARSET_NAME_UTF8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String tempStr ;
            while ((tempStr = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(tempStr);
            }
            retultObj = stringBuffer.toString();
            inputStream.close();
            inputStreamReader.close();
            httpURLConnection.disconnect();
        } catch (UnknownHostException e) {
            retultObj = retultObj.replace("$","网络链接失败，请检查网络设置");
            e.printStackTrace();
        } catch (ConnectException e) {
            retultObj = retultObj.replace("$","服务器异常，请稍后重试");
            e.printStackTrace();
        } catch (SocketException e) {
            retultObj = retultObj.replace("$","服务器异常，请稍后重试");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            retultObj = retultObj.replace("$","开发者的问题");
            e.printStackTrace();
        } catch (IOException e) {
            retultObj = retultObj.replace("$","开发者的问题");
            e.printStackTrace();
        } catch (Exception e) {
            retultObj = retultObj.replace("$","开发者的问题");
            e.printStackTrace();
        }
        return retultObj;
    }



    public static Bundle getStockGifImage(String stockNumber)
    {
        Bundle bundle = null;
        URL url = null;
        String gifImageUrl = StockSystemConstant.QUERY_IMG.replace("$", stockNumber);
        try {
            url = new URL(gifImageUrl);


            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.connect();
            bundle = new Bundle();
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            bundle.putParcelable(StockSystemConstant.STOCK_GIF_IMAGE, bitmap);
        }catch (MalformedURLException e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }catch (UnknownHostException e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }catch (IOException e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
        url = null;
        return bundle;
    }








}
