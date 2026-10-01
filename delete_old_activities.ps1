# Delete individual PDF unit activity Java files
$javaFiles = Get-ChildItem -Path "app\src\main\java\myapp\org\userapp" -Filter "*_main.java" | Where-Object { $_.Name -ne "MainActivity.java" }
$deletedJava = 0
foreach ($file in $javaFiles) {
    Remove-Item -LiteralPath $file.FullName -Force
    $deletedJava++
}

# Delete corresponding layout XML files (exclude activity_main.xml)
$xmlFiles = Get-ChildItem -Path "app\src\main\res\layout" -Filter "*_main.xml" | Where-Object { $_.Name -ne "activity_main.xml" }
$deletedXml = 0
foreach ($file in $xmlFiles) {
    Remove-Item -LiteralPath $file.FullName -Force
    $deletedXml++
}

Write-Host "Deleted $deletedJava Java files"
Write-Host "Deleted $deletedXml XML layout files"
