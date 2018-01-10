package filak.officereminder20.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import filak.officereminder20.R;

public class EnterTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "myLogs";
    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private int DIALOG_TIME;
    private int mHourStart = -1;
    private int mMinuteStart;
    private int mHourEnd = -1;
    private int mMinuteEnd;
    private Button mBtnStartApp;
    private int range;
    private int rangeFinal;
    private static final String mStrRemindersInstall = "Reminders are installed";
    private static final String mStrRemindersCancel = "Reminers canceled";
    private Button mBtnStartTime;
    private Button mBtnEndTime;
    protected static final String mStrSp = "shared_preferences";
    private Context context = EnterTimeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_time);

        mTvStartTime = (TextView) findViewById(R.id.mTvStartTime);
        mTvEndTime = (TextView) findViewById(R.id.mTvEndTime);
        mBtnStartApp = (Button) findViewById(R.id.mBtnStartApp);
        mBtnStartTime = (Button) findViewById(R.id.mBtnStartTime);
        mBtnEndTime = (Button) findViewById(R.id.mBtnEndTime);
    }

    public void onclick(View view) {

        if (view == mBtnStartTime) {

            DIALOG_TIME = 1;
        } else {

            DIALOG_TIME = 2;
        }
        showDialog(DIALOG_TIME);
    }

    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {

            if (DIALOG_TIME == 1) {

                return new TimePickerDialog(this, callBackStart, mHourStart, mMinuteStart, true);
            } else {

                return new TimePickerDialog(this, callBackEnd, mHourEnd, mMinuteEnd, true);
            }
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener callBackStart = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            mHourStart = hourOfDay;
            mMinuteStart = minute;
            Resources resources = getResources();
            String text = resources.getString(R.string.start_time_is, mHourStart, mMinuteStart);
            mTvStartTime.setText(text);
        }
    };

    TimePickerDialog.OnTimeSetListener callBackEnd = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            mHourEnd = hourOfDay;
            mMinuteEnd = minute;
            Resources resources = getResources();
            String text = resources.getString(R.string.end_time_is, mHourEnd, mMinuteEnd);
            mTvEndTime.setText(text);
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mBtnStartApp:

                if (mHourStart >= mHourEnd) {

                    range = (24 - mHourStart) + mHourEnd;
                } else {

                    range = mHourEnd - mHourStart;
                }

                getRangeFinal(mMinuteStart, mMinuteEnd);

                for (int i = 1; i < rangeFinal / 2 + 1; i++) {

                    AlarmActivity alarmService = new AlarmActivity(this, i);

                    if (mHourStart != -1 && mHourEnd != -1) {

                        alarmService.startAlarm(mHourStart + (i * 2), mMinuteStart);
                    }
                }

                if (mHourStart != -1 && mHourEnd != -1)

                    Toast.makeText(this, mStrRemindersInstall, Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_disable_reminders:

                for (int i = 1; i < rangeFinal / 2 + 1; i++) {

                    AlarmActivity alarmService = new AlarmActivity(this, i);
                    alarmService.cancelAlarm(i);
                }
                Toast.makeText(this, mStrRemindersCancel, Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_settings:

                Intent intent = new Intent(EnterTimeActivity.this, ProfileScreenActivity.class);
                startActivity(intent);
                break;

            case R.id.action_log_out:

                SharedPreferences mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSpEmailAndLogin.edit();
                mEditor.clear();
                mEditor.apply();

                Intent intent1 = new Intent(context, LogInActivity.class);
                startActivity(intent1);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getRangeFinal(int mMinuteStart, int mMinuteEnd) {

        if (mMinuteStart > mMinuteEnd) {

            if (range % 2 == 0) {

                rangeFinal = range - 2;
            } else {

                rangeFinal = range;
            }
        } else {

            rangeFinal = range;
        }

    }
}


