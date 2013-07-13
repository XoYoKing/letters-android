package com.returnondevelopment.letters;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Letters extends FragmentActivity implements ActionBar.TabListener {

	static final int ITEMS = 10;
	MyAdapter mAdapter;
	CustomViewPager mPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letters);

		mAdapter = new MyAdapter(getSupportFragmentManager());		
		mPager = (CustomViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mPager.setPagingEnabled(false);
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < 5; i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(getPageTitle(i))
					.setTabListener(this));
		}
	}

	public String getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.title_home).toUpperCase(l);
		case 1:
			return getString(R.string.title_more).toUpperCase(l);
		case 2:
			return getString(R.string.title_bookmarks).toUpperCase(l);
		case 3:
			return getString(R.string.title_search).toUpperCase(l);
		case 4:
			return getString(R.string.title_send).toUpperCase(l);			
		}
		return null;
	}			
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letters, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	
	public static class MyAdapter extends FragmentStatePagerAdapter {
		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}
		
		@Override
		public int getCount() {
			return ITEMS;			
		}
		
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);									
			return fragment;
		}
						
	}
	
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_letters_dummy,
					container, false);
			
			WebView dummyWebView = (WebView) rootView.findViewById(R.id.section_webView);
			
			String base_url = "http://www.letterstocrushes.com/mobile/";
			String extension = "";
			
			switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1:
				// home
				extension = "";
				break;
			case 2:
				// more
				extension = "more";
				break;
			case 3:
				extension = "bookmarks";
				break;
			case 4:
				extension = "search";
				break;
			case 5:
				extension = "more";
				break;
			
			}
			
			dummyWebView.getSettings().setJavaScriptEnabled(true);

			dummyWebView.setWebViewClient(new Callback()); 
			dummyWebView.loadUrl(base_url + extension);
						
			return rootView;
		}

		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_home).toUpperCase(l);
			case 1:
				return getString(R.string.title_more).toUpperCase(l);
			case 2:
				return getString(R.string.title_bookmarks).toUpperCase(l);
			case 3:
				return getString(R.string.title_search).toUpperCase(l);
			case 4:
				return getString(R.string.title_send).toUpperCase(l);
				
			}
			return null;
		}		
				

	    private class Callback extends WebViewClient{ 

	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return (false);
	        }

	    }		
		
	}
	
	public class CustomViewPager extends ViewPager {

		 private boolean enabled;

		    public CustomViewPager(Context context, AttributeSet attrs) {
		        super(context, attrs);
		        this.enabled = true;
		    }

		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
		        if (this.enabled) {
		            return super.onTouchEvent(event);
		        }
		  
		        return false;
		    }

		    @Override
		    public boolean onInterceptTouchEvent(MotionEvent event) {
		        if (this.enabled) {
		            return super.onInterceptTouchEvent(event);
		        }
		 
		        return false;
		    }
		 
		    public void setPagingEnabled(boolean enabled) {
		        this.enabled = enabled;
		    }

	}

}
