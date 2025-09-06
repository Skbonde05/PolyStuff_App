package myapp.org.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splash_screen1 extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2500;

    Animation bottomAnim;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen1);

        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.imageView);
        image.setAnimation(bottomAnim);

        // Inside onCreate method of splash_screen1.java
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is logged in (you can use SharedPreferences, database, etc.)
                // For simplicity, I'm assuming the user is logged in by default
                startActivity(new Intent(splash_screen1.this, Dashboard.class));
                finish();
            }
        }, SPLASH_SCREEN);

    }

}