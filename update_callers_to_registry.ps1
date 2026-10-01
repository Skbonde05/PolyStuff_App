# Update all callers to use SubjectListActivity.launch(context, "key")
$callerFiles = @(
    "app\src\main\java\myapp\org\userapp\levels1_it.java",
    "app\src\main\java\myapp\org\userapp\levels2_it.java",
    "app\src\main\java\myapp\org\userapp\levels3_it.java",
    "app\src\main\java\myapp\org\userapp\levels4_it.java",
    "app\src\main\java\myapp\org\userapp\levels4b_it.java",
    "app\src\main\java\myapp\org\userapp\levels5_it.java",
    "app\src\main\java\myapp\org\userapp\levels2.java",
    "app\src\main\java\myapp\org\userapp\levels3.java",
    "app\src\main\java\myapp\org\userapp\levels4.java",
    "app\src\main\java\myapp\org\userapp\levels4b.java",
    "app\src\main\java\myapp\org\userapp\levels5.java",
    "app\src\main\java\myapp\org\userapp\Semesters.java"
)

$totalReplacements = 0

foreach ($filePath in $callerFiles) {
    if (-not (Test-Path $filePath)) {
        Write-Host "SKIP: $filePath not found"
        continue
    }
    
    $content = Get-Content $filePath -Raw
    $original = $content
    
    # Replace startActivity(new Intent(xxx.this, Yyy_cs.class))
    # with SubjectListActivity.launch(xxx.this, "Yyy_cs")
    $content = [Regex]::Replace($content, 
        'startActivity\(new Intent\((\w+\.this),\s*(\w+_(?:cs|it))\.class\)\)', 
        'SubjectListActivity.launch($1, "$2")')
    
    # Replace two-line pattern: Intent intent = new Intent(xxx.this, Yyy_cs.class); startActivity(intent);
    $content = [Regex]::Replace($content,
        'Intent intent = new Intent\((\w+\.this),\s*(\w+_(?:cs|it))\.class\);\s*startActivity\(intent\);',
        'SubjectListActivity.launch($1, "$2");')
    
    if ($content -ne $original) {
        Set-Content -Path $filePath -Value $content -NoNewline
        $count = ([regex]::Matches($content, 'SubjectListActivity\.launch')).Count
        $totalReplacements += $count
        Write-Host "Updated: $(Split-Path $filePath -Leaf) ($count launch calls)"
    }
}

Write-Host ""
Write-Host "Total files updated: $($callerFiles.Count)"
Write-Host "Total SubjectListActivity.launch calls: $totalReplacements"
