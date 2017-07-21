package com.github.nf1213.choreapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.github.nf1213.choreapp.datastorage.Chore;
import com.github.nf1213.choreapp.ui.MainActivity;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import static com.google.android.gms.gcm.GcmNetworkManager.RESULT_SUCCESS;
import static com.google.android.gms.gcm.Task.NETWORK_STATE_ANY;

/**
 * Created by Nicole Felch on 6/27/17.
 */
public class ReminderTask extends GcmTaskService {

    private static final String TAG_NOTIFICATION = "REMINDER_";
    private static final String EXTRA_CHORE_NAME = "extra_chore_name";

    public static void schedule(Context context, Chore chore) {
        long deliveryTime = (chore.date.getTime() - System.currentTimeMillis()) / 1000;
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CHORE_NAME, chore.name);
        Task task = new OneoffTask.Builder()
                .setService(ReminderTask.class)
                .setTag(TAG_NOTIFICATION + chore.id)
                .setRequiredNetwork(NETWORK_STATE_ANY)
                .setExecutionWindow(deliveryTime, deliveryTime + 30)
                .setPersisted(true)
                .setExtras(bundle)
                .build();
        GcmNetworkManager.getInstance(context).schedule(task);
    }

    public static void cancel(Context context, Chore chore) {
        GcmNetworkManager.getInstance(context).cancelTask(TAG_NOTIFICATION + chore.id, ReminderTask.class);
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String text = getString(R.string.notification_message, taskParams.getExtras().getString(EXTRA_CHORE_NAME));

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        
        return RESULT_SUCCESS;
    }
}
