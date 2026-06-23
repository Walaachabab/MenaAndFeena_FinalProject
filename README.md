Mena & Feena | منا وفينا
Project Description | وصف المشروع
Arabic
منا وفينا هي منصة مجتمعية ذكية تهدف إلى تعزيز الترابط والتكافل بين سكان الأحياء من خلال توفير خدمات رقمية متكاملة. تُمكّن السكان من الإبلاغ عن المشكلات، المشاركة في الفعاليات والمبادرات المجتمعية، التواصل مع الجيران، انتخاب عمدة الحي، واستعراض المعالم القريبة. كما توفر سوقًا مجتمعيًا لبيع وتأجير وتبادل المنتجات بين السكان مع إدارة الطلبات والمدفوعات والتأمينات. تعتمد المنصة على تقنيات الذكاء الاصطناعي وتحليل البيانات لتحسين جودة الحياة، دعم اتخاذ القرار، وتعزيز المشاركة المجتمعية داخل الأحياء.
English
Mena & Feena is a smart community platform designed to strengthen social connections and cooperation among neighborhood residents through a set of integrated digital services. The platform enables residents to report issues, participate in community events and initiatives, connect with neighbors, vote for a neighborhood mayor, and explore nearby landmarks. It also provides a community marketplace for buying, renting, and exchanging products, along with order management, payment processing, and security deposits. By leveraging Artificial Intelligence and data analytics, the platform aims to improve quality of life, support decision-making, and increase community engagement within neighborhoods.


⸻


Diagrams
Class Diagram
Insert Class Diagram Image Here


⸻


Use Case Diagram
Insert Use Case Diagram Image Here


⸻


API Documentation
Postman Documentation:
Paste Postman Documentation Link Here
Example:
https://documenter.getpostman.com/view/xxxxxxxx


⸻
Deployment
Live Application:
Paste Deployment URL Here
Example:
https://mena-and-feena.up.railway.app

⸻
Technologies Used
Java
Spring Boot
Spring Security
JWT Authentication
Spring Data JPA
MySQL
REST APIs
OpenAI API
WhatsApp API
Email Service
OpenStreetMap API
Overpass API
PDF Generation
Postman
GitHub


⸻


Modules Implemented
Models
User
Neighborhood
FamilyMember
Landmark
ElectionRound
MayorCandidate
MayorProfile
MayorVote


⸻


Services
UserService
NeighborhoodService
FamilyMemberService
LandmarkService
ElectionRoundService
MayorCandidateService
MayorProfileService
MayorVoteService
JwtService
JwtAuthenticationFilter


⸻


Controllers
AuthController
UserController
NeighborhoodController
FamilyMemberController
LandmarkController
ElectionRoundController
MayorCandidateController
MayorProfileController
MayorVoteController


⸻


API Endpoints
Auth Controller
Register
POST /api/v1/auth/register
Login
POST /api/v1/auth/login


⸻


Election Round Controller
Get All Election Rounds
GET /api/v1/election-rounds/get-all
Get Election Round Details
GET /api/v1/election-rounds/get/{roundId}/details
Create Election Round
POST /api/v1/election-rounds/add
Update Election Round
PUT /api/v1/election-rounds/update/{roundId}
Delete Election Round
DELETE /api/v1/election-rounds/delete/{roundId}


⸻


Family Members Controller
CRUD Operations
GET Family Members
POST Add Family Member
PUT Update Family Member
DELETE Family Member


⸻


Landmark Controller
Get All Landmarks
GET
Create Landmark
POST
Update Landmark
PUT
Delete Landmark
DELETE
Sync Landmarks
POST
Nearby Landmarks
GET
Landmark Dashboard
GET


⸻


Mayor Candidate Controller
Get All Candidates
GET
Apply For Candidacy
POST
Get Candidates By Round
GET
Election Dashboard
GET
Candidate Profile
GET
Update Candidate
PUT
Delete Candidate
DELETE


⸻


Mayor Profile Controller
CRUD Operations
Mayor Analytics
GET
Mayor Reports
GET
Weekly Report
GET
Performance Report
GET
Satisfaction Report
GET


⸻


Mayor Vote Controller
Get All Votes
GET
Vote For Candidate
POST
Delete Vote
DELETE


⸻


Neighborhood Controller
Get All Neighborhoods
GET
CRUD Operations
Neighborhood Dashboard
GET


⸻


User Controller
Get All Users
GET
CRUD Operations
Welcome Page
GET
About Page
GET
Contact Page
POST
Neighborhood Residents
GET
Activity Log
GET
Full Profile
GET
Basic Profile
GET
Marketplace Summary
GET
Marketplace My Orders
GET
Marketplace Product Orders
GET
Profile Family
GET
Profile Votes
GET
Profile Events
GET
Profile Reviews
GET
Profile Issues
GET


⸻


Security
JWT Authentication
JwtService
JwtAuthenticationFilter


⸻


Team Members
Reenad Almadhi
Walaa Alrashidi
Abdullah Alrasheed


⸻
Project
Tuwaiq Academy – Java Web Development Bootcamp Final Project
