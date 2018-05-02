package com.example.jay.quicktools;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private ImageButton torch_btn,compass_btn,todo_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingPermission();

        torch_btn = (ImageButton) findViewById(R.id.goto_torch_btn);
        compass_btn = (ImageButton) findViewById(R.id.goto_compass_btn);
        //todo_btn = (ImageButton) findViewById(R.id.goto_tdo_btn);

        torch_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openActivity("torch_activity");
                    }
                }
        );
        compass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity("compass_activity");
            }
        });
        //todo_btn.setOnClickListener(new View.OnClickListener() {
           // @Override
          //  public void onClick(View view) {
            //    openActivity("todo_activity");
         //  }
       // });
    }

    private void openActivity(String activity_name) {
        if (activity_name == "torch_activity") {
            Intent intent = new Intent(this, torch_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(activity_name == "compass_activity"){
            Intent intent = new Intent(this, compass.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(activity_name == "todo_activity"){
            Intent intent = new Intent(this, todo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void settingPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);

            }
        }
    }
}
