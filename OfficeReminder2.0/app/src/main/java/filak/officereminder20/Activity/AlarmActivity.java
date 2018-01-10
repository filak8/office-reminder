package filak.officereminder20.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import filak.officereminder20.Receiver.ReceiverForAlarm;

public class AlarmActivity extends AppCompatActivity {

    private Context context;
    private PendingIntent mAlarmSender;
    final String LOG_TAG = "myLogs";

    public AlarmActivity(Context context, int iter) {

        this.context = context;
        mAlarmSender = PendingIntent.getBroadcast(context, iter,
                new Intent(context, ReceiverForAlarm.class), 0);
    }

    public void startAlarm(int finalHours, int finalMinute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, finalHours);
        calendar.set(Calendar.MINUTE, finalMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {

            calendar.add(Calendar.HOUR_OF_DAY, 24);
        }

        long firstTime = calendar.getTimeInMillis();
        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
    }

    public void cancelAlarm(int iter) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mAlarmSender = PendingIntent.getBroadcast(context, iter,
                new Intent(context, ReceiverForAlarm.class), 0);
        am.cancel(mAlarmSender);
        mAlarmSender.cancel();
    }
}
