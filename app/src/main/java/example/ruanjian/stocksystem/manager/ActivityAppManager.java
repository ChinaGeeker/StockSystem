package example.ruanjian.stocksystem.manager;

import android.app.Activity;

import java.util.Stack;

import example.ruanjian.stocksystem.application.StockSystemApplication;

public class ActivityAppManager
{

    private static Stack<Activity> _activityStack;

    private static ActivityAppManager _instance;

    public static ActivityAppManager getInstance()
    {
        if (_instance == null)
        {
            _activityStack = new Stack<Activity>();
            _instance = new ActivityAppManager();
        }
        return _instance;
    }


    public void exit()
    {
        for (Activity activity: _activityStack)
        {
            if (activity != null)
            {
                activity.finish();
            }
        }
        _activityStack.clear();
        StockSystemApplication.getInstance().destroy();
    }

    public Activity getActivityBySimpeleName(String simpleName)
    {
        for (Activity activity: _activityStack)
        {
            if (activity != null && (activity.getClass().getSimpleName().equals(simpleName)))
            {
                return activity;
            }
        }
        return null;
    }

    public boolean isEmpty()
    {
        return _activityStack.isEmpty();
    }

    public void removeActivity(Activity activity)
    {
        boolean isExist =_activityStack.contains(activity);
        if (isExist == true)
        {
            _activityStack.remove(activity);
        }
    }

    public void addActivity(Activity activity)
    {
        boolean isExist =_activityStack.contains(activity);
        if (isExist == false && activity != null)
        {
            _activityStack.add(activity);
        }
    }



}
