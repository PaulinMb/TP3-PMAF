@echo off
set /p user_input=Parametres?
@echo on
start ./WinFormapp.exe %user_input%
pause