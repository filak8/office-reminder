package filak.officereminder20.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import filak.officereminder20.R;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtMail;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private static final String mStrErrorLenght = "Password must be at least 6 characters long";
    private static final String mStrErrorEmptyField = "Empty field";
    private SharedPreferences mSpEmailAndLogin;
    private static final String mStrSavedEmail = "saved_email";
    private static final String mStrSavedPassword = "saved_password";
    private String mStrMail;
    private CheckBox mChbLogIn;
    private SharedPreferences.Editor mEditor;
    protected static final String mStrSp = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mEtMail = (EditText) findViewById(R.id.editMail);
        mEtPassword = (EditText) findViewById(R.id.editPassword);
        mBtnLogin = (Button) findViewById(R.id.login);
        mBtnLogin.setOnClickListener(this);
        mChbLogIn = (CheckBox) findViewById(R.id.mChbLogIn);

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);

        if (mSpEmailAndLogin.contains(mStrSavedEmail)) {

            mChbLogIn.setChecked(true);
            loadInfo(mStrSavedEmail, mEtMail);
            loadInfo(mStrSavedPassword, mEtPassword);
        }
    }

    private void loadInfo(String x, EditText y) {

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
        String savedtext = mSpEmailAndLogin.getString(x, "");
        y.setText(savedtext);
    }

    @Override
    public void onClick(View v) {

        if (!validateMail(mEtMail.getText().toString().trim())) {

            mEtMail.setError("Invalid Mail");
            mEtMail.requestFocus();
        } else if (!validatePassword(mEtPassword.getText().toString().trim())) {

            mEtPassword.setError(InvalidPassword(mEtPassword.getText().toString().trim()));
        }

        if (mChbLogIn.isChecked()) {

            saveInfo(mStrSavedEmail, mEtMail);
            saveInfo(mStrSavedPassword, mEtPassword);
        } else {

            mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
            mEditor = mSpEmailAndLogin.edit();
            mEditor.remove(mStrSavedEmail);
            mEditor.remove(mStrSavedPassword);
            mEditor.apply();
        }

        if ((validateMail(mEtMail.getText().toString().trim())) && (validatePassword(mEtPassword.getText().toString().trim()))) {

            Intent intent = new Intent(this, EnterTimeActivity.class);
            startActivity(intent);
        }
    }

    private void saveInfo(String x, EditText y) {

        mSpEmailAndLogin = getSharedPreferences(mStrSp, MODE_PRIVATE);
        mEditor = mSpEmailAndLogin.edit();
        mEditor.putString(x, y.getText().toString());
        mEditor.apply();
    }

    private boolean validatePassword(String editPassword) {

        if (editPassword != null && editPassword.length() > 5) {

            return true;
        } else {

            return false;
        }
    }

    private boolean validateMail(String mail) {

        String mailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@+[A-Za-z0-9-]+" +
                "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(mailPattern);
        Matcher matcher = pattern.matcher(mail);

        return matcher.matches();
    }

    private String InvalidPassword(String editPassword) {

        if (editPassword.length() < 7 && editPassword.length() > 0) {

            return mStrErrorLenght;
        } else {

            return mStrErrorEmptyField;
        }
    }
}
