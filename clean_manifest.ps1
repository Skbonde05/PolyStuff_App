$manifestPath = "app\src\main\AndroidManifest.xml"
$content = Get-Content $manifestPath -Raw
$original = $content

# Remove all individual *_main activity registrations
# Pattern matches lines like: <activity android:name=".xxx_main" android:exported="false"/>
$content = $content -replace '\s*<activity android:name="\.[a-z0-9_]+_main" android:exported="false"\s*/>', ''

# Clean up any double blank lines that might result
$content = $content -replace '\n{3,}', "`n`n"

if ($content -ne $original) {
    Set-Content -Path $manifestPath -Value $content -NoNewline
    $removed = ([regex]::Matches($original, '<activity android:name="\.[a-z0-9_]+_main"')).Count
    Write-Host "Removed $removed individual activity registrations from AndroidManifest.xml"
} else {
    Write-Host "No changes needed in AndroidManifest.xml"
}
