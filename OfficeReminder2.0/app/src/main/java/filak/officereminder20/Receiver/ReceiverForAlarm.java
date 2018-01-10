package filak.officereminder20.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import filak.officereminder20.Activity.EnterTimeActivity;
import filak.officereminder20.R;

import static android.R.attr.id;

public class ReceiverForAlarm extends BroadcastReceiver {

    final String contentTitle = "Up";
    final String contentText = "Down";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager;
        notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder notifBuilder = new Notification.Builder(context)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ww);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, EnterTimeActivity.class), 0);
        notifBuilder.setContentIntent(pendingIntent);
        Notification notification = notifBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_ALL;

        notificationManager.notify(id, notification);
    }
}


