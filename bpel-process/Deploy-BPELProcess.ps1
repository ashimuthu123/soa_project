# Deploy-BPELProcess.ps1

# Configuration
$TOMCAT_HOME = "C:\apache-tomcat-9.0.108"
$ODE_HOME = "C:\apache-ode-war-1.3.8"
$ODE_VERSION = "1.3.8"

# Set environment variables
$env:CATALINA_HOME = $TOMCAT_HOME
$env:CATALINA_BASE = $TOMCAT_HOME
$env:JAVA_HOME = [System.Environment]::GetEnvironmentVariable("JAVA_HOME", "Machine")
$env:ANT_HOME = "C:\apache-ant-1.10.15"
$env:Path = "$env:ANT_HOME\bin;$env:Path"

# Add required Jakarta EE dependencies
$JAKARTA_LIB = "$TOMCAT_HOME\lib"

# Function to download a file
function Download-File {
    param(
        [string]$url,
        [string]$output
    )
    Write-Host "Downloading $url to $output"
    $webClient = New-Object System.Net.WebClient
    $webClient.DownloadFile($url, $output)
}

# Check if Tomcat is running and stop it if it is
$tomcatProcess = Get-Process -Name "tomcat*" -ErrorAction SilentlyContinue
if ($tomcatProcess) {
    Write-Host "Stopping Tomcat..."
    & "$TOMCAT_HOME\bin\shutdown.bat"
    Start-Sleep -Seconds 10
}

# Stop Tomcat if running
Write-Host "Checking if Tomcat is running..."
$tomcatProcess = Get-Process -Name "tomcat*" -ErrorAction SilentlyContinue
if ($tomcatProcess) {
    Write-Host "Stopping Tomcat..."
    & "$TOMCAT_HOME\bin\shutdown.bat"
    Start-Sleep -Seconds 10
}

# Clean up old deployment
Write-Host "Cleaning up old deployment..."
Remove-Item "$TOMCAT_HOME\webapps\ode" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$TOMCAT_HOME\webapps\ode.war" -Force -ErrorAction SilentlyContinue

# Deploy ODE to Tomcat
Write-Host "Deploying ODE to Tomcat..."
Copy-Item "$ODE_HOME\ode.war" "$TOMCAT_HOME\webapps\" -Force

# Start Tomcat
Write-Host "Starting Tomcat..."
& "$TOMCAT_HOME\bin\startup.bat"

# Wait for ODE webapp to be deployed
Write-Host "Waiting for ODE to be deployed..."
$maxAttempts = 30
$attempts = 0
while (-not (Test-Path "$TOMCAT_HOME\webapps\ode\WEB-INF\processes") -and $attempts -lt $maxAttempts) {
    Start-Sleep -Seconds 5
    $attempts++
    Write-Host "Waiting for ODE deployment... Attempt $attempts of $maxAttempts"
}

if (-not (Test-Path "$TOMCAT_HOME\webapps\ode\WEB-INF\processes")) {
    Write-Host "Error: ODE failed to deploy properly"
    exit 1
}

# Build BPEL process
Write-Host "Building BPEL process..."
Set-Location -Path "D:\GitHub\soa_project_test\bpel-process"
& ant package

# Verify the build output
if (Test-Path "dist\PlaceOrder.zip") {
    Write-Host "BPEL process built successfully"
    
    # Create processes directory if it doesn't exist
    New-Item -ItemType Directory -Force -Path "$TOMCAT_HOME\webapps\ode\WEB-INF\processes"
    
    # Deploy BPEL process
    Write-Host "Deploying BPEL process..."
    Copy-Item "dist\PlaceOrder.zip" "$TOMCAT_HOME\webapps\ode\WEB-INF\processes\" -Force
    Write-Host "BPEL process deployed successfully"
    
    Write-Host "`nDeployment completed successfully!"
    Write-Host "You can access the ODE web console at: http://localhost:8080/ode"
    Write-Host "The BPEL process should be listed under: http://localhost:8080/ode/processes"
} else {
    Write-Host "Error: BPEL process build failed - PlaceOrder.zip not found"
    exit 1
}
# Build BPEL process
Write-Host "Building BPEL process..."
Set-Location -Path "D:\GitHub\soa_project_test\bpel-process"
& ant package

# Deploy BPEL process
Write-Host "Deploying BPEL process..."
if (Test-Path "dist\PlaceOrder.zip") {
    Copy-Item "dist\PlaceOrder.zip" "$TOMCAT_HOME\webapps\ode\WEB-INF\processes\" -Force
    Write-Host "BPEL process deployed successfully"
} else {
    Write-Host "Error: BPEL process build failed - PlaceOrder.zip not found"
    exit 1
}

Write-Host "Deployment complete. Please wait a few moments for Tomcat to start up."
Write-Host "You can check the BPEL process status at: http://localhost:8080/ode/processes"
