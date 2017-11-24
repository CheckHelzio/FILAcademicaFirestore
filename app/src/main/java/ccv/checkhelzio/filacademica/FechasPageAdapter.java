package ccv.checkhelzio.filacademica;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


public class FechasPageAdapter extends FragmentPagerAdapter {
    private final String tabTitles[] = new String[]{"25 NOV", "26 NOV", "27 NOV", "28 NOV", "29 NOV", "30 NOV", "01 DIC", "02 DIC", "03 DIC"};

    public FechasPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return FechaFragment.newInstance(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}