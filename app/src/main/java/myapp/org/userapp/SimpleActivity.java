package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SimpleActivity extends AppCompatActivity {

    public static void launch(Context context, int layoutResId) {
        Intent intent = new Intent(context, SimpleActivity.class);
        intent.putExtra("layout_res_id", layoutResId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = getIntent().getIntExtra("layout_res_id", -1);
        if (layoutResId != -1) {
            setContentView(layoutResId);
        } else {
            finish();
        }
    }
}
