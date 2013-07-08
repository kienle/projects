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
    
    private List<String> getAllPackageName() {
    	List<String> packageName = new ArrayList<String>();
    	final PackageManager pm = getPackageManager();
    	//get a list of installed apps.
    	List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

    	for (ApplicationInfo packageInfo : packages) {
    	    Log.d("KienLT", "Installed package :" + packageInfo.packageName);
    	    Log.d("KienLT", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
    	    packageName.add(packageInfo.packageName);
    	}
    	
    	return packageName;
    }
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

    static final String[] COUNTRIES = new String[] {
	"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
	"Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
	"Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
	"Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
	"Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
	"Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
	"British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
	"Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
	"Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
	"Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
	"Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
	"Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
	"East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
	"Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
	"Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia",
	"French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
	"Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
	"Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary",
	"Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
	"Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
	"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
	"Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
	"Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
	"Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
	"Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
	"Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
	"Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
	"Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
	"Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
	"Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
	"Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal",
	"Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
	"Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea",
	"Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden",
	"Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas",
	"The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
	"Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
	"Ukraine", "United Arab Emirates", "United Kingdom",
	"United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
	"Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara",
	"Yemen", "Yugoslavia", "Zambia", "Zimbabwe"
    };
}
