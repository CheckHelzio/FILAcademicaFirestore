package ccv.checkhelzio.filacademica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ActividadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividades_activity_layout);
        iniciarToolbar();
        iniciarPager();
    }

    private void iniciarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void iniciarPager() {
        TabLayout mTabLayout = findViewById(R.id.tablayout);
        FechasPageAdapter mFechasPagerAdapter = new FechasPageAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mFechasPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
