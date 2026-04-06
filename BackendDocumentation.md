# Backend API Endpoints Documentation

# NOTE: ALWAYS CODE IN VANILLA HTML, CSS AND JS

---

## 1. Apartments API
**Base Path:** `/api/apartments`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/{id}` | `Path: id (Long)` | - | `ApartmentDto` |
| **POST** | `/` | - | `UpdateApartmentDto` | `ApartmentDto` (201) |
| **PUT** | `/{id}` | `Path: id (Long)` | `UpdateApartmentDto` | `200 OK` (Void) |
| **DELETE** | `/{id}` | `Path: id (Long)` | - | `204 No Content` |

---

## 2. Configuration API
**Base Path:** `/configuration`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | - | - | `List<ConfigurationDto>` |
| **PUT** | `/update` | - | `UpdateConfigurationDto` | `200 OK` (Void) |

---

## 3. Furniture API
**Base Path:** `/api/furniture`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | - | - | `List<FurnitureDto>` |
| **GET** | `/{id}` | `Path: id (Long)` | - | `FurnitureDto` |
| **POST** | `/` | - | `UpdateFurnitureDto` | `200 OK` (Void) |
| **PUT** | `/{id}` | `Path: id (Long)` | `UpdateFurnitureDto` | `200 OK` (Void) |
| **DELETE** | `/{id}` | `Path: id (Long)` | - | `200 OK` (Void) |

---

## 4. Lease API
**Base Path:** `/api/leases`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/apartment/{flatNumber}` | `Path: flatNumber (String)` | - | `LeaseDto` |
| **POST** | `/` | - | `CreateLeaseDto` | `LeaseDto` (201) |
| **PUT** | `/{leaseId}` | `Path: leaseId (Long)` | `UpdateLeaseDto` | `200 OK` (Void) |

---

## 5. Maintenance Requests API
**Base Path:** `/api/maintenanceRequests`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | - | - | `List<MaintenanceRequestDto>` |
| **GET** | `/{id}` | `Path: id (Long)` | - | `MaintenanceRequestDto` |
| **GET** | `/getAllPendingRequests`| - | - | `List<MaintenanceRequestDto>` |
| **GET** | `/countOpenRequests` | - | - | `{ "count": Integer }` |
| **GET** | `/countCompletedRequests`| - | - | `{ "count": Integer }` |
| **GET** | `/countPendingRequests` | - | - | `{ "count": Integer }` |
| **POST** | `/{tenantId}` | `Path: tenantId (Long)` | `TenantUpdateMaintenanceRequestDto` | `MaintenanceRequestDto` (201) |
| **PUT** | `/{id}` | `Path: id (Long)` | `TenantUpdateMaintenanceRequestDto` | `200 OK` (Void) |
| **PATCH**| `/{id}` | `Path: id (Long)` | `UpdateStatusDto` | `200 OK` (Void) |
| **DELETE**| `/{id}` | `Path: id (Long)` | - | `200 OK` (Void) |

---

## 6. Managers API
**Base Path:** `/api/managers`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | - | - | `List<ManagerDto>` |
| **GET** | `/{id}` | `Path: id (Long)` | - | `ManagerDto` |
| **POST** | `/` | - | `UpdateManagerDto` | `ManagerDto` (201) |
| **PUT** | `/{id}` | `Path: id (Long)` | `UpdateManagerDto` | `200 OK` (Void) |
| **DELETE** | `/{id}` | `Path: id (Long)` | - | `204 No Content` |

---

## 7. Payments API
**Base Path:** `/api/payments`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | - | - | `List<PaymentDto>` |
| **GET** | `/{id}` | `Path: id (Long)` | - | `PaymentDto` |
| **POST** | `/` | - | `UpdatePaymentDto` | `PaymentDto` (201) |
| **PUT** | `/{id}` | `Path: id (Long)` | `UpdatePaymentDto` | `200 OK` (Void) |
| **DELETE** | `/{id}` | `Path: id (Long)` | - | `204 No Content` |

---

## 8. Tenants API
**Base Path:** `/api/tenants`

| Method | Endpoint | Params/Variables | Request Body | Response Type |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/` | `Query: ?name={String}` | - | `List<TenantDto>` |
| **GET** | `/{tenantId}` | `Path: tenantId (Long)` | - | `TenantDto` |
| **POST** | `/` | - | `UpdateTenantDto` | `TenantDto` (201) |
| **PUT** | `/{id}` | `Path: id (Long)` | `UpdateTenantDto` | `200 OK` (Void) |

---

## Data Models (Payloads & Responses)

*(Note: Java `Date` objects are represented as ISO 8601 Strings. Java `BigDecimal` objects are represented as numeric values.)*

Apartment Models
```json
// ApartmentDto
{
  "flatNumber": "string",
  "occupied": true,
  "lastOccupied": "2026-04-05T00:00:00.000Z"
}

// UpdateApartmentDto
{
  "flatNumber": "string"
}

// =====================
// Configuration Models
// =====================

// ConfigurationDto
{
  "id": 1,
  "configKey": "string",
  "configValue": "string"
}

// UpdateConfigurationDto
{
  "configKey": "string",
  "configValue": "string"
}


// =====================
// Furniture Models
// =====================

// FurnitureDto
{
  "id": 1,
  "flatNumber": "string",
  "furnitureType": "string", // Enum representation
  "quantity": 0
}

// UpdateFurnitureDto
{
  "flatNumber": "string",
  "furnitureType": "string", // Enum representation
  "quantity": 0
}


// =====================
// Lease Models
// =====================

// CreateLeaseDto
{
  "start": "2026-04-05T00:00:00.000Z",
  "end": "2027-04-05T00:00:00.000Z",
  "flatNumber": "string",
  "tenantIds": [1, 2, 3], // Array of Longs
  "rentAmount": 0.00,
  "maintenanceAmount": 0.00,
  "depositAmount": 0.00,
  "isDepositCollected": true
}

// UpdateLeaseDto
{
  "start": "2026-04-05T00:00:00.000Z",
  "end": "2027-04-05T00:00:00.000Z",
  "flatNumber": "string",
  "rentAmount": 0.00,
  "maintenanceAmount": 0.00,
  "depositAmount": 0.00,
  "isDepositCollected": true,
  "isDepositReturned": false
}


// ================================
// Maintenance Request Models
// ================================

// MaintenanceRequestDto
{
  "id": 1,
  "flatNumber": "string",
  "managerId": 1,
  "maintenanceType": "string", // Enum representation
  "title": "string",
  "description": "string",
  "status": "string", // Enum representation
  "dateSubmitted": "2026-04-05T00:00:00.000Z"
}

// TenantUpdateMaintenanceRequestDto
{
  "maintenanceType": "string", // Enum representation
  "title": "string",
  "description": "string",
  "dateSubmitted": "2026-04-05T00:00:00.000Z"
}

// UpdateStatusDto
{
  "status": "string" // Enum representation
}


// =====================
// Manager Models
// =====================

// ManagerDto
{
  "id": 1,
  "name": "string",
  "number": 1234567890,
  "maintenanceTypes": ["string1", "string2"] // Array of Enum string values
}

// UpdateManagerDto
{
  "name": "string",
  "number": 1234567890,
  "maintenanceTypes": ["string1", "string2"] // Array of Enum string values
}


// =====================
// Payment Models
// =====================

// PaymentDto
{
  "id": 1,
  "flatNumber": "string",
  "paymentType": "string", // Enum representation
  "paymentAmount": 0.00,
  "paymentMethod": "string", // Enum representation
  "comment": "string",
  "paymentDate": "2026-04-05T00:00:00.000Z"
}

// UpdatePaymentDto
{
  "flatNumber": "string",
  "paymentType": "string", // Enum representation
  "paymentAmount": 0.00,
  "paymentMethod": "string", // Enum representation
  "comment": "string",
  "paymentDate": "2026-04-05T00:00:00.000Z"
}


// =====================
// Tenant Models
// =====================

// TenantDto
{
  "id": 1,
  "name": "string",
  "email": "string",
  "phoneNumber": "string",
  "address": "string",
  "fatherName": "string",
  "uniqueIdentifier": "string",
  "isBackgroundChecked": true,
  "hasActiveLease": true
}

// UpdateTenantDto
{
  "name": "string",
  "email": "string",
  "phoneNumber": "string",
  "address": "string",
  "fatherName": "string",
  "uniqueIdentifier": "string",
  "isBackgroundChecked": true
}



