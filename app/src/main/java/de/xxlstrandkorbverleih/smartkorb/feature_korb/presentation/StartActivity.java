package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.add_edit_korb.AddEditKorbFragment;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common.PagerAdapter;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.körbe.KoerbeFragment;

public class StartActivity extends AppCompatActivity {
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.flFragment);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new KoerbeFragment(), "KoerbeFragment");
        adapter.addFragment(new AddEditKorbFragment(), "AddEditKorbFragment");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int FragmentNumber) {
        mViewPager.setCurrentItem(FragmentNumber);
    }
}