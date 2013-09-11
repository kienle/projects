package com.kienlt.searchappwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SearchActivity extends Activity {
	private LinearLayout mRoot;
	
	private List<String> components = new ArrayList<String>();
	private Map<String, String> mMapAppPackage = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.autocomplete_1);
        
        mRoot = (LinearLayout) findViewById(R.id.root);
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        mRoot.setPadding(10, 10, 10, 10);
        mRoot.setLayoutParams(params);
        
//		try {
//			components = getInstalledComponentList();
//			mMapAppPackage = getInstalledAppList();
//		} catch (NameNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		textViewParams.setMargins(10, 10, 10, 10);
//        
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, components);
//        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit);
//        textView.setLayoutParams(textViewParams);
//        textView.requestFocus();
//        textView.setAdapter(adapter);
//        textView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				PackageManager pm = getPackageManager();
//				startActivity(pm.getLaunchIntentForPackage(mMapAppPackage.get(arg0.getItemAtPosition(arg2).toString())));
//			}
//		});
        
        GetDataTask task = new GetDataTask();
        task.execute();
    }
    
    private class GetDataTask extends AsyncTask<Void, Void, List<String>> {
    	@Override
    	protected List<String> doInBackground(Void... params) {

    		try {
    			components = getInstalledComponentList();
    			mMapAppPackage = getInstalledAppList();
    		} catch (NameNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		return components;
    	}
    	
    	@Override
    	protected void onPostExecute(List<String> result) {
    		super.onPostExecute(result);
    		
    		LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    		textViewParams.setMargins(10, 10, 10, 10);
    		
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,
                    android.R.layout.simple_dropdown_item_1line, result);
            AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit);
            textView.setLayoutParams(textViewParams);
            textView.requestFocus();
            textView.setAdapter(adapter);
            textView.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				// TODO Auto-generated method stub
    				PackageManager pm = getPackageManager();
    				startActivity(pm.getLaunchIntentForPackage(mMapAppPackage.get(arg0.getItemAtPosition(arg2).toString())));
    			}
    		});
    		
    	}
    }
    
    
    private List<String> getInstalledComponentList() throws NameNotFoundException {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
        List<String> componentList = new ArrayList<String>();
        String name = null;

        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                Resources res = getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                } else {
                    name = ri.activityInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                }
                componentList.add(name);
            }
        }
        return componentList;
    }
    
    private Map<String, String> getInstalledAppList() throws NameNotFoundException {
    	final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    	mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    	List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
//    	List<String> componentList = new ArrayList<String>();
    	Map<String, String> list = new HashMap<String, String>();
    	String name = null;
    	
    	for (ResolveInfo ri : ril) {
    		if (ri.activityInfo != null) {
    			Resources res = getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
    			if (ri.activityInfo.labelRes != 0) {
    				name = res.getString(ri.activityInfo.labelRes);
    			} else {
    				name = ri.activityInfo.applicationInfo.loadLabel(getPackageManager()).toString();
    			}
    			
    			list.put(name, ri.activityInfo.applicationInfo.packageName);
//    			componentList.add(name);
    		}
    	}
    	return list;
    }

}
