# 🏡🌳 Mena & Feena | منا وفينا

## Project Description | وصف المشروع

### Arabic

منا وفينا هي منصة مجتمعية ذكية تهدف إلى تعزيز الترابط والتكافل بين سكان الأحياء من خلال توفير خدمات رقمية متكاملة، مثل الإبلاغ عن المشكلات، المشاركة في الفعاليات والمبادرات، التواصل مع الجيران، انتخاب عمدة الحي، واستعراض المعالم القريبة.

كما توفر المنصة سوقًا مجتمعيًا لبيع وتأجير وتبادل المنتجات بين السكان، مع إدارة الطلبات والمدفوعات والتأمينات. وتعتمد على تقنيات الذكاء الاصطناعي وتحليل البيانات لتحسين جودة الحياة ودعم اتخاذ القرار وتعزيز المشاركة المجتمعية داخل الأحياء.

### English

Mena & Feena is a smart community platform designed to strengthen social connections and cooperation among neighborhood residents through integrated digital services such as issue reporting, community events and initiatives, neighbor communication, neighborhood mayor elections, and nearby landmark discovery.

The platform also provides a community marketplace for buying, renting, and exchanging products, including order management, payments, and security deposits. By leveraging Artificial Intelligence and data analytics, Mena & Feena aims to improve quality of life, support decision-making, and increase community engagement within neighborhoods.

---

## 📊 System Diagrams

### Class Diagram
![Class Diagram](https://github.com/Reenad1424/MenaAndFeena_FinalProject/blob/main/classDiagram.png)

### Use Case Diagram

![Use Case Diagram](https://github.com/Walaachabab/MenaAndFeena_FinalProject/blob/main/MenaAndFeena.drawio%20(1).png?raw=true)

## 🚀 Deployment

http://menaandfeena-env.eba-dsbymcfp.eu-central-1.elasticbeanstalk.com/api/v1/users/welcome

## 📮 API Documentation

https://github.com/Reenad1424/MenaAndFeena_FinalProject/blob/main/API%20documentation.postman_collection%20(1).json

# 🎨 Figma Design:

https://www.figma.com/design/e3KckUskVxFWFs5o5K72JD/%D9%85%D9%86%D9%8B%D8%A7-%D9%88%D9%81%D9%8A%D9%86%D8%A7?node-id=0-1&t=orEb4zsaxuzTMBDy-1


## 🛠 Technologies Used

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* MySQL
* OpenAI API
* WhatsApp API
* Email Service
* OpenStreetMap API
* Overpass API
* PDF Generation
* Postman

---
## 📦 Modules Implemented

### Models

* Announcement
* Event
* EventRegistration
* Initiative
* InitiativeParticipation
* Review
* EventFeatures
* EventSchedule
* Ticket

### Tests JWT

* EventRegistrationRepositoryTest
* InitiativeRepositoryTest
* ReviewRepositoryTest
* InitiativeServiceTest
* ReviewServiceTest

---

## 📢 Announcement Controller

| Feature                | Method | Endpoint                                                  |
| ---------------------- | ------ | --------------------------------------------------------- |
| Get All Announcements  | GET    | `/api/v1/announcement/get`                                |
| Get My Announcements   | GET    | `/api/v1/announcement/my`                                 |
| Create Announcement    | POST   | `/api/v1/announcement/create`                             |
| Update Announcement    | PUT    | `/api/v1/announcement/update/{id}`                        |
| Delete Announcement    | DELETE | `/api/v1/announcement/delete/{id}`                        |
| Moderate Announcement  | POST   | `/api/v1/announcement/moderate/{announcementId}`          |
| Search Announcements   | GET    | `/api/v1/announcement/search`                             |
| Get Announcement By ID | GET    | `/api/v1/announcement/{id}`                               |
| Contact Publisher      | GET    | `/api/v1/announcement/contact-publisher/{announcementId}` |
| Test OpenAI            | GET    | `/api/v1/announcement/test-openai`                        |

---

## 🎉 Event Controller

| Feature                      | Method | Endpoint                               |
| ---------------------------- | ------ | -------------------------------------- |
| Get All Events               | GET    | `/api/v1/event/get`                    |
| Create Event                 | POST   | `/api/v1/event/create`                 |
| Update Event                 | PUT    | `/api/v1/event/update/{id}`            |
| Delete Event                 | DELETE | `/api/v1/event/delete/{id}`            |
| Get Upcoming Events          | GET    | `/api/v1/event/upcoming`               |
| Get Previous Events          | GET    | `/api/v1/event/previous`               |
| Get Events By Date           | GET    | `/api/v1/event/date/{date}`            |
| Get Event By ID              | GET    | `/api/v1/event/{id}`                   |
| Get All Event Features       | GET    | `/api/v1/event/features`               |
| Get Event Features           | GET    | `/api/v1/event/{eventId}/features`     |
| Get Event Schedule           | GET    | `/api/v1/event/{eventId}/schedule`     |
| Upload Event Image           | POST   | `/api/v1/event/{eventId}/upload-image` |
| Recommend Event              | GET    | `/api/v1/event/recommend`              |
| Generate Weekend Family Plan | GET    | `/api/v1/event/weekend-plan`           |

---

## 🎟 Event Registration Controller

| Feature                     | Method | Endpoint                                                                |
| --------------------------- | ------ | ----------------------------------------------------------------------- |
| Get All Event Registrations | GET    | `/api/v1/event-registration/get`                                        |
| Update Event Registration   | PUT    | `/api/v1/event-registration/update/{id}`                                |
| Delete Event Registration   | DELETE | `/api/v1/event-registration/delete/{id}`                                |
| Register To Event           | POST   | `/api/v1/event-registration/register/{eventId}`                         |
| Register Family Member      | POST   | `/api/v1/event-registration/register-family/{familyMemberId}/{eventId}` |
| Get My Event Registrations  | GET    | `/api/v1/event-registration/my`                                         |
| Get Event Attendees         | GET    | `/api/v1/event-registration/event/{eventId}/attendees`                  |

---

## 📅 Event Schedule Controller

| Feature              | Method | Endpoint                                  |
| -------------------- | ------ | ----------------------------------------- |
| Add Schedule Item    | POST   | `/api/v1/event-schedules/event/{eventId}` |
| Update Schedule Item | PUT    | `/api/v1/event-schedules/{scheduleId}`    |
| Delete Schedule Item | DELETE | `/api/v1/event-schedules/{scheduleId}`    |
| Get Event Schedule   | GET    | `/api/v1/event-schedules/event/{eventId}` |

---

## 🌱 Initiative Controller

| Feature                     | Method | Endpoint                                         |
| --------------------------- | ------ | ------------------------------------------------ |
| Get All Initiatives         | GET    | `/api/v1/initiative/get`                         |
| Create Initiative           | POST   | `/api/v1/initiative/create`                      |
| Update Initiative           | PUT    | `/api/v1/initiative/update/{id}`                 |
| Delete Initiative           | DELETE | `/api/v1/initiative/delete/{id}`                 |
| Get Initiative By ID        | GET    | `/api/v1/initiative/{id}`                        |
| Get Initiatives By Category | GET    | `/api/v1/initiative/category/{category}`         |
| Get Upcoming Initiatives    | GET    | `/api/v1/initiative/upcoming`                    |
| Upload Initiative Image     | POST   | `/api/v1/initiative/{initiativeId}/upload-image` |

---

## 🤝 Initiative Participation Controller

| Feature                           | Method | Endpoint                                                                       |
| --------------------------------- | ------ | ------------------------------------------------------------------------------ |
| Get All Initiative Participations | GET    | `/api/v1/initiative-participation/get`                                         |
| Update Initiative Participation   | PUT    | `/api/v1/initiative-participation/update/{id}`                                 |
| Delete Initiative Participation   | DELETE | `/api/v1/initiative-participation/delete/{id}`                                 |
| Join Initiative                   | POST   | `/api/v1/initiative-participation/join/{initiativeId}`                         |
| Join Family Member                | POST   | `/api/v1/initiative-participation/join-family/{familyMemberId}/{initiativeId}` |
| Get Initiative Participants       | GET    | `/api/v1/initiative-participation/{initiativeId}/participants`                 |

---

## 💬 Inquiry Controller

| Feature                     | Method | Endpoint                                               |
| --------------------------- | ------ | ------------------------------------------------------ |
| Create Announcement Inquiry | POST   | `/api/v1/inquiry/announcement/{announcementId}/create` |
| Add Message                 | POST   | `/api/v1/inquiry/{inquiryId}/message`                  |
| Get Inquiry Messages        | GET    | `/api/v1/inquiry/{inquiryId}/messages`                 |
| Get My Inquiries            | GET    | `/api/v1/inquiry/my-inquiries`                         |
| Get Received Inquiries      | GET    | `/api/v1/inquiry/received`                             |
| Resolve Inquiry             | PUT    | `/api/v1/inquiry/{inquiryId}/resolve`                  |

---

## ⭐ Review Controller

| Feature                       | Method | Endpoint                                                  |
| ----------------------------- | ------ | --------------------------------------------------------- |
| Get All Reviews               | GET    | `/api/v1/review/get`                                      |
| Get My Reviews                | GET    | `/api/v1/review/my`                                       |
| Update Review                 | PUT    | `/api/v1/review/update/{id}`                              |
| Delete Review                 | DELETE | `/api/v1/review/delete/{id}`                              |
| Add Event Review              | POST   | `/api/v1/review/add-event-review/{eventId}`               |
| Add Initiative Review         | POST   | `/api/v1/review/add-initiative-review/{initiativeId}`     |
| Add Marketplace Seller Review | POST   | `/api/v1/review/marketplace/order-item/{orderItemId}`     |
| Get Seller Reviews            | GET    | `/api/v1/review/seller/{sellerId}`                        |
| Get Event Reviews             | GET    | `/api/v1/review/event/{eventId}`                          |
| Get Initiative Reviews        | GET    | `/api/v1/review/initiative/{initiativeId}`                |
| Filter Event Reviews          | GET    | `/api/v1/review/event/{eventId}/filter`                   |
| Get Average Rating            | GET    | `/api/v1/review/average/{eventId}`                        |
| Get Positive Ratio            | GET    | `/api/v1/review/positive-ratio/{eventId}`                 |
| Get Event AI Summary          | GET    | `/api/v1/review/ai-summary/{eventId}`                     |
| Get Initiative Average Rating | GET    | `/api/v1/review/average/initiative/{initiativeId}`        |
| Get Initiative Positive Ratio | GET    | `/api/v1/review/positive-ratio/initiative/{initiativeId}` |

---

## 🎫 Ticket Controller

| Feature                    | Method | Endpoint                                        |
| -------------------------- | ------ | ----------------------------------------------- |
| Get Ticket By Registration | GET    | `/api/v1/tickets/registration/{registrationId}` |
| Check In Ticket            | PUT    | `/api/v1/tickets/check-in/{ticketCode}`         |

---

## 🤖 AI Features

| Feature                        | Method | Endpoint                                               |
| ------------------------------ | ------ | ------------------------------------------------------ |
| Smart Event Creation           | POST   | `/api/v1/event/create`                                 |
| Event Recommendation           | GET    | `/api/v1/event/recommend`                              |
| Announcement Moderation        | POST   | `/api/v1/announcement/moderate/{announcementId}`       |
| Event Review AI Summary        | GET    | `/api/v1/review/ai-summary/{eventId}`                  |
| Smart Initiative Participation | POST   | `/api/v1/initiative-participation/join/{initiativeId}` |

---

## 👨‍💻 Team Members

* Reenad Almadhi
* Walaa Alrashidi
* Abdullah Alrasheed

---

## 🎓 Project

**Tuwaiq Academy – Java Web Development Bootcamp Final Project**
