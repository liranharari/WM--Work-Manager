package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;import android.app.PendingIntent;
import android.app.NotificationManager;
import android.util.Log;

/**
 * Created by liran on 07/04/2016.
 */
public class ReminderAlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {

        String customer= intent.getStringExtra("customer");
        String msg= intent.getStringExtra("msg");

        Log.d("reminder", "recieve On");

        String title="תזכורת עבור "+ customer;

        PendingIntent reminderIntent= PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)//()
                .setSmallIcon(R.mipmap.ic_launcher_minima_logo)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(msg);

        mBuilder.setContentIntent(reminderIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(1, mBuilder.build());
    }
}
