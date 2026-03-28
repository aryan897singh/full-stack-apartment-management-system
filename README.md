# Property Management Portal (MVP v1.0)

A secure, full-stack enterprise web application engineered specifically for **Shri Shyam Kaleshwar Residency**. Designed to digitize and streamline their property management operations, this system provides a centralized dashboard for the property owner to track financial records, manage tenant lifecycles, and oversee maintenance requests.

The application is currently in active production testing (v1.0), serving as a live MVP to validate core business workflows and data architecture in a real-world environment.

**Note:** As of `v1.0`, the system is heavily secured and restricted strictly to the `OWNER` role. Tenant-facing portals are planned for future iterations.

## Tech Stack & Architecture
* **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate
* **Frontend:** HTML5, CSS3, JavaScript, Thymeleaf (Server-side rendering)
* **Database:** Managed Aiven Cloud MySQL (Strict SSL)
* **Identity & Access Management:** Microsoft Entra ID (OAuth2 / OIDC)
* **Hosting:** Azure App Service (Linux, Java SE)

--------------------------------------------

## Architecture Diagram & Security Flow

```mermaid
graph TD
%% External Entities
    User([Property Owner / Frontend])
    Entra[Microsoft Entra ID<br/>Auth Server]
    DB[(Aiven Cloud MySQL)]

    subgraph Azure[Azure App Service]
        direction TB
        subgraph SpringBoot[Spring Boot Application v1.0]
            direction TB

        %% Servlet Filter Level
            DFP(DelegatingFilterProxy)

            subgraph SecurityLayer[Spring Security Layer]
                direction TB
                SFC{SecurityFilterChain<br/>Defense in Depth}
                Converter[EntraIdRoleConverter<br/>JWT to Spring Role Mapping]
                AccessDenied[Custom Unauthorized Page<br/>Global Error Controller]
            end

            Dispatcher(DispatcherServlet)

        %% Application Level
            subgraph NTier[N-Tier Layered Architecture]
                direction TB
                IoC((Spring IoC Container<br/>Constructor Injection))
                Controllers[Controller Layer<br/>Domain-Specific APIs]
                Services[Service Layer<br/>Business Logic]
                Repos[Repository Layer<br/>Spring Data JPA]

            %% IoC Wiring Note
                IoC -.->|Injects Dependencies| Controllers
                IoC -.->|Injects Dependencies| Services
                IoC -.->|Injects Dependencies| Repos

                Controllers -->|Calls| Services
                Services -->|Calls| Repos
            end
        end
    end

%% Step-by-Step Flow
    User -->|1. HTTP Request| DFP
    DFP -->|2. Intercepts before Servlet| SFC
    SFC <-->|3. OAuth2 / OIDC Flow| Entra
    SFC -->|4. Passes JWT| Converter
    Converter -->|5. Returns Merged Authorities| SFC

%% Authorization Branching
    SFC -->|6a. If Unauthorized API/Web| AccessDenied
    SFC -->|6b. If Authorized| Dispatcher

%% Core Logic Flow
    Dispatcher -->|7. Route to endpoint| Controllers
    Controllers -->|8. GET / POST / PUT / DELETE| Services
    Services -->|9. Data Access| Repos
    Repos <-->|10. Hibernate / JDBC| DB

%% Response
    Controllers -.->|11. Returns Java Record DTOs| User

%% Styling with high contrast text colors (black text on light backgrounds)
    classDef external fill:#f9f9f9,color:#000000,stroke:#333,stroke-width:2px;
    classDef security fill:#fce4ec,color:#000000,stroke:#c2185b,stroke-width:2px;
    classDef core fill:#e3f2fd,color:#000000,stroke:#1565c0,stroke-width:2px;
    classDef filter fill:#fff3e0,color:#000000,stroke:#ef6c00,stroke-width:2px;
    classDef azure fill:#f0f8ff,color:#000000,stroke:#0078d4,stroke-width:2px,stroke-dasharray: 5 5;

    class User,Entra,DB external;
    class SFC,Converter,AccessDenied security;
    class Controllers,Services,Repos core;
    class DFP,Dispatcher filter;
    class Azure azure;
```
---------------------------------

```mermaid
erDiagram
%% Core Relationships based on Java Entity Mappings
    apartment_tbl ||--o{ tenants_tbl : "houses (@ManyToOne)"
    apartment_tbl ||--o{ payments_tbl : "receives (@ManyToOne LAZY)"
    apartment_tbl ||--o{ deposits_tbl : "secures (@ManyToOne EAGER)"
    apartment_tbl ||--o{ maintenance_requests_tbl : "generates (@ManyToOne EAGER)"
    apartment_tbl ||--o{ furniture : "contains (@ManyToOne LAZY)"

    managers_tbl ||--o{ maintenance_requests_tbl : "assigned to (@ManyToOne EAGER)"
    managers_tbl ||--o{ manager_maintenance_types : "specializes in (@ElementCollection)"

%% Table Definitions (Mapped directly from Entity Columns)
    apartment_tbl {
        Long id PK
        String flat_number
        BigDecimal expected_rent
        BigDecimal rent_amount
        BigDecimal maintenance_amount
        BigDecimal paid_maintenance
        BigDecimal paid_rent
        Boolean occupied
        Date last_occupied
        Boolean depositCollected
    }

    tenants_tbl {
        Long id PK
        Long apartment_id FK
        String name
        Boolean main_owner
        String email
        String phone_number
        String aadhar_card_number "UNIQUE"
        boolean exists_flag "Soft Delete Pattern"
    }

    payments_tbl {
        Long id PK
        Long apartment_id FK
        BigDecimal rentAmount
        BigDecimal maintenanceAmount
        BigDecimal electricityAmount
        String payment_method "Enum"
        Date paymentDate
    }

    deposits_tbl {
        Long id PK
        Long apartment_id FK
        BigDecimal expected
        BigDecimal negotiated
        BigDecimal paid
    }

    maintenance_requests_tbl {
        Long id PK
        Long apartment_id FK
        Long manager_id FK
        String maintenance_type "Enum"
        String title
        String status "Enum"
        Date date_submitted
    }

    furniture {
        Long id PK
        Long apartment_id FK
        String furniture_type "Enum"
        int quantity
    }

    managers_tbl {
        Long id PK
        String Name "UNIQUE"
        Long number
    }

    manager_maintenance_types {
        Long manager_id FK
        String maintenance_type "Enum"
    }

    config_tbl {
        Long id PK
        String config_key "UNIQUE"
        String config_value
    }
```
-------------------------------------

## N-Tier Layered Architecture & Domain Modules

The application enforces a strict separation of concerns by combining an **N-Tier (Layered) Architecture** with vertical domain slicing. All dependencies are managed and injected via Spring's **Inversion of Control (IoC) container** using Constructor Injection, ensuring the codebase remains modular, testable, and loosely coupled.

### How the Data Flows (Horizontal Layers)
Every incoming HTTP request traverses three distinct horizontal layers before returning a response:
1. **Controller Layer (Presentation):** REST API endpoints intercept GET, POST, PUT, and DELETE requests. This layer validates incoming parameters and maps the final outbound data into lightweight **Java Records (DTOs)**, ensuring internal database entities are never exposed directly to the frontend.
2. **Service Layer (Business Logic):** This layer acts as the brain of the application. It handles complex business rules, calculations, and enforces `@Transactional` boundaries so that any failing database operations are safely rolled back.
3. **Repository Layer (Data Access):** Interfaces extending Spring Data JPA manage all direct database interactions. This layer translates Java method calls into optimized SQL queries via Hibernate, communicating securely with the Aiven Cloud database.

### Core Business Domains (Vertical Slices)
To maintain a scalable enterprise structure, the N-Tier pattern is applied vertically across **8 distinct business domains**. Each of these modules operates independently with its own dedicated Entity, Repository, Service, and Controller:

1. **Apartments:** Manages physical property data, unit availability, and expected vs. actual rent scaling.
2. **Tenants:** Handles occupant lifecycle and leverages **soft-delete** mechanisms (`exists_flag`) to **retain historical tenant data.**
3. **Furniture:** Tracks inventory and asset allocation tied to specific units.
4. **Payments:** Records financial transactions, logging rent, maintenance, and utility breakdowns securely.
5. **Deposits:** Manages the negotiation, collection, and tracking of security deposits.
6. **Maintenance Requests:** Handles the ticketing system, tracking issue statuses, timestamps, and priority levels.
7. **Managers:** Maps specific maintenance personnel to the specialized request types they are qualified to handle.
8. **Configuration:** A dynamic settings table for storing environment variables and global application states.

-------------------------

## Security & Authentication Architecture (Defense-in-Depth)

This application implements a hardened, zero-trust security posture leveraging **Spring Security 6** and **OAuth2 / OpenID Connect (OIDC)**. The authentication and authorization pipelines are strictly decoupled, utilizing Microsoft Entra ID as the primary Identity Provider (IdP).

### 1. The OAuth2 / OIDC Handshake
* **Authentication Flow:** Unauthenticated traffic hitting the `DelegatingFilterProxy` is intercepted and routed through the `OAuth2AuthorizationRequestRedirectFilter`. Users are redirected to the Microsoft Entra ID authorization endpoint.
* **Token Resolution:** Upon successful authentication, the application exchanges the authorization code for an ID Token and Access Token (JWT) via the OIDC back-channel.

### 2. JWT Interception & Custom Role Translation (RBAC)
Out-of-the-box Spring Security prefixes roles with `SCOPE_` based on standard OAuth2 claims, which is insufficient for enterprise Role-Based Access Control.
* Engineered a custom **`EntraIdRoleConverter`** (implementing `Converter<Jwt, AbstractAuthenticationToken>`).
* This component intercepts the incoming Entra ID JWT, extracts the custom `roles` claim natively defined in the Azure App Registration, and dynamically maps them into standard Spring **`GrantedAuthority`** objects (e.g., mapping to `ROLE_OWNER`).

### 3. Granular Route Protection (`SecurityFilterChain`)
Authorization is enforced at the Servlet Filter level before requests ever reach the `DispatcherServlet`.
* **API Lockdown:** All internal data endpoints (e.g., `/apartments/**`, `/tenants/**`) explicitly require `hasRole('OWNER')`.
* **Static Asset Protection:** The frontend directories (e.g., `/OWNER_PAGES/**`) are secured behind the same filter chain, preventing unauthorized users from even downloading the HTML/JS payloads of the dashboard.

### 4. Custom Exception Handling & Routing
To prevent the notorious "infinite redirect loop" bug common in misconfigured OAuth2 applications, the security chain includes custom exception routing:
* **`AccessDeniedHandler`:** Catches authenticated users attempting to access elevated routes (403 Forbidden) and gracefully redirects them to a dedicated, visually consistent `/access-denied` endpoint handled by the `GlobalErrorController`.
* **Unmapped Roots:** Implemented a root level redirect (`/`) to automatically funnel successfully authenticated `OWNER` traffic directly into the secure dashboard.