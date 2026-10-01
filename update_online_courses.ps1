$filePath = "app\src\main\java\myapp\org\userapp\OnlineCoursesFragment.java"
$content = Get-Content $filePath -Raw

$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), cpp_information\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.cpp_information)'
$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), java_information\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.java_information)'
$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), iot_information\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.iot_information)'
$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), php_information\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.php_information)'
$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), python_information\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.python_information)'
$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), c_information\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.c_information)'
$content = $content -replace 'startActivity\(new Intent\(getActivity\(\), ds_course_info\.class\)\)', 'SimpleActivity.launch(getActivity(), R.layout.ds_course_info)'

Set-Content -Path $filePath -Value $content -NoNewline
Write-Host "Updated OnlineCoursesFragment.java"
