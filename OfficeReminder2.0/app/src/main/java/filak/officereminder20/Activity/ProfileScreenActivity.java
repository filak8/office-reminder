package filak.officereminder20.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import filak.officereminder20.R;

public class ProfileScreenActivity extends AppCompatActivity {

    private SharedPreferences mSpEmailAndLogin;

    private Context context = ProfileScreenActivity.this;

    private static final String mStrFirstName = "saved_first_name";
    private static final String mStrLastName = "saved_last_name";
    private static final String mStrEmail = "saved_email";
    private static final String mStrBirthday = "saved_birthday";
    private static final String mStrSex = "saved_sex";
    private static final String mStrDecription = "saved_decription";
    private static final String mStrSavedEmail = "saved_email";
    protected static final String mStrSp = "shared_preferences";
    private static final String mStrUriProfileImage = "saved_uri_profile_image";

    private TextView mTvFirstName;
    private TextView mTvLastName;
    private TextView mTvBirthday;
    private TextView mTvSex;
    private TextView mTvDescription;
    private TextView mTvEmail;
    private ImageView mIvProfileImage;
    private Button mBtnEditProfileScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        mTvEmail = (TextView) findViewById(R.id.mTvEmail);
        mBtnEditProfileScreen = (Button) findViewById(R.id.mBtnEditProfileScreen);
        mTvFirstName = (TextView) findViewById(R.id.mTvFirstName);
        mTvLastName = (TextView) findViewById(R.id.mTvLastName);
        mTvBirthday = (TextView) findViewById(R.id.mTvBirthday);
        mTvSex = (TextView) findViewById(R.id.mTvSex);
        mTvDescription = (TextView) findViewById(R.id.mTvDescription);
        mIvProfileImage = (ImageView) findViewById(R.id.mIvProfileImage);

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);

        loadInfo(mStrSavedEmail, mTvEmail);
        loadInfo(mStrFirstName, mTvFirstName);
        loadInfo(mStrLastName, mTvLastName);
        loadInfo(mStrBirthday, mTvBirthday);
        loadInfo(mStrSex, mTvSex);
        loadInfo(mStrDecription, mTvDescription);
        loadImage(mStrUriProfileImage, mIvProfileImage);
    }

    private void loadInfo(String x, TextView y) {

        if (mSpEmailAndLogin.contains(x)) {

            mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
            String savedtext = mSpEmailAndLogin.getString(x, "");
            y.setText(savedtext);
        }
    }

    private void loadImage(String x, ImageView y) {

        if (mSpEmailAndLogin.contains(x)) {

            mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
            String saveduri = mSpEmailAndLogin.getString(x, "");
            Uri uri;
            uri = Uri.parse(saveduri);
            y.setImageURI(uri);
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mBtnEditProfileScreen:

                Intent intent = new Intent(context, EditProfileScreenActivity.class);
                startActivity(intent);
                break;

            case R.id.mBtnGoToEnterTimeActivity:

                Intent intent1 = new Intent(context, EnterTimeActivity.class);
                startActivity(intent1);

            default:
                break;
        }
    }
}
