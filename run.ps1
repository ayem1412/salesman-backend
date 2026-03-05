# 1. Force the correct JAVA_HOME for this session
$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.2"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

Write-Host "--- Launching SalesMaster Microservices ---" -ForegroundColor Cyan

function Start-ServiceWindow {
    param ([string]$name, [string]$path)
    Write-Host "Starting $name..." -ForegroundColor Yellow
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd $path; `$env:JAVA_HOME='C:\Program Files\Java\jdk-25.0.2'; `$env:Path='C:\Program Files\Java\jdk-25.0.2\bin;'+`$env:Path; ..\mvnw.cmd spring-boot:run"
}

Start-ServiceWindow "Discovery Server" "./discovery-server"

Write-Host "Waiting 15 seconds for Discovery Server to warm up..." -ForegroundColor Gray
Start-Sleep -Seconds 15

Start-ServiceWindow "API Gateway" "./api-gateway"
Start-ServiceWindow "Identity Service" "./identity-service"

Start-ServiceWindow "Inventory Service" "./inventory-service"
Start-ServiceWindow "Customer Service" "./customer-service"
Start-ServiceWindow "Order Service" "./order-service"

Write-Host "--- All windows launched. Check logs for registration! ---" -ForegroundColor Green
