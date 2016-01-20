package org.laosao.festivalsms;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.laosao.festivalsms.fragment.FestivalCategoryFragment;
import org.laosao.festivalsms.fragment.SmsHistoryFragment;

public class MainActivity extends AppCompatActivity {

	private TabLayout tl;
	private ViewPager vp;


	private String[] mTitles = new String[]{"节日短信", "发信记录"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		tl = (TabLayout) findViewById(R.id.tl);
		vp = (ViewPager) findViewById(R.id.vp);

		vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				if (position == 1) {
					return new SmsHistoryFragment();
				}
				return new FestivalCategoryFragment();

			}

			@Override
			public int getCount() {
				return mTitles.length;
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return mTitles[position];
			}
		});

		tl.setupWithViewPager(vp);
	}
}
