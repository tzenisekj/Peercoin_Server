@echo off

if EXIST %cd%\lib (
    RMDIR /S /Q .\lib\
    echo "cleaning %cd%\lib"
)

py -2 --version || (
    echo "python 2 does not exist on this machine. Please reinstall"
    GOTO END
)

MD .\lib\

py -2 -m pip install --target=%cd%\lib -r requirements.txt

:END