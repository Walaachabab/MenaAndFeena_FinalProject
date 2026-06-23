# Mena & Feena | منا وفينا

## Project Description | وصف المشروع

### Arabic
منا وفينا منصة مجتمعية ذكية تهدف إلى تعزيز الترابط والتكافل والاستدامة داخل الأحياء السكنية. تتيح المنصة للسكان إدارة ملفاتهم الشخصية، إضافة أفراد الأسرة، متابعة لوحة معلومات الحي، رفع البلاغات، المشاركة في المبادرات والفعاليات، التصويت في انتخابات العمدة، واستعراض المعالم القريبة مثل المساجد والمدارس والحدائق.

كما تدعم المنصة عدداً من الميزات الذكية مثل تحليل البلاغات، تقارير العمدة، لوحة الحي، والتكامل مع خدمات خارجية مثل WhatsApp و Email و OpenStreetMap و Overpass API.

### English
Mena & Feena is a smart community platform designed to strengthen neighborhood connection, cooperation, and sustainability. The platform allows residents to manage their profiles, add family members, view neighborhood dashboards, submit issue reports, participate in events and initiatives, vote in mayor elections, and explore nearby landmarks such as mosques, schools, and parks.

The system also supports smart features such as issue analysis, mayor reports, neighborhood analytics, and integrations with WhatsApp, Email, OpenStreetMap, and Overpass API.

---

## Diagrams

### Class Diagram
![Class Diagram](./images/class-diagram.png)

### Use Case Diagram
![Use Case Diagram](./images/use-case-diagram.png)

---

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- REST APIs
- OpenStreetMap / Nominatim
- Overpass API
- WhatsApp API
- Email Service
- PDF Generation
- Postman

---

## Reenad Almadhi

### Models
- User
- Neighborhood
- FamilyMember
- Landmark
- ElectionRound
- MayorCandidate
- MayorProfile
- MayorVote

### Services
- UserService
- NeighborhoodService
- FamilyMemberService
- LandmarkService
- ElectionRoundService
- MayorCandidateService
- MayorProfileService
- MayorVoteService
- JwtService
- JwtAuthenticationFilter

### Controllers
- AuthController
- UserController
- NeighborhoodController
- FamilyMemberController
- LandmarkController
- ElectionRoundController
- MayorCandidateController
- MayorProfileController
- MayorVoteController

---

## API Endpoints

### Auth Controller

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/auth/register` | Register a new resident |
| POST |ct Description | وصف ا| Login and generate JWT token |

---

### User Controller

| Method | Endpoint | Description |
|---|---|---|
| GET |يز الترابط والتكافل والاس| Get all users |
| POST |

## Project Descript| Add user |
| PUT | وفينا

## Project Description | | Update user |
| DELETE |

## Project Description | وصف ال| Delete user |
| GET |ينا

## Project Descripti| Welcome page |
| GET |نا

## Project Descript| About platform |
| POST |
## Project Description || Contact support |
| GET |
## Project Description | وصف المشروع

#| Get residents in the same neighborhood |
| GET | | وصف المشروع

### Arabic
منا| Get user activity log |
| GET |oject Description | وصف المشرو| Get full profile |
| GET |## Project Description | وصف ال| Get basic profile |
| GET |# Project Description | وصف المشروع

| Get marketplace summary |
| GET |ect Description | وصف المشروع

### Arab| Get user's marketplace orders |
| GET |scription | وصف المشروع

### Arabic
منا وفين| Get orders on user's products |
| GET |scription | وصف المشروع

### Ara| Get family profile |
| GET | Project Description | وصف المش| Get voting profile |
| GET | Project Description | وصف المشر| Get events profile |
| GET | Project Description | وصف المشرو| Get reviews profile |
| GET |Project Description | وصف المشرو| Get issue reports profile |

---

### Neighborhood Controller

| Method | Endpoint | Description |
|---|---|---|
| GET |ترابط والتكافل والاستدامة داخل ال| Get all neighborhoods |
| POST |ject Description | وصف المشرو| Add neighborhood |
| PUT |## Project Description | وصف المشروع

### Arabic
| Update neighborhood |
| DELETE |ject Description | وصف المشروع

### Arabic
منا وف| Delete neighborhood |
| GET |Project Description | وصف المشروع

| Get neighborhood dashboard |

---

### Family Members Controller
| Method | Endpoint | Description |
|---|---|---|
| GET |بط والتكافل والاستدامة داخل ال| Get user's family members |
| POST | Description | وصف المشروع

##| Add family member |
| PUT |# Project Description | وصف المشروع

### Arabic
من| Update family member |
| DELETE |ect Description | وصف المشروع

### Arabic
منا وفين| Delete family member |

---

### Landmark Controller

| Method | Endpoint | Description |
|---|---|---|
| GET | تعزيز الترابط والتكافل والاس| Get all landmarks |
| POST | Project Description | وص| Add landmark |
| PUT |نا

## Project Description | وصف المشروع
| Update landmark |
| DELETE | Project Description | وصف المشروع

### A| Delete landmark |
| POST |## Project Description | و| Sync nearby landmarks using Overpass API |
| GET | وصف المشروع

### Arabic
منا| Get nearby landmarks |
| GET |roject Description | وصف المشرو| Get landmarks dashboard |

---

### Election Round Controller

| Method | Endpoint | Description |
|---|---|---|
| GET |ترابط والتكافل والاستدامة داخل الأح| Get all election rounds |
| GET |ect Description | وصف المشروع

### Arabic
منا وفي| Get election round details |
| POST |Description | وصف المشروع

### | Create election round |
| PUT |oject Description | وصف المشروع

### Arabic
| Update election round |
| DELETE |ct Description | وصف المشروع

### Arabic
منا| Delete election round |

---

### Mayor Candidate Controller

| Method | Endpoint | Description |
|---|---|---|
| GET |لترابط والتكافل والاستدامة داخل الأح| Get all mayor candidates |
| POST |t Description | وصف المشروع

### Arabic
منا وفينا | Apply as mayor candidate |
| GET |ct Description | وصف المشروع

### Arabic
منا| Get candidates by round |
| GET |ect Description | وصف المشروع

### Arabic
منا وفينا من| Get election dashboard |
| GET |ject Description | وصف المشروع

### Arabic
منا وفي| Get candidate profile |
| PUT |oject Description | وصف المشروع

### Arabic
منا و| Update candidate |
| DELETE |Project Description | وصف المشروع

### Arabic
منا| Delete candidate |

---

### Mayor Profile Controller

| Method | Endpoint | Description |
|---|---|---|
| GET |تعزيز الترابط والتكافل والاست| Get mayor profiles |
| POST |Project Description | وصف الم| Add mayor profile |
| PUT |# Project Description | وصف المشروع

### Arabic
م| Update mayor profile |
| DELETE |ect Description | وصف المشروع

### Arabic
منا وفي| Delete mayor profile |
| GET |roject Description | وصف المشروع

#| Get mayor analytics |
| GET |Project Description | وصف المشروع| Get mayor reports |
| GET |# Project Description | وصف المش| Send weekly issue report |
| GET |ct Description | وصف المشروع

### Ara| Send neighborhood performance report |
| GET |on | وصف المشروع

### Arabic
منا وفينا| Send resident satisfaction report |
| GET |ption | وصف المشروع

### Arabic
منا وفينا منصة م| Get AI initiative suggestions |
| POST |cription | وصف المشروع

### Arabic
منا وفينا منصة | Resend mayor appointment email |

---

### Mayor Vote Controller

| Method | Endpoint | Description |
|---|---|---|
| GET |بط والتكافل والاستدامة داخل الأ| Get all mayor votes |
| POST |roject Description | وصف المشروع

### Arabic
منا وفينا منصة مجتمعية | Vote for mayor candidate |
| DELETE |Description | وصف المشروع

### Arabic
م| Delete vote |

---

## Security

The platform uses JWT authentication.

Main security services:
- ذكية تهدف إلى- Mena & Feena | منا وفينا


JWT token includes:
- User email
- User ID
- Role
- Status

---

## Main Business Flows

### Resident Registration Flow
1. User registers.
2. Location coordinates are used to detect the neighborhood.
3. User is linked to the neighborhood.
4. WhatsApp welcome message is sent.
5. Election round opens automatically when the neighborhood reaches the required number of residents.

### Landmark Flow
1. User registers with latitude and longitude.
2. Landmarks are synced using Overpass API.
3. The system retrieves nearby mosques, schools, and parks.
4. Landmarks dashboard displays the closest services.

### Mayor Election Flow
1. Election round starts.
2. Residents apply as candidates.
3. Residents vote for candidates.
4.Election dashboard displays candidates and votes.
5. Winner is assigned as mayor.
6. Mayor gets access to analytics and reports.

### Mayor Reports Flow
1. Mayor requests report.
2. System analyzes neighborhood data.
3. PDF report is generated.
4. Report is sent by email.

---

## Team Members

- Reenad Almadhi
- Walaa Alrashidi
- Abdullah Alrasheed

---

## Project Status

Final Project - Tuwaiq Academy Java Web Development Bootcamp.
