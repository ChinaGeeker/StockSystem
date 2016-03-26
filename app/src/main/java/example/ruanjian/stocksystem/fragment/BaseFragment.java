package example.ruanjian.stocksystem.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    protected boolean isInited = false; //是否初始化界面完成
    protected boolean isVisible = false; //是否显示状态

//    protected boolean isLoaded = false; //是否加载过

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser == true)
        {
            onVisble();
        }
        else
        {
            onInvisble();
        }
    }

    protected void onVisble()
    {
        loading();
    }

    protected void onInvisble()
    {

    }

    public abstract void loading();//实体类 子类  必须重写

}
