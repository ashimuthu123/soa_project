# Deploy-CatalogService.ps1

# Configuration
$TOMCAT_HOME = "C:\Program Files\Apache Software Foundation\Tomcat 10.1"

# Stop Tomcat if it's running
$tomcatProcess = Get-Process -Name "tomcat*" -ErrorAction SilentlyContinue
if ($tomcatProcess) {
    Write-Host "Stopping Tomcat..."
    & "$TOMCAT_HOME\bin\shutdown.bat"
    Start-Sleep -Seconds 10
}

# Build the project
Write-Host "Building catalog-service..."
Set-Location -Path "D:\GitHub\soa_project_test\catalog-service"
& mvn clean package

# Remove old deployment
if (Test-Path "$TOMCAT_HOME\webapps\catalog-service") {
    Remove-Item "$TOMCAT_HOME\webapps\catalog-service" -Recurse -Force
}
if (Test-Path "$TOMCAT_HOME\webapps\catalog-service.war") {
    Remove-Item "$TOMCAT_HOME\webapps\catalog-service.war" -Force
}

# Deploy new WAR
Write-Host "Deploying catalog-service..."
Copy-Item "target\catalog-service.war" "$TOMCAT_HOME\webapps\"

# Start Tomcat
Write-Host "Starting Tomcat..."
& "$TOMCAT_HOME\bin\startup.bat"

Write-Host "Deployment complete. Please wait a few moments for Tomcat to start up."
Write-Host "You can check the WSDL at: http://localhost:8080/catalog-service/CatalogService?wsdl"
