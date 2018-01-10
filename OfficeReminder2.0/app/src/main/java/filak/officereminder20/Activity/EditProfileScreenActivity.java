package filak.officereminder20.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.noelchew.multipickerwrapper.library.MultiPickerWrapper;
import com.noelchew.multipickerwrapper.library.ui.MultiPickerWrapperAppCompatActivity;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import filak.officereminder20.R;

import static filak.officereminder20.Activity.LogInActivity.mStrSp;

public class EditProfileScreenActivity extends MultiPickerWrapperAppCompatActivity {

    private Context context = EditProfileScreenActivity.this;

    private int DIALOG_DATE = 1;
    private int mIntYear = 2000;
    private int mIntMonth = 0;
    private int mIntDay = 1;

    private AlertDialog.Builder mAdBuilder;

    private static final String mStrPermissionDenied = "No permission - no foto";
    private static final String mStrError = "Error: Please, restart app";
    private static final String mStrAlertDialogMessage = "Select foto from:";
    private static final String mStrAlertDialogTitle = "Profile foto";
    private static final String mStrPositiveButton = "Camera";
    private static final String mStrNegativeButton = "Gallery";
    private static final String mStrFirstName = "saved_first_name";
    private static final String mStrLastName = "saved_last_name";
    private static final String mStrEmail = "saved_email";
    private static final String mStrBirthday = "saved_birthday";
    private static final String mStrSex = "saved_sex";
    private static final String mStrDecription = "saved_decription";
    private static final String mStrUriProfileImage = "saved_uri_profile_image";
    private static final String SEX_MALE = "Male";
    private static final String SEX_FEMALE = "Female";
    public static final String LOG_TAG = "myLogs";

    private SharedPreferences mSpEmailAndLogin;
    private SharedPreferences.Editor mEditor;

    private EditText mEtFirsName;
    private EditText mEtLastName;
    private EditText mEtEmail;
    private EditText mEtDescription;
    private TextView mTvDatePicker;
    private RadioButton mRbSexMale;
    private RadioButton mRbSexFemale;
    private ImageView mIvProfileImage;
    private Button mBtnSaveProfileScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen_edit);

        mIvProfileImage = (ImageView) findViewById(R.id.profile_image);
        mTvDatePicker = (TextView) findViewById(R.id.mTvDatePicker);
        mBtnSaveProfileScreen = (Button) findViewById(R.id.mBtnSaveProfileScreen);
        mEtFirsName = (EditText) findViewById(R.id.mEtFirstName);
        mEtLastName = (EditText) findViewById(R.id.mEtLastName);
        mEtEmail = (EditText) findViewById(R.id.mEtEmail);
        mEtDescription = (EditText) findViewById(R.id.mEtDecription);
        mRbSexMale = (RadioButton) findViewById(R.id.mRbSexMale);
        mRbSexFemale = (RadioButton) findViewById(R.id.mRbSexFemale);

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);

        loadInfoEditText(mStrFirstName, mEtFirsName);
        loadInfoEditText(mStrLastName, mEtLastName);
        loadInfoEditText(mStrEmail, mEtEmail);
        loadInfoTextView(mStrBirthday, mTvDatePicker);
        loadInfoEditText(mStrDecription, mEtDescription);
        loadImage(mStrUriProfileImage, mIvProfileImage);
        setRadioButton(mStrSex);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            return new DatePickerDialog(this, myCallBack, mIntYear, mIntMonth, mIntDay);
        }
        return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mIntYear = year;
            mIntMonth = monthOfYear + 1;
            mIntDay = dayOfMonth;
            mTvDatePicker.setText(mIntMonth + "/" + mIntDay + "/" + mIntYear);
        }
    };

    @Override
    protected MultiPickerWrapper.PickerUtilListener getMultiPickerWrapperListener() {
        return multiPickerWrapperListener;
    }

    MultiPickerWrapper.PickerUtilListener multiPickerWrapperListener = new MultiPickerWrapper.PickerUtilListener() {
        @Override
        public void onPermissionDenied() {
            Toast.makeText(context, mStrPermissionDenied, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onImagesChosen(List<ChosenImage> list) {
            String filePath = list.get(0).getOriginalPath();
            final Uri uri = Uri.fromFile(new File(filePath));

            Picasso.with(context).load(uri).into(mIvProfileImage);
            String uritostring = uri.toString();
            saveInfoString(mStrUriProfileImage, uritostring);
        }

        @Override
        public void onVideosChosen(List<ChosenVideo> list) {
            String filePath = list.get(0).getOriginalPath();
            String thumbnailPath = list.get(0).getPreviewThumbnail();
            Uri uri = Uri.fromFile(new File(filePath));
        }

        @Override
        public void onError(String s) {
            Toast.makeText(context, mStrError, Toast.LENGTH_SHORT).show();
        }
    };

    private void pickCroppedImage() {

        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        options.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setCropFrameColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        options.setCropGridColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setToolbarTitle("MultiPickerWrapper - Crop");
        options.setCircleDimmedLayer(true);

        multiPickerWrapper.getPermissionAndPickSingleImageAndCrop(options, 1, 1);
    }

    private void takeCroppedImage() {

        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        options.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setCropFrameColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        options.setCropGridColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setToolbarTitle("MultiPickerWrapper - Crop");
        options.setCircleDimmedLayer(true);

        multiPickerWrapper.getPermissionAndTakePictureAndCrop(options, 1, 1);
    }

    private void createProfileImageDialog() {

        mAdBuilder = new AlertDialog.Builder(this);
        mAdBuilder.setTitle(mStrAlertDialogTitle);
        mAdBuilder.setMessage(mStrAlertDialogMessage);
        mAdBuilder.setPositiveButton(mStrPositiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                takeCroppedImage();
            }
        });
        mAdBuilder.setNegativeButton(mStrNegativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                pickCroppedImage();
            }
        });
    }

    public void onclick(View view) {
        switch (view.getId()) {

            case R.id.mBtnDatePicker:
                showDialog(DIALOG_DATE);
                break;

            case R.id.profile_image:
                createProfileImageDialog();
                mAdBuilder.show();
                break;

            case R.id.mBtnSaveProfileScreen:
                saveInfoEditText(mStrFirstName, mEtFirsName);
                saveInfoEditText(mStrLastName, mEtLastName);
                saveInfoEditText(mStrDecription, mEtDescription);
                saveInfoTextView(mStrBirthday, mTvDatePicker);

                if (mEtEmail.getText().length() == 0)
                    saveInfoEditText(mStrEmail, mEtEmail);


                if (mRbSexMale.isChecked()) {

                    saveInfoString(mStrSex, SEX_MALE);
                } else if (mRbSexFemale.isChecked()) {

                    saveInfoString(mStrSex, SEX_FEMALE);
                }

                Intent intent = new Intent(context, ProfileScreenActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void saveInfoEditText(String x, EditText y) {

        if (y.getText().toString().length() != 0) {

            mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
            mEditor = mSpEmailAndLogin.edit();
            mEditor.putString(x, y.getText().toString());
            mEditor.apply();
        }
    }

    private void saveInfoTextView(String x, TextView y) {

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
        mEditor = mSpEmailAndLogin.edit();
        mEditor.putString(x, y.getText().toString());
        mEditor.apply();
    }

    private void saveInfoString(String x, String y) {

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
        mEditor = mSpEmailAndLogin.edit();
        mEditor.putString(x, y);
        mEditor.apply();
    }

    private void loadInfoEditText(String x, EditText y) {

        if (mSpEmailAndLogin.contains(x)) {
            mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
            String savedtext = mSpEmailAndLogin.getString(x, "");
            y.setText(savedtext);
        }
    }

    private void loadInfoTextView(String x, TextView y) {

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

    private void setRadioButton(String x) {

        if (mSpEmailAndLogin.contains(x)) {
            mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
            String savedinfo = mSpEmailAndLogin.getString(x, "");

            Log.d(LOG_TAG, savedinfo);

            if (savedinfo == SEX_MALE) {

                mRbSexMale.setChecked(true);
            } else {

                mRbSexFemale.setChecked(true);
            }
        }
    }
}
