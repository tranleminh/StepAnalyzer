package com.functionality.stepanalyzer;

import java.text.SimpleDateFormat;

public final class ParametersCollection {

    /**The Channel ID used for Foreground Task Notification Service. Used in DailyStepService.java**/
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    /**SharedPreferences file name, used in both Service and MainActivity**/
    public static final String sharedPrefFile = "StepAnalyzerPreferences";

    /**File manipulation parameters, used in DailyStepService.java**/
    public static final String FILE_NAME = "DailyStep";
    public static final String EXTENSION = ".csv";
    public static final String FIRST_LINE = "Timestamp; Delta\n";
    public static final SimpleDateFormat df2 = new SimpleDateFormat("hh:mm:ss");
    public static final SimpleDateFormat dfYear = new SimpleDateFormat("YYYY");
    public static final SimpleDateFormat dfMonth = new SimpleDateFormat("MMM");
    public static final SimpleDateFormat dfDay = new SimpleDateFormat("d");
    public static final SimpleDateFormat dfHour = new SimpleDateFormat("hh-mm-ss");
    public static final long TIME_LIMIT_MS = 5000;

    /**Max report latency parameter used in registering listener for step sensor, set to 0**/
    public static final int ZERO_REPORT_LATENCY = 0;

    /**Broadcast action name, used in both Service and Main**/
    public static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    /**Titles and Messages displayed in MainActivity's dialog**/
    public static final String TOOLTIPS_TITLE = "Welcome to Step Analyzer !";
    public static final String TOOLTIPS_MESSAGE = "In order to use this app, " +
            "please first fill correctly the ID field with your given ID " +
            "before pressing the Start button.\n\n" +
            "This app works well on Background task, and the Service will be relaunched " +
            "if app is manually turned off, or your device is rebooted.\n\n" +
            "Thanks for your patience ! Click anywhere outside this box to use Step Analyzer.";

    public static final String TITLE_LEGAL = "Term of Use";
    public static final String INFO_LEGAL = "You can read about term of use here";

}
