package results.sara.com.sararesults;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sara Results");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        i = getIntent();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    Result r=new Result();
                    Bundle b1=new Bundle();
                    b1.putStringArray("Scode",i.getStringArrayExtra("Scode"));
                    b1.putStringArray("Sname",i.getStringArrayExtra("Sname"));
                    b1.putStringArray("Grade",i.getStringArrayExtra("Grade"));
                    b1.putStringArray("Result",i.getStringArrayExtra("Result"));
                    b1.putStringArray("Credit",i.getStringArrayExtra("Credit"));
                    b1.putStringArray("Semester",i.getStringArrayExtra("Semester"));
                    b1.putStringArray("Profile",i.getStringArrayExtra("Profile"));
                    r.setArguments(b1);
                    return r;
                case 1:
                    Profile p=new Profile();
                    Bundle b2=new Bundle();
                    b2.putStringArray("Scode",i.getStringArrayExtra("Scode"));
                    b2.putStringArray("Sname",i.getStringArrayExtra("Sname"));
                    b2.putStringArray("Grade",i.getStringArrayExtra("Grade"));
                    b2.putStringArray("Result",i.getStringArrayExtra("Result"));
                    b2.putStringArray("Credit",i.getStringArrayExtra("Credit"));
                    b2.putStringArray("Semester",i.getStringArrayExtra("Semester"));
                    b2.putStringArray("Profile",i.getStringArrayExtra("Profile"));
                    p.setArguments(b2);
                    return p;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Result";
                case 1:
                    return "Profile";
            }
            return null;
        }
    }
}
