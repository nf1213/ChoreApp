package com.github.nf1213.choreapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import java.util.Date;

import static com.google.android.gms.gcm.GcmNetworkManager.RESULT_SUCCESS;
import static com.google.android.gms.gcm.Task.NETWORK_STATE_ANY;

/**
 * Created by Nicole Felch on 6/27/17.
 */
public class ReminderTask extends GcmTaskService {

    private static final String TAG_NOTIFICATION = "REMINDER_";
    private static final long ONE_HOUR_IN_SECONDS = 8600;

    protected static void schedule(Context context, Date sendDate) {
        long deliveryTime = (sendDate.getTime() - System.currentTimeMillis()) / 1000;
        Bundle extras = new Bundle();
        Task task = new OneoffTask.Builder()
                .setService(ReminderTask.class)
                .setTag(TAG_NOTIFICATION + deliveryTime)
                .setRequiredNetwork(NETWORK_STATE_ANY)
                .setExecutionWindow(deliveryTime, deliveryTime + ONE_HOUR_IN_SECONDS)
                .setPersisted(true)
                .setExtras(extras)
                .build();
        GcmNetworkManager.getInstance(context).schedule(task);
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Intent intent = new Intent(this, ChoreListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        
        return RESULT_SUCCESS;
    }
}
