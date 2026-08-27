@echo off
setlocal

echo [1/3] Compilando o projeto...
call mvn clean install -DskipTests

if errorlevel 1 (
    echo.
    echo ERRO: O Maven falhou.
    pause
    exit /b 1
)

echo [2/3] Preparando a pasta run...
if not exist "run" mkdir "run"

echo [3/3] Movendo o JAR...
move /Y "target\vet-0.0.1-SNAPSHOT-shaded.jar" "run\vet-0.0.1-SNAPSHOT-shaded.jar"

if errorlevel 1 (
    echo.
    echo ERRO: Nao foi possivel mover o JAR.
    pause
    exit /b 1
)

echo.
echo ===============================================================================
echo Iniciando aplicacao...
java -jar "run\vet-0.0.1-SNAPSHOT-shaded.jar"

pause