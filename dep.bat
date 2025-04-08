@echo off
setlocal

REM Nom de l'application et chemins principaux
set APPLICATION=ETU003252
set WORK_DIR=C:\Users\Natiora\Desktop\S4\MrNaina\EXAMEN_ETU003252\information

REM Définition des chemins
set BUILD_DIR=%WORK_DIR%\build
set LIB_DIR=%WORK_DIR%\lib
set XML_FILE=%WORK_DIR%\src\webapp\WEB-INF
set HTML_FILE=%WORK_DIR%\
set SRC_DIR=%WORK_DIR%\src
set DEPLOYMENT_DIR=C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps

REM Suppression du dossier build s'il existe
echo Suppression de l'ancien répertoire build...
if exist "%BUILD_DIR%" (
    rmdir /s /q "%BUILD_DIR%"
    if exist "%BUILD_DIR%" (
        echo Erreur : Impossible de supprimer le dossier build.
        exit /b 1
    )
)

REM Création de la structure du dossier build
echo Création de la structure du répertoire build...
mkdir "%BUILD_DIR%\WEB-INF\classes"
mkdir "%BUILD_DIR%\WEB-INF\lib"

REM Copie des fichiers nécessaires
echo Copie des fichiers depuis les répertoires sources...
xcopy /Q "%WORK_DIR%\*.html" "%BUILD_DIR%\" 
xcopy /Q "%WORK_DIR%\*.jsp" "%BUILD_DIR%\" 
xcopy /Q "%XML_FILE%\*.xml" "%BUILD_DIR%\WEB-INF\"
xcopy /Q "%WORK_DIR%\*.css" "%BUILD_DIR%\" 
xcopy /E /Q "%LIB_DIR%" "%BUILD_DIR%\WEB-INF\lib"

REM Compilation des fichiers Java
echo Compilation des fichiers Java...
pushd "%SRC_DIR%"
dir /s /B *.java > sources.txt
javac -d "%BUILD_DIR%\WEB-INF\classes" -cp "%BUILD_DIR%\WEB-INF\lib\*" @sources.txt
if errorlevel 1 (
    echo Erreur lors de la compilation. Vérifiez votre code.
    del sources.txt
    popd
    exit /b 1
)
del sources.txt
popd

REM Création du fichier .war
echo Création du fichier WAR...
pushd "%BUILD_DIR%"
jar cf "%APPLICATION%.war" -C "%BUILD_DIR%" .
if not exist "%APPLICATION%.war" (
    echo Erreur : Échec de la création du fichier WAR.
    popd
    exit /b 1
)
popd

REM Déploiement vers le répertoire webapps de Tomcat
echo Déploiement de l'application vers Tomcat...
xcopy /Y "%BUILD_DIR%\%APPLICATION%.war" "%DEPLOYMENT_DIR%\"
if errorlevel 1 (
    echo Erreur lors du déploiement. Vérifiez les permissions et le chemin.
    exit /b 1
)

REM Redémarrage de Tomcat (optionnel)
set TOMCAT_DIR="C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\bin"
if exist %TOMCAT_DIR%\shutdown.bat (
    echo Redémarrage de Tomcat...
    call %TOMCAT_DIR%\shutdown.bat
    timeout /t 5 /nobreak > nul
    call %TOMCAT_DIR%\startup.bat
    echo Tomcat a été redémarré.
) else (
    echo Tomcat non trouvé. Vérifiez le chemin.
)

echo Déploiement terminé avec succès. Accédez à votre application via Tomcat.
pause
