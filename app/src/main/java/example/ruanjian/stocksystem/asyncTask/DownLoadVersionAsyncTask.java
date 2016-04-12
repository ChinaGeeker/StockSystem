package example.ruanjian.stocksystem.asyncTask;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.application.StockSystemApplication;

// 测试用的，实际项目要用 service
public class DownLoadVersionAsyncTask extends AsyncTask<String, Integer, Void>
{
    private Context _context;


    public DownLoadVersionAsyncTask(Context context)
    {
        this._context = context;
    }


    private Notification _notification;
    @Override
    protected Void doInBackground(String... params)
    {
        NotificationManager notificationManager = (NotificationManager) StockSystemApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(_context);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.noticationdownicon);
        builder.setAutoCancel(true);
        builder.setTicker("版本更新");
//        builder.setContent() 自定义 view ，在view 显示下载进度
        _notification = builder.build();

        notificationManager.notify(1000, _notification);
        int index = 0;
        boolean isProgress = true;
        while (isProgress)
        {
            publishProgress(index);
            try {
                Thread.sleep(500);
                index++;
                if (index >= 100)
                {
                    index = 100;
                    isProgress = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values)
    {
        int perfer = values[0];
    }

    @Override
    protected void onPostExecute(Void resultIndex)
    {
        // 下载完成 进行 安卓
    }

}