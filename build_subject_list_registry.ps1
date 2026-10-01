$csFiles = Get-ChildItem -Path "app\src\main\java\myapp\org\userapp" -Filter "*_cs.java"
$itFiles = Get-ChildItem -Path "app\src\main\java\myapp\org\userapp" -Filter "*_it.java" | Where-Object { $_.Name -notmatch "^(levels|subject|python_it)" }
$allFiles = @($csFiles + $itFiles)

$registryEntries = @()
foreach ($file in $allFiles) {
    $content = Get-Content $file.FullName -Raw
    if ($content -match 'SubjectListActivity\.launch\(this,\s*R\.layout\.(\w+),\s*"([^"]+)",\s*"(.*)"\);') {
        $layoutName = $Matches[1]
        $backClass = $Matches[2]
        $cardMapJson = $Matches[3]
        $key = $file.BaseName
        $registryEntries += "        REGISTRY.put(""$key"", new Config(R.layout.$layoutName, ""$backClass"", ""$cardMapJson""));"
    }
}

$registryBlock = $registryEntries -join "`n"

$header = @'
package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SubjectListActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT_KEY = "subject_list_key";

    private static class Config {
        int layoutId;
        String backClass;
        String cardMapJson;
        Config(int layoutId, String backClass, String cardMapJson) {
            this.layoutId = layoutId;
            this.backClass = backClass;
            this.cardMapJson = cardMapJson;
        }
    }

    private static final Map<String, Config> REGISTRY = new HashMap<>();
    static {
'@

$footer = @'
    }

    public static void launch(Context context, String key) {
        Config config = REGISTRY.get(key);
        if (config == null) {
            Toast.makeText(context, "Subject not found: " + key, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, SubjectListActivity.class);
        intent.putExtra("layout_id", config.layoutId);
        intent.putExtra("back_class", config.backClass);
        intent.putExtra("card_map", config.cardMapJson);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getIntent().getIntExtra("layout_id", -1);
        if (layoutId == -1) {
            Toast.makeText(this, "Layout ID missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setContentView(layoutId);

        String backClassName = getIntent().getStringExtra("back_class");

        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null && backClassName != null && !backClassName.isEmpty()) {
            backButton.setOnClickListener(v -> {
                try {
                    Class<?> backClass = Class.forName(backClassName);
                    Intent intent = new Intent(SubjectListActivity.this, backClass);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    finish();
                }
                finish();
            });
        }

        String cardMapJson = getIntent().getStringExtra("card_map");
        if (cardMapJson != null && !cardMapJson.isEmpty()) {
            try {
                JSONObject cardMap = new JSONObject(cardMapJson);
                Iterator<String> keys = cardMap.keys();
                while (keys.hasNext()) {
                    String viewIdName = keys.next();
                    int viewId = getResources().getIdentifier(viewIdName, "id", getPackageName());
                    if (viewId != 0) {
                        CardView cardView = findViewById(viewId);
                        if (cardView != null) {
                            String subjectKey = cardMap.getString(viewIdName);
                            cardView.setOnClickListener(v -> {
                                SubjectContentActivity.launch(SubjectListActivity.this, subjectKey);
                            });
                        }
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Failed to load subjects", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
'@

$newContent = $header + "`n" + $registryBlock + "`n" + $footer
Set-Content -Path "app\src\main\java\myapp\org\userapp\SubjectListActivity.java" -Value $newContent -NoNewline
Write-Host "SubjectListActivity written with $($registryEntries.Count) registry entries"
