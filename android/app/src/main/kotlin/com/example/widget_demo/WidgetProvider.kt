package com.example.widget_demo

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import es.antonborri.home_widget.HomeWidgetLaunchIntent
import es.antonborri.home_widget.HomeWidgetProvider

class AppWidgetProvider : HomeWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray, widgetData: SharedPreferences) {
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {

                // Open App on Widget Click
                val pendingIntent = HomeWidgetLaunchIntent.getActivity(context,
                    MainActivity::class.java)
                setOnClickPendingIntent(R.id.widget_root, pendingIntent)

                val counter = widgetData.getInt("_counter", 0)

                var counterText = "Your counter value is: $counter"

                if (counter == 0) {
                    counterText = "Send or Receive tags with NextLink"
                }else if(counter < 0){
                    counterText = "Receiving..."
                }else if(counter > 0){
                    counterText = "Sending..."
                }

                setTextViewText(R.id.tv_counter, counterText)

                // Pending intent to update counter on button click
                val backgroundIntentSend = HomeWidgetBackgroundIntent.getBroadcast(context,
                    Uri.parse("myAppWidget://send"))
                setOnClickPendingIntent(R.id.bt_send, backgroundIntentSend)
                // Pending intent to update counter on button click
                val backgroundIntentReceive = HomeWidgetBackgroundIntent.getBroadcast(context,
                    Uri.parse("myAppWidget://receive"))
                setOnClickPendingIntent(R.id.bt_receive, backgroundIntentReceive)
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}