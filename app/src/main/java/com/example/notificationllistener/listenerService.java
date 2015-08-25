package com.example.notificationllistener;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by fbernal on 8/17/2015.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class listenerService extends NotificationListenerService {

    // Method to detect when service is created (when the user enable service).
    @Override
    public void onCreate(){
        showNotification(true);

        // Method to detect when this device is connect to server using Socket.io
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            mSocket.emit("high","Android device connected...");
            }
        });

        mSocket.connect();
    }

    //Method to detect when service is destroy (when the user disable service).
    @Override
    public void onDestroy(){
        showNotification(false);
    }

    //Method to detect when notification is posted...
    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        String app = sbn.getPackageName();
        mSocket.emit("high",app); // Send info of the notification posted to server.
    }

    // Method to detect when notification is removed...
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        String app = sbn.getPackageName();
        mSocket.emit("low",app);  // Send info of the notification removed to server.
    }

    // Show notification on device.
    private void showNotification(boolean show){
        Resources r = getResources();

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,MainActivity.class), 0);

        // Set the parameters for notification showing.
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.notification_description))
                .setSmallIcon(R.drawable.beagle_logo)
                .setContentTitle(r.getString(R.string.notif_tittle_text))
                .setContentText(r.getString((R.string.notif_text)))
                .setContentIntent(pi)
                .setAutoCancel(false).build();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        // Show or delete notification when the service is Posted or Removed.
        if (show)
            notificationManager.notify(0, notification);
        else
            notificationManager.cancel(0);
    }

    // Initialize setting of socket.io and the server to connect.
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://...herokuapp.com/");
        } catch (URISyntaxException e) {}
    }
}
