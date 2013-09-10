package com.kienlt.searchappwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SearchActivity extends Activity {
	private List<String> components = new ArrayList<String>();
	private Map<String, String> mMapAppPackage = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.autocomplete_1);
        
		try {
			components = getInstalledComponentList();
			mMapAppPackage = getInstalledAppList();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, components);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit);
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
//        textView.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				PackageManager pm = getPackageManager();
//				startActivity(pm.getLaunchIntentForPackage(components.get(arg2)));
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
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
    
//    private List<String> getAllPackageName() {
//    	List<String> packageName = new ArrayList<String>();
//    	final PackageManager pm = getPackageManager();
//    	//get a list of installed apps.
//    	List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//    	for (ApplicationInfo packageInfo : packages) {
//    	    Log.d("KienLT", "Installed package :" + packageInfo.packageName);
//    	    Log.d("KienLT", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
//    	    packageName.add(packageInfo.packageName);
//    	}
//    	
//    	return packageName;
//    }
//    private List<String> getInstalledComponentList() {
//        Intent componentSearchIntent = new Intent();
//        componentSearchIntent.addCategory(Constants.COMPONENTS_INTENT_CATEGORY);
//        componentSearchIntent.setAction(Constants.COMPONENTS_INTENT_ACTION_DEFAULT);
//        List<ResolveInfo> ril = getPackageManager().queryIntentActivities(componentSearchIntent, PackageManager.MATCH_DEFAULT_ONLY);
//        List<String> componentList = new ArrayList<String>();
//        Log.d("KienLT", "Search for installed components found " + ril.size() + " matches.");
//        for (ResolveInfo ri : ril) {
//            if (ri.activityInfo != null) {
//                componentList.add(ri.activityInfo.packageName);// + ri.activityInfo.name);
//                Log.d("KienLT", "Found installed: " + componentList.get(componentList.size()-1));
//            }
//        }
//        return componentList;
//    }

}
