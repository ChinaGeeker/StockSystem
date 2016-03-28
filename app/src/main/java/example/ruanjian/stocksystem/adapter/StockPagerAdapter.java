package example.ruanjian.stocksystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class StockPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> _fragmentList;

    public StockPagerAdapter(FragmentManager fm, List<Fragment> fragmentList)
    {
        super(fm);
        this._fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position)
    {
        return _fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return _fragmentList.size();
    }


}
