package com.kienlt.searchappwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

public class SearchWidget extends AppWidgetProvider {

	private static final String SHOW_DIALOG_ACTION = "com.kienlt.searchappwidget.widgetshowdialog";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		context.getApplicationContext().registerReceiver(this,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

//		updateView(context);
		prepareWidget(context);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {

//		int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//		int level = -1;
//		if (rawlevel >= 0 && scale > 0) {
//			level = (rawlevel * 100) / scale;
//
//			batteryLevel = Integer.toString(level);
//
//		} else {
//			batteryLevel = "err";
//		}
//
//		updateView(context);
		
		// If the intent is the one that signals
	    // to launch the modal popup activity
	    // we launch the activity
		Log.d("KienLT", "test action = " + intent.getAction());
		if (intent.getAction().equals(SHOW_DIALOG_ACTION)) {
			Intent i = new Intent(context, SearchActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}

		super.onReceive(context, intent);
	}

//	public void updateView(Context context) {
//		RemoteViews thisViews = new RemoteViews(context.getApplicationContext()
//				.getPackageName(), R.layout.activity_main);
//		thisViews.setTextViewText(R.id.widget_text, batteryLevel);
//		thisViews.setImageViewResource(R.id.imageView1, widgetImageFrame);
//
//		ComponentName thisWidget = new ComponentName(context, MydBatWidget.class);
//		AppWidgetManager.getInstance(context).updateAppWidget(thisWidget, thisViews);
//	}
	
	private void prepareWidget(Context context) {

	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

	    ComponentName thisWidget = new ComponentName(context, SearchWidget.class);

	    // Fetch all instances of our widget 
	    // from the AppWidgetManager manager.
	    // The user may have added multiple 
	    // instances of the same widget to the 
	    // home screen
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    for (int widgetId : allWidgetIds) {

	      RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

	      // Create intent that launches the
	      // modal popup activity
	      Intent intent = new Intent(context, SearchWidget.class);
	      intent.setAction(SHOW_DIALOG_ACTION);

	      PendingIntent pendingIntent = PendingIntent.getBroadcast(
	          context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

	      // Attach the onclick listener to the widget button
	      remoteViews.setOnClickPendingIntent(R.id.btShow, pendingIntent);
	      appWidgetManager.updateAppWidget(widgetId, remoteViews);

	    }
	  }

}