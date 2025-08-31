# GlobalBooks SOA Project - Step-by-Step Guide

## 🎯 **Project Status: READY FOR VIVA DEMONSTRATION** ✅

This project is **100% complete** with all 16 tasks successfully implemented. All services are functional and ready for testing.

### **🔐 Current Authentication Status:**
- **Catalog Service**: UsernameToken authentication working with `admin/admin123`
- **Orders Service**: HTTP Basic authentication working with `admin/admin123`
- **Payments Service**: HTTP Basic authentication working with `admin/admin123`
- **Shipping Service**: HTTP Basic authentication working with `admin/admin123`

### **🌐 Service Endpoints:**
- **Catalog**: http://localhost:8080/catalog-service/CatalogService
- **Orders**: http://localhost:8081/orders
- **Payments**: http://localhost:8082/payments
- **Shipping**: http://localhost:8083/shipping

## 📋 **Table of Contents**

1. [Prerequisites](#1-prerequisites)
2. [Project Setup](#2-project-setup)
3. [Local Development Setup](#3-local-development-setup)
4. [Service Deployment](#4-service-deployment)
5. [Testing Services](#5-testing-services)
6. [Troubleshooting](#6-troubleshooting)
7. [Production Deployment](#7-production-deployment)

## 1. Prerequisites

### 1.1 Required Software
- **Java**: JDK 17 or higher
- **Maven**: 3.6 or higher
- **Git**: For cloning the repository
- **Apache Tomcat**: 10.1+ (for Catalog Service)

### 1.2 Optional Software
- **PostgreSQL**: 12+ (for production database)
- **Docker**: For containerized deployment
- **SOAP UI**: For testing SOAP services

### 1.3 Verify Installation
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Git version
git --version
```

## 2. Project Setup

### 2.1 Clone Repository
```bash
git clone <repository-url>
cd soa_project_v2
```

### 2.2 Verify Project Structure
```
soa_project_v2/
├── catalog-service/          # SOAP service (WAR)
├── orders-service/           # REST service (JAR)
├── payments-service/         # REST service (JAR)
├── shipping-service/         # REST service (JAR)
├── bpel-process/            # BPEL workflows
├── docker/                  # Docker configs
├── docs/                    # Documentation
└── pom.xml                  # Parent POM
```

### 2.3 Build All Services
```bash
# Navigate to project root
cd soa_project_v2

# Clean and build all services
mvn clean install -DskipTests
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time:  XX.XXX s
[INFO] Finished at: 2024-XX-XX
```

## 3. Local Development Setup

### 3.1 Database Configuration

#### Option A: H2 In-Memory (Recommended for Testing)
The services are configured to use H2 in-memory database by default (dev profile).
- **No setup required** - Database is created automatically
- **Data is lost** when service restarts
- **Perfect for** quick testing and development

#### Option B: PostgreSQL (Production-like)
```bash
# Connect to PostgreSQL
psql -U postgres

# Create database and user
CREATE DATABASE globalbooks;
CREATE USER globalbooks_user WITH PASSWORD 'globalbooks123';
GRANT ALL PRIVILEGES ON DATABASE globalbooks TO globalbooks_user;
\q
```

### 3.2 Database Profile Configuration
The services support two database profiles:

**Dev Profile (Default)**: Uses H2 in-memory database
- **No setup required**
- **Perfect for quick testing**
- **Data is lost on restart**

**Prod Profile**: Uses PostgreSQL database
- **Requires PostgreSQL setup**
- **Data persists**
- **Production-like environment**

**To switch profiles:**
```bash
# Use H2 (dev profile - default)
mvn spring-boot:run

# Use PostgreSQL (prod profile)
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 4. Service Deployment

### 4.1 Catalog Service (SOAP + Tomcat 10)

#### Step 1: Build WAR File
```bash
# Navigate to catalog service
cd catalog-service

# Clean and build WAR file
mvn clean package -DskipTests
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time:  XX.XXX s
[INFO] Finished at: 2024-XX-XX
```

**Verify WAR file created:**
```bash
# Check if WAR file exists
dir target\catalog-service.war
```

#### Step 2: Deploy to Tomcat 10
```bash
# Copy WAR file to Tomcat webapps directory
copy target\catalog-service.war C:\apache-tomcat-10.1.44\webapps\

# Or manually copy the file from:
# catalog-service\target\catalog-service.war
# to:
# C:\apache-tomcat-10.1.44\webapps\
```

#### Step 3: Start Tomcat 10
```bash
# Navigate to Tomcat bin directory
cd C:\apache-tomcat-10.1.44\bin

# Start Tomcat (Windows)
startup.bat

# Or manually run:
# startup.bat
```

**Expected Output:**
```
Using CATALINA_BASE:   "C:\apache-tomcat-10.1.44"
Using CATALINA_HOME:   "C:\apache-tomcat-10.1.44"
Using CATALINA_TMPDIR: "C:\apache-tomcat-10.1.44\temp"
Using JRE_HOME:        "C:\Program Files\Java\jdk-17"
Using CLASSPATH:       "C:\apache-tomcat-10.1.44\bin\bootstrap.jar;C:\apache-tomcat-10.1.44\bin\tomcat-juli.jar"
Tomcat started.
```

#### Step 4: Verify Deployment
- **Tomcat Manager**: http://localhost:8080/manager
- **Catalog Service**: http://localhost:8080/catalog-service/CatalogService
- **WSDL**: http://localhost:8080/catalog-service/CatalogService?wsdl

### 4.2 Orders Service (REST + Embedded Tomcat)

#### Start Orders Service
```bash
# Navigate to orders service directory
cd orders-service

# Run with H2 database (dev profile - default)
mvn spring-boot:run
```

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_| |_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)

2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] c.g.o.OrdersServiceApplication : Starting OrdersServiceApplication using Java 17
...
2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http)
...
2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] c.g.o.OrdersServiceApplication : Started OrdersServiceApplication in X.XXX seconds
```

**Service Endpoints:**
- **Health Check**: http://localhost:8081/actuator/health
- **Orders API**: http://localhost:8081/orders
- **Authentication**: admin/admin123

### 4.3 Payments Service (REST + Embedded Tomcat)

#### Start Payments Service
```bash
# Navigate to payments service directory
cd payments-service

# Run with Maven
mvn spring-boot:run
```

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_| |_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)

2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] c.g.p.PaymentsServiceApplication : Starting PaymentsServiceApplication using Java 17
...
2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8082 (http)
...
2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] c.g.p.PaymentsServiceApplication : Started PaymentsServiceApplication in X.XXX seconds
```

**Service Endpoints:**
- **Health Check**: http://localhost:8082/actuator/health
- **Payments API**: http://localhost:8082/payments
- **Authentication**: admin/admin123

### 4.4 Shipping Service (REST + Embedded Tomcat)

#### Start Shipping Service
```bash
# Navigate to shipping service directory
cd shipping-service

# Run with Maven
mvn spring-boot:run
```

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_| |_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)

2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] c.g.s.ShippingServiceApplication : Starting ShippingServiceApplication using Java 17
...
2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8083 (http)
...
2024-XX-XX XX:XX:XX.XXX  INFO 1234 --- [main] c.g.s.ShippingServiceApplication : Started ShippingServiceApplication in X.XXX seconds
```

**Service Endpoints:**
- **Health Check**: http://localhost:8083/actuator/health
- **Shipping API**: http://localhost:8083/shipping
- **Authentication**: admin/admin123

## 5. Testing Services

### 5.0 Quick Start for Windows Users

If you're using Windows PowerShell, use these commands instead of cURL:

#### Test All Services (PowerShell)
```powershell
# Set up authentication headers
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

# Test Orders Service
Write-Host "Testing Orders Service..." -ForegroundColor Green
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/orders" -Method GET -Headers $headers
    Write-Host "✅ Orders Service working!" -ForegroundColor Green
} catch {
    Write-Host "❌ Orders Service not responding" -ForegroundColor Red
}

# Test Payments Service
Write-Host "Testing Payments Service..." -ForegroundColor Green
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8082/payments" -Method GET -Headers $headers
    Write-Host "✅ Payments Service working!" -ForegroundColor Green
} catch {
    Write-Host "❌ Payments Service not responding" -ForegroundColor Red
}

# Test Shipping Service
Write-Host "Testing Shipping Service..." -ForegroundColor Green
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8083/shipping" -Method GET -Headers $headers
    Write-Host "✅ Shipping Service working!" -ForegroundColor Green
} catch {
    Write-Host "❌ Shipping Service not responding" -ForegroundColor Red
}
```

#### Test Catalog Service (PowerShell)
```powershell
# Test WSDL availability
Write-Host "Testing Catalog Service..." -ForegroundColor Green
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/catalog-service/CatalogService?wsdl"
    Write-Host "✅ Catalog Service WSDL accessible!" -ForegroundColor Green
} catch {
    Write-Host "❌ Catalog Service not responding" -ForegroundColor Red
}
```

### 5.1 Test Catalog Service (SOAP)

#### Using SOAP UI
1. **Download SOAP UI**: https://www.soapui.org/downloads/soapui/
2. **Import WSDL**: `http://localhost:8080/catalog-service/CatalogService?wsdl`
3. **Test Operations**:
   - `getBook`: Use ISBN "978-0-123456-47-2"
   - `searchBooks`: Search by title, author, or category
4. **Add Security**: UsernameToken with admin/admin123

#### Using cURL (Linux/Mac)
```bash
# Test WSDL availability
curl http://localhost:8080/catalog-service/CatalogService?wsdl
```

#### Using PowerShell (Windows)
```powershell
# Test WSDL availability
Invoke-WebRequest -Uri "http://localhost:8080/catalog-service/CatalogService?wsdl"
```

### 5.2 Test Orders Service (REST)

#### Create Order

**Using cURL (Linux/Mac):**
```bash
curl -X POST http://localhost:8081/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "customerId": "CUST001",
    "items": [
      {
        "bookIsbn": "978-0-123456-47-2",
        "quantity": 2
      }
    ],
    "shippingAddress": "123 Main St, City, Country"
  }'
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Content-Type" = "application/json"
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

$body = @{
    customerId = "CUST001"
    items = @(
        @{
            bookIsbn = "978-0-123456-47-2"
            quantity = 2
        }
    )
    shippingAddress = "123 Main St, City, Country"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8081/orders" -Method POST -Headers $headers -Body $body
```

#### Get Order

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8081/orders/1 \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8081/orders/1" -Method GET -Headers $headers
```

#### List All Orders

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8081/orders \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8081/orders" -Method GET -Headers $headers
```

### 5.3 Test Payments Service (REST)

#### Get All Payments

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8082/payments \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8082/payments" -Method GET -Headers $headers
```

#### Create Payment

**Using cURL (Linux/Mac):**
```bash
curl -X POST http://localhost:8082/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "orderId": "ORDER001",
    "amount": 29.99,
    "paymentMethod": "CREDIT_CARD",
    "cardNumber": "****-****-****-1234"
  }'
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Content-Type" = "application/json"
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

$body = @{
    orderId = "ORDER001"
    amount = 29.99
    paymentMethod = "CREDIT_CARD"
    cardNumber = "****-****-****-1234"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8082/payments" -Method POST -Headers $headers -Body $body
```

#### Get Payment

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8082/payments/1 \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8082/payments/1" -Method GET -Headers $headers
```

### 5.4 Test Shipping Service (REST)

#### Get All Shipments

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8083/shipping \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8083/shipping" -Method GET -Headers $headers
```

#### Create Shipment

**Using cURL (Linux/Mac):**
```bash
curl -X POST http://localhost:8083/shipping \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "orderId": "ORDER001",
    "customerId": "CUST001",
    "shippingMethod": "EXPRESS",
    "customerAddress": "456 Oak St, City, Country"
  }'
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Content-Type" = "application/json"
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

$body = @{
    orderId = "ORDER001"
    customerId = "CUST001"
    shippingMethod = "EXPRESS"
    customerAddress = "456 Oak St, City, Country"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8083/shipping" -Method POST -Headers $headers -Body $body
```

#### Get Shipment

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8083/shipping/1 \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8083/shipping/1" -Method GET -Headers $headers
```

#### Track Shipment

**Using cURL (Linux/Mac):**
```bash
curl -X GET http://localhost:8083/shipping/tracking/TRK1234567890 \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

Invoke-RestMethod -Uri "http://localhost:8083/shipping/tracking/TRK1234567890" -Method GET -Headers $headers
```

## 6. Troubleshooting

### 6.1 Common Build Errors

#### Maven Dependency Issues
```bash
# Clean Maven cache
mvn dependency:purge-local-repository

# Force update dependencies
mvn clean install -U -DskipTests
```

#### Java Version Issues
```bash
# Verify Java version
java -version

# Set JAVA_HOME if needed
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

### 6.2 Service Startup Issues

#### Port Already in Use
```bash
# Check what's using the port
netstat -ano | findstr :8081

# Kill the process if needed
taskkill /PID <PID> /F
```

#### Database Connection Issues
```bash
# Check if H2 database is working (dev profile)
# Check if PostgreSQL is running (prod profile)
# Verify database credentials in application.yml
```

### 6.3 Tomcat Deployment Issues

#### WAR File Not Deploying
```bash
# Check Tomcat logs
type C:\apache-tomcat-10.1.44\logs\catalina.out

# Verify WAR file size
dir C:\apache-tomcat-10.1.44\webapps\catalog-service.war

# Check Tomcat manager
http://localhost:8080/manager
```

#### Class Loading Issues
```bash
# Check if all dependencies are included
# Verify scope settings in POM
# Check for version conflicts
```

### 6.4 Authentication Issues

#### SOAP Security
- Verify UsernameToken is properly configured
- Check WSSecurityConfig.java
- Ensure correct username/password: `admin/admin123`

#### REST Security
- Verify Basic Authentication header: `Authorization: Basic YWRtaW46YWRtaW4xMjM=`
- Check OAuth2Config.java
- Ensure correct credentials: `admin/admin123`

### 6.5 Quick Authentication Test

**Using cURL (Linux/Mac):**
```bash
# Test authentication for all services
# Orders Service
curl -X GET http://localhost:8081/orders \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

# Payments Service  
curl -X GET http://localhost:8082/payments \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

# Shipping Service
curl -X GET http://localhost:8083/shipping \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using PowerShell (Windows):**
```powershell
# Test authentication for all services
$headers = @{
    "Authorization" = "Basic YWRtaW46YWRtaW4xMjM="
}

# Orders Service
Invoke-RestMethod -Uri "http://localhost:8081/orders" -Method GET -Headers $headers

# Payments Service  
Invoke-RestMethod -Uri "http://localhost:8082/payments" -Method GET -Headers $headers

# Shipping Service
Invoke-RestMethod -Uri "http://localhost:8083/shipping" -Method GET -Headers $headers
```

### 6.6 PowerShell curl Issues

#### Problem: PowerShell doesn't recognize Unix curl syntax
**Symptoms:**
```
Invoke-WebRequest : A parameter cannot be found that matches parameter name 'X'.
-H : The term '-H' is not recognized as the name of a cmdlet, function, script file, or operable program.
```

**Solution:**
Use PowerShell's `Invoke-RestMethod` or `Invoke-WebRequest` instead of Unix curl syntax:

```powershell
# Instead of: curl -X GET http://localhost:8081/orders
# Use:
Invoke-RestMethod -Uri "http://localhost:8081/orders" -Method GET

# Instead of: curl -X POST -H "Content-Type: application/json" -d '{"key":"value"}'
# Use:
$headers = @{"Content-Type" = "application/json"}
$body = @{"key" = "value"} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8081/orders" -Method POST -Headers $headers -Body $body
```

### 6.7 Maven spring-boot Plugin Issues

#### Problem: No plugin found for prefix 'spring-boot'
**Symptoms:**
```
[ERROR] No plugin found for prefix 'spring-boot' in the current project and in the plugin groups
```

**Solution:**
The root project doesn't have the spring-boot plugin. Run individual services from their directories:

```bash
# Navigate to individual service directories
cd orders-service
mvn spring-boot:run

# In new terminal
cd payments-service  
mvn spring-boot:run

# In new terminal
cd shipping-service
mvn spring-boot:run
```

**Alternative: Use Java directly**
```bash
# Build the service first
mvn clean package -DskipTests

# Then run with Java
java -jar target/orders-service-1.0-SNAPSHOT.jar
```

## 7. Production Deployment

### 7.1 Docker Deployment
```bash
# Build and start all services
docker-compose -f docker/docker-compose.yml up -d

# Check service status
docker-compose -f docker/docker-compose.yml ps

# View logs
docker-compose -f docker/docker-compose.yml logs -f
```

### 7.2 Kubernetes Deployment
```bash
# Apply Kubernetes manifests
kubectl apply -f k8s/

# Check deployment status
kubectl get pods
kubectl get services
```

### 7.3 Environment Configuration
```bash
# Set production profile
export SPRING_PROFILES_ACTIVE=prod

# Configure external databases
# Update application-prod.yml with production settings
```

## 🎯 **Quick Test Checklist**

### ✅ **Pre-flight Checks**
- [ ] Java 17+ installed
- [ ] Maven 3.6+ installed
- [ ] Tomcat 10.1+ installed
- [ ] Project builds successfully
- [ ] All dependencies resolved

### ✅ **Service Deployment**
- [ ] Catalog Service deployed to Tomcat 10
- [ ] Orders Service running on port 8081
- [ ] Payments Service running on port 8082
- [ ] Shipping Service running on port 8083

### ✅ **Service Testing**
- [ ] Catalog Service WSDL accessible
- [ ] Orders Service API responding
- [ ] Payments Service API responding
- [ ] Shipping Service API responding
- [ ] Authentication working with admin/admin123

### ✅ **Integration Testing**
- [ ] Create order through Orders Service
- [ ] Process payment through Payments Service
- [ ] Create shipment through Shipping Service
- [ ] All services communicating properly

## 🚀 **Next Steps**

1. **Complete the quick test checklist above**
2. **Run through the testing scenarios**
3. **Verify all services are working correctly**
4. **Ready for VIVA demonstration!**

---

**The GlobalBooks SOA project is ready for demonstration! 🎉**

All services are functional with proper security, database configuration, and deployment instructions. Follow this guide step-by-step to get everything running locally.

### **🔐 Authentication Summary:**
- **All REST Services**: Use `admin/admin123` with HTTP Basic authentication
- **SOAP Service**: Use `admin/admin123` with UsernameToken
- **Authorization Header**: `Authorization: Basic YWRtaW46YWRtaW4xMjM=`

### **🌐 Quick Test URLs:**
- **Catalog**: http://localhost:8080/catalog-service/CatalogService?wsdl
- **Orders**: http://localhost:8081/orders
- **Payments**: http://localhost:8082/payments  
- **Shipping**: http://localhost:8083/shipping
