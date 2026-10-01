$files = Get-ChildItem -Path "app\src\main\java\myapp\org\userapp" -Filter "*.java" | Where-Object { $_.Name -match "(_it|MainActivity)\.java$" }
$totalReplacements = 0

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $original = $content
    
    # Only match PDF unit/question/tutorial/ref/cmd/mcq activities
    # Pattern: xxx_uN_main, xxx_que_main, xxx_tut_main, xxx_ref_main, xxx_cmd_main, xxx_mcq_main, lab_manuals_main, mp_main
    $targetPattern = '[a-z0-9_]+_(?:u[0-9]+|que|tut|ref|cmd|mcq)_main|lab_manuals_main|mp_main'
    
    # Pattern 1: Intent intent = new Intent(xxx.this, yyy_main.class); startActivity(intent);
    $pattern1 = "Intent intent = new Intent\(([^)]+\.this), ($targetPattern)\.class\);\s*startActivity\(intent\);"
    $replacement1 = 'SubjectContentActivity.launch($1, "$2");'
    $content = [Regex]::Replace($content, $pattern1, $replacement1)
    
    # Pattern 2: startActivity(new Intent(xxx.this, yyy_main.class));
    $pattern2 = "startActivity\(new Intent\(([^)]+\.this), ($targetPattern)\.class\)\);"
    $replacement2 = 'SubjectContentActivity.launch($1, "$2");'
    $content = [Regex]::Replace($content, $pattern2, $replacement2)
    
    if ($content -ne $original) {
        Set-Content -Path $file.FullName -Value $content -NoNewline
        $replacements = ([regex]::Matches($content, 'SubjectContentActivity\.launch')).Count
        $totalReplacements += $replacements
        Write-Host "Updated: $($file.Name) ($replacements replacements)"
    }
}

Write-Host ""
Write-Host "Total files updated: $($files.Count)"
Write-Host "Total replacements: $totalReplacements"
