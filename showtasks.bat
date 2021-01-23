call runcrud
if "%ERRORLEVEL%" == "0" goto browser
echo.
echo runcrud.bat has errors - breaking work
goto fail

:browser
set url="http://localhost:8080/crud/v1/task/getTasks"
start "C:\Users\Maciek\AppData\Local\Programs\Opera\launcher.exe" %url%
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Error opening Opera browser
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Task successfully retrieved.