@echo off

set original_dir=%cd%

if EXIST %cd%\plugins\ (
    cd %cd%\plugins\
    CALL installDependencies.bat
    cd %original_dir%
) ELSE (
    echo ERROR: No plugin directory
)

if EXIST %cd%\config\ (
    RMDIR /S /Q .\config\
)

if EXIST %cd%\wallet\ (
    RMDIR /S /Q .\wallet\
)

MD %cd%\config\
MD %cd%\config\web
MD %cd%\wallet\

cd .\scripts\
CALL runServices.bat
cd %original_dir%

for /F "tokens=3 delims=: " %%H in ('sc query "MongoDB" ^| findstr "        STATE"') do (
    if /I "%%H" NEQ "RUNNING" (
        echo Starting MongoDB
        net start "MongoDB"
    ) ELSE (
        echo MongoDB service already active
    )
)
