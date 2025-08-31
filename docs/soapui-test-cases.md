# SOAP UI Test Cases for CatalogService

## Overview
This document provides comprehensive test cases for testing the GlobalBooks CatalogService using SOAP UI. The service implements WS-Security with UsernameToken authentication and provides two main operations: `getBook` and `searchBooks`.

## Test Setup

### 1. Import WSDL
1. Open SOAP UI
2. Create new SOAP Project
3. Import WSDL from: `http://localhost:8080/catalog-service/CatalogService?wsdl`

### 2. Configure Security
- **Security Type**: UsernameToken
- **Username**: `admin`
- **Password**: `admin123`
- **Password Type**: PasswordText

## Test Cases

### Test Case 1: GetBook Operation - Valid ISBN

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:GetBookRequest>
         <cat:isbn>978-0-123456-47-2</cat:isbn>
      </cat:GetBookRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Title**: "The Great Gatsby"
- **Book Author**: "F. Scott Fitzgerald"
- **Book Price**: 12.99
- **Stock Level**: 50
- **Book ID**: "BOOK-001"

### Test Case 2: GetBook Operation - Invalid ISBN

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:GetBookRequest>
         <cat:isbn>INVALID-ISBN</cat:isbn>
      </cat:GetBookRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Title**: "Book Not Found"
- **Book ID**: "NOT-FOUND"
- **Stock Level**: 0

### Test Case 3: SearchBooks Operation - By Title

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:SearchBooksRequest>
         <cat:title>Gatsby</cat:title>
      </cat:SearchBooksRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Count**: 1
- **Book Title**: Contains "Gatsby"
- **Book Author**: "F. Scott Fitzgerald"

### Test Case 4: SearchBooks Operation - By Author

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:SearchBooksRequest>
         <cat:author>Harper Lee</cat:author>
      </cat:SearchBooksRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Count**: 1
- **Book Title**: "To Kill a Mockingbird"
- **Book Author**: "Harper Lee"

### Test Case 5: SearchBooks Operation - By Category

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:SearchBooksRequest>
         <cat:category>Fiction</cat:category>
      </cat:SearchBooksRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Count**: 2
- **All Books**: Category = "Fiction"

### Test Case 6: SearchBooks Operation - By Max Price

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:SearchBooksRequest>
         <cat:maxPrice>13.00</cat:maxPrice>
      </cat:SearchBooksRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Count**: 2
- **All Books**: Price <= 13.00

### Test Case 7: SearchBooks Operation - Combined Criteria

#### Request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://catalog.globalbooks.com/">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">admin123</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <cat:SearchBooksRequest>
         <cat:category>Fiction</cat:category>
         <cat:maxPrice>15.00</cat:maxPrice>
      </cat:SearchBooksRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

#### Assertions
- **SOAP Response**: Contains valid SOAP envelope
- **Response Time**: < 200ms
- **HTTP Status**: 200 OK
- **Book Count**: 2
- **All Books**: Category = "Fiction" AND Price <= 15.00

## Security Test Cases

### Test Case 8: Authentication - Valid Credentials

#### Request
Use any of the above requests with valid credentials (admin/admin123)

#### Assertions
- **HTTP Status**: 200 OK
- **Response**: Contains expected data
- **Security**: UsernameToken properly validated

### Test Case 9: Authentication - Invalid Credentials

#### Request
Use any of the above requests with invalid credentials (admin/wrongpassword)

#### Assertions
- **HTTP Status**: 401 Unauthorized
- **Response**: Security fault or authentication error
- **Security**: UsernameToken properly rejected

### Test Case 10: Authentication - Missing Credentials

#### Request
Use any of the above requests without security header

#### Assertions
- **HTTP Status**: 401 Unauthorized
- **Response**: Security fault or authentication error
- **Security**: Missing UsernameToken properly detected

## Performance Test Cases

### Test Case 11: Load Testing - Multiple Concurrent Requests

#### Setup
- **Number of Threads**: 10
- **Ramp-up Period**: 30 seconds
- **Test Duration**: 5 minutes

#### Assertions
- **Response Time**: 95th percentile < 200ms
- **Throughput**: > 100 requests/second
- **Error Rate**: < 1%
- **No Memory Leaks**: Stable memory usage

### Test Case 12: Stress Testing - High Load

#### Setup
- **Number of Threads**: 50
- **Ramp-up Period**: 60 seconds
- **Test Duration**: 10 minutes

#### Assertions
- **Response Time**: 95th percentile < 500ms
- **Throughput**: > 200 requests/second
- **Error Rate**: < 5%
- **Service Stability**: No crashes or hangs

## Data Validation Test Cases

### Test Case 13: Input Validation - Empty ISBN

#### Request
```xml
<cat:GetBookRequest>
   <cat:isbn></cat:isbn>
</cat:GetBookRequest>
```

#### Assertions
- **Response**: Appropriate error handling
- **Validation**: Empty input properly validated

### Test Case 14: Input Validation - Special Characters in Search

#### Request
```xml
<cat:SearchBooksRequest>
   <cat:title>Gatsby & Co.</cat:title>
</cat:SearchBooksRequest>
```

#### Assertions
- **Response**: Proper handling of special characters
- **Security**: No injection vulnerabilities

## Test Execution

### 1. Manual Testing
1. Open each test case in SOAP UI
2. Configure security settings
3. Execute request
4. Verify assertions manually

### 2. Automated Testing
1. Create test suite in SOAP UI
2. Configure test runner
3. Execute all test cases
4. Review test results and reports

### 3. Continuous Integration
1. Export test suite as XML
2. Integrate with CI/CD pipeline
3. Execute tests on every build
4. Fail build on test failures

## Test Data

### Sample Books in Database
- **BOOK-001**: "The Great Gatsby" by F. Scott Fitzgerald (Fiction, $12.99)
- **BOOK-002**: "To Kill a Mockingbird" by Harper Lee (Fiction, $14.99)
- **BOOK-003**: "1984" by George Orwell (Science Fiction, $11.99)

### Test Users
- **admin/admin123**: Full access to all operations
- **user/user123**: Standard user access
- **service/service123**: Service-to-service access

## Troubleshooting

### Common Issues
1. **Connection Refused**: Ensure CatalogService is running on port 8080
2. **Authentication Failed**: Verify username/password and security configuration
3. **WSDL Not Found**: Check service deployment and URL configuration
4. **Slow Response**: Monitor system resources and database performance

### Debug Steps
1. Check service logs for errors
2. Verify network connectivity
3. Test with simple requests first
4. Use SOAP UI's built-in debugging tools

This comprehensive test suite ensures the CatalogService meets all functional, security, and performance requirements.
