$csFiles = Get-ChildItem -Path "app\src\main\java\myapp\org\userapp" -Filter "*_cs.java"
$itFiles = Get-ChildItem -Path "app\src\main\java\myapp\org\userapp" -Filter "*_it.java" | Where-Object { $_.Name -notmatch "^(levels|subject)" }
$allFiles = @($csFiles + $itFiles)

$totalUpdated = 0

foreach ($file in $allFiles) {
    $content = Get-Content $file.FullName -Raw
    $original = $content
    
    # Extract layout ID
    if ($content -match 'setContentView\(R\.layout\.(\w+)\)') {
        $layoutName = $Matches[1]
        $layoutId = "R.layout.$layoutName"
    } else {
        Write-Host "SKIP $($file.Name): No layout found"
        continue
    }
    
    # Extract back class
    $backClass = ""
    if ($content -match '(?s)backButton\.setOnClickListener.*?new Intent\(\w+\.this,\s*(\w+)\.class\)') {
        $backClass = "myapp.org.userapp." + $Matches[1]
    } else {
        $backClass = ""
    }
    
    # Extract CardView mappings
    $cardMap = @{}
    $blockPattern = '(?s)(\w+)\s*=\s*findViewById\(R\.id\.(\w+)\);.*?setOnClickListener.*?\{.*?public void onClick.*?\{(.*?)\}\s*\}'
    $blockMatches = [regex]::Matches($content, $blockPattern)
    
    foreach ($match in $blockMatches) {
        $varName = $match.Groups[1].Value
        $viewId = $match.Groups[2].Value
        $onClickBody = $match.Groups[3].Value
        
        if ($viewId -eq "backButton") {
            continue
        }
        
        $subjectKey = ""
        
        if ($onClickBody -match 'SubjectContentActivity\.launch\(\w+\.this,\s*"([^"]+)"\)') {
            $subjectKey = $Matches[1]
        }
        elseif ($onClickBody -match 'intent\.putExtra\(SubjectContentActivity\.EXTRA_SUBJECT_KEY,\s*"([^"]+)"\)') {
            $subjectKey = $Matches[1]
        }
        elseif ($onClickBody -match 'new Intent\(\w+\.this,\s*(\w+_main)\.class\)') {
            $subjectKey = $Matches[1]
        }
        
        if ($subjectKey -ne "") {
            $cardMap[$viewId] = $subjectKey
        }
    }
    
    if ($cardMap.Count -eq 0) {
        Write-Host "SKIP $($file.Name): No CardView mappings found"
        continue
    }
    
    # Build JSON string with escaped quotes for Java
    $cardMapJson = "{"
    $first = $true
    foreach ($key in $cardMap.Keys) {
        if (-not $first) { $cardMapJson += "," }
        $cardMapJson += '\"' + $key + '\":\"' + $cardMap[$key] + '\"'
        $first = $false
    }
    $cardMapJson += "}"
    
    # Build new file content
    $className = $file.BaseName
    $newContent = @"
package myapp.org.userapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class $className extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubjectListActivity.launch(this, $layoutId, "$backClass", "$cardMapJson");
    }
}
"@
    
    Set-Content -Path $file.FullName -Value $newContent -NoNewline
    $totalUpdated++
    Write-Host "Updated: $($file.Name) ($($cardMap.Count) cards, back=$backClass)"
}

Write-Host ""
Write-Host "Total updated: $totalUpdated"
