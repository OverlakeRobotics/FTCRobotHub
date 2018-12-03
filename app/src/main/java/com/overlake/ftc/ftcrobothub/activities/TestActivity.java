package com.overlake.ftc.ftcrobothub.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.overlake.ftc.ftcrobothub.R;
import com.overlake.ftc.ftcrobothub.app.App;
import com.overlake.ftc.ftcrobothub.app.RobotApp;

public class TestActivity extends AppCompatActivity {
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        app = new RobotApp(this);
        app.start();
    }

}
