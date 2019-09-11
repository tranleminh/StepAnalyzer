package com.functionality.stepanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    /****************Attributes and global variables declared here*****************/

    /***Application's main UI features***/
    private EditText ID;
    private TextView Steps;
    private Button BtnDaily;
    private Button BtnInfo;

    /***Button manipulation variables***/
    private boolean dailyTracker = false;
    private String id = "NOT_INITIALIZED";
    private String btnText = "Start Daily Tracker";
    private int steps = 0;

    /***A Shared Preferences to store information on user ID and button status to be restore on every launch***/
    private SharedPreferences mPreferences;

    /***Broadcast Receiver which receive step numbers counted by DailyStepTracker.java***/
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            steps = intent.getIntExtra("Nb_Steps", steps);
            Steps.setText("Number of steps :" + steps);
        }
    };

    /**Intent declared for service launch**/
    private Intent intent;

    /**************************************************************************************************/

    /**
     * A private method that checks if the Service is already launched
     * @param serviceClass
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    /**
     * A method that display a dialog containing tooltips
     */
    private void displayTooltips() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(ParametersCollection.TOOLTIPS_TITLE);
        builder.setMessage(ParametersCollection.TOOLTIPS_MESSAGE);
        builder.show();
    }

    /**
     * A method that displays a dialog containing term of use
     */
    private void displayTermOfUse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(ParametersCollection.TITLE_LEGAL);
        builder.setMessage(ParametersCollection.INFO_LEGAL);
        builder.show();
    }


    /**
     * Main method revoked on application launch
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Instantiate intent to launch service**/
        intent = new Intent(this, DailyStepService.class);

        /***The Broadcast Receiver is launched here***/
        registerReceiver(broadcastReceiver, new IntentFilter(ParametersCollection.ACTION_CUSTOM_BROADCAST));

        /***Shared Preferences instantiated, user id and button status are updated here***/
        mPreferences = getSharedPreferences(ParametersCollection.sharedPrefFile, MODE_PRIVATE);
        id = mPreferences.getString("ID", id);
        dailyTracker = mPreferences.getBoolean("DailyTracker", dailyTracker);
        btnText = mPreferences.getString("BtnText", btnText);

        /***UI instantiation***/
        ID = findViewById(R.id.id);
        Steps = findViewById(R.id.nbstep);
        BtnDaily = findViewById(R.id.btn_daily);
        BtnInfo = findViewById(R.id.btn_info);
        Steps.setText("Number of steps :" + steps);

        if (!id.equals("NOT_INITIALIZED"))
            ID.setText(id);

        /***Display tooltips before everything else***/
        displayTooltips();

        /***A text changed listener with afterTextChanged() method implemented in order to automatically save ID right after the ID field is modified***/
        ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                id = ID.getText().toString();
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.putString("ID", id);
                preferencesEditor.apply();
            }
        });

        /***Daily Tracker Button implemented here***/
        BtnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMyServiceRunning(DailyStepService.class)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    dailyTracker = true;
                    btnText = "Daily Tracker Started";
                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                    preferencesEditor.putString("BtnText", btnText);
                    preferencesEditor.putBoolean("DailyTracker", dailyTracker);
                    preferencesEditor.apply();
                    BtnDaily.setText(btnText);
                }
            }
        });

        /***Term of Use button implemented here***/
        BtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTermOfUse();
            }
        });
    }

    /**
     * Overridden method which implements the stopService() method
     */
    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }
}

