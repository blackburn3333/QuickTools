package com.example.jay.quicktools;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class torch_activity extends AppCompatActivity {


    private ImageButton on_off_btn;
    private ImageButton back_btn;
    private Camera camera;
    private Camera.Parameters can_para;
    boolean flash_status = false;
    boolean isOn = false;
    private ConstraintLayout torch_main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torch_activity);
        on_off_btn = (ImageButton) findViewById(R.id.flash_light);
        back_btn = (ImageButton) findViewById(R.id.back_button);
        torch_main_layout = (ConstraintLayout) findViewById(R.id.torch_main_layout);
        hasCamera();

        on_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flash_status) {
                    if (!isOn) {
                        turnOnFlash();
                    } else {
                        turnOffFlash();
                    }
                } else {
                        if (!isOn) {
                            noFlashTurnOnScreen();
                            isOn = true;
                        } else {
                            noFlashTurnOffScreen();
                        }
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(torch_activity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    protected void hasCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            camera = Camera.open();
            can_para = camera.getParameters();
            flash_status = true;
        }
    }

    protected void turnOnFlash() {
        on_off_btn.setImageResource(R.drawable.switch_on);
        can_para.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(can_para);
        camera.startPreview();
        isOn = true;
    }

    protected void turnOffFlash() {
        on_off_btn.setImageResource(R.drawable.switch_off);
        can_para.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(can_para);
        camera.stopPreview();
        isOn = false;
    }

    protected void noFlashTurnOnScreen() {
        Toast.makeText(getApplicationContext(), "You have no torch in your device. So we open screen torch for you.", Toast.LENGTH_LONG).show();
        torch_main_layout.setBackgroundColor(ContextCompat.getColor(torch_activity.this, R.color.WhiteColor));
        on_off_btn.setImageResource(R.drawable.switch_on_no_flash);
        back_btn.setImageResource(R.drawable.back_btn_gray);
        ScreenBrightness(1.0f,255);
    }

    protected void noFlashTurnOffScreen() {
        torch_main_layout.setBackgroundColor(ContextCompat.getColor(torch_activity.this, R.color.colorBackGroundDark));
        on_off_btn.setImageResource(R.drawable.switch_off);
        back_btn.setImageResource(R.drawable.back_btn_white);
        ScreenBrightness(0.0f,0);
        isOn = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
            on_off_btn.setImageResource(R.drawable.switch_off);
            back_btn.setImageResource(R.drawable.back_btn_white);
            flash_status = false;
            isOn = false;
            noFlashTurnOffScreen();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (camera == null) {
            hasCamera();
        }
    }

    private void ScreenBrightness(float level,int screenBriLevel) {
        Settings.System.putInt(this.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, screenBriLevel);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = level;
        getWindow().setAttributes(lp);
    }

}
