package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.DTO.In.EventInDTO;
import org.example.menaandfeena_finalproject.Model.Event;
import org.example.menaandfeena_finalproject.Model.FamilyMember;
import org.example.menaandfeena_finalproject.Model.Initiative;
import org.example.menaandfeena_finalproject.Repository.EventRepository;
import org.example.menaandfeena_finalproject.Repository.FamilyMemberRepository;
import org.example.menaandfeena_finalproject.Repository.InitiativeRepository;
import org.example.menaandfeena_finalproject.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.menaandfeena_finalproject.Model.User;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final InitiativeRepository initiativeRepository;
    private final OpenAIService openAIService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    public void addEvent(EventInDTO eventInDTO) {
        Event event = new Event();
        event.setTitle(eventInDTO.getTitle());
        event.setDescription(eventInDTO.getDescription());
        event.setDate(eventInDTO.getDate());
        event.setLocation(eventInDTO.getLocation());
        event.setIsPaid(eventInDTO.getIsPaid());
        event.setPrice(eventInDTO.getPrice());
        event.setMaxParticipants(eventInDTO.getMaxParticipants());
        event.setStatus("ACTIVE");

        if (Boolean.FALSE.equals(eventInDTO.getIsPaid())) {
            event.setPrice(0.0);
        }

        if (Boolean.TRUE.equals(eventInDTO.getIsPaid()) && (eventInDTO.getPrice() == null || eventInDTO.getPrice() <= 0)) {
            throw new ApiException("Paid event must have price greater than 0");
        }

        eventRepository.save(event);
    }


    public void updateEvent(Integer id, EventInDTO eventInDTO) {
        Event oldEvent = eventRepository.findEventById(id);

      if(oldEvent == null){
          throw new ApiException("Event not found");
      }
        oldEvent.setTitle(eventInDTO.getTitle());
        oldEvent.setDescription(eventInDTO.getDescription());
        oldEvent.setDate(eventInDTO.getDate());
        oldEvent.setLocation(eventInDTO.getLocation());
        oldEvent.setIsPaid(eventInDTO.getIsPaid());
        oldEvent.setPrice(eventInDTO.getPrice());
        oldEvent.setMaxParticipants(eventInDTO.getMaxParticipants());

        if (Boolean.FALSE.equals(eventInDTO.getIsPaid())) {
            oldEvent.setPrice(0.0);
        }

        if (Boolean.TRUE.equals(eventInDTO.getIsPaid()) && (eventInDTO.getPrice() == null || eventInDTO.getPrice() <= 0)) {
            throw new ApiException("Paid event must have price greater than 0");
        }

        eventRepository.save(oldEvent);

    }


    public void deleteEvent(Integer id) {
        Event event = eventRepository.findEventById(id);

        if (event == null) {
            throw new ApiException("Event not found");
        }

        eventRepository.delete(event);
    }

// Walaa
    public List<Event> getUpcomingEvents() {
        return eventRepository.findEventsByDateAfter(LocalDateTime.now());
    }




  // Walaa
  public List<Event> getPreviousEvents() {
      return eventRepository.findEventsByDateBefore(LocalDateTime.now());
  }


  // Walaa
  public List<Event> getEventsByDate(LocalDate date) {
      LocalDateTime startOfDay = date.atStartOfDay();
      LocalDateTime endOfDay = date.atTime(23, 59, 59);
      return eventRepository.findEventsByDateBetween(startOfDay, endOfDay);

  }

    // Walaa
    public Event getEventById(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        return event;
    }




    // Walaa

    public void createEvent(Integer userId, EventInDTO eventInDTO) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        Event event = new Event();
        event.setTitle(eventInDTO.getTitle());
        event.setDescription(eventInDTO.getDescription());
        event.setDate(eventInDTO.getDate());
        event.setLocation(eventInDTO.getLocation());
        event.setIsPaid(eventInDTO.getIsPaid());
        event.setPrice(eventInDTO.getPrice());
        event.setMaxParticipants(eventInDTO.getMaxParticipants());

        if (Boolean.FALSE.equals(eventInDTO.getIsPaid())) {
            event.setPrice(0.0);
        }

        if (Boolean.TRUE.equals(eventInDTO.getIsPaid()) && (eventInDTO.getPrice() == null || eventInDTO.getPrice() <= 0)) {
            throw new ApiException("Paid event must have price greater than 0");
        }

        event.setUser(user);
        event.setStatus("ACTIVE");

        eventRepository.save(event);
    }


// Walaa

    public String recommendEventForUser(Integer userId) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        List<FamilyMember> familyMembers = familyMemberRepository.findByUserId(userId);
        System.out.println("FAMILY SIZE = " + familyMembers.size());

        for (FamilyMember member : familyMembers) {
            System.out.println("MEMBER AGE = " + member.getAge());
        }
        List<Event> upcomingEvents = eventRepository.findEventsByDateAfter(LocalDateTime.now());

        if (upcomingEvents.isEmpty()) {
            throw new ApiException("No upcoming events found");
        }

       // return "ready";
        StringBuilder prompt = new StringBuilder();

        prompt.append("Family Members:\n");

        for (FamilyMember member : familyMembers) {
            prompt.append("Name: ")
                    .append(member.getName())
                    .append(" | Age: ")
                    .append(member.getAge())
                    .append(" | Relation: ")
                    .append(member.getRelation())
                    .append("\n");
        }

        prompt.append("\nAvailable Events:\n");

        for (Event event : upcomingEvents) {
            prompt.append("- ")
                    .append(event.getTitle())
                    .append(": ")
                    .append(event.getDescription())
                    .append("\n");
        }
        String aiRecommendation = openAIService.askAI(
                "You are an AI event recommendation system for a smart neighborhood platform. " +
                        "Recommend one suitable event for EACH family member individually based on their age and relation. " +
                        "Use these age groups exactly: children = age 0 to 12, teenagers = age 13 to 18, adults = age 19 and above. " +
                        "Children prefer drawing, games, entertainment, and educational activities. " +
                        "Teenagers prefer sports, technology, competitions, and skill-building activities. " +
                        "Adults prefer community, cultural, educational, and social activities. " +
                        "Choose only from the available events list. " +
                        "Return only in this format:\n" +
                        "Family Member: <name> | Age Group: <children/teenagers/adults> | Recommended Event: <event title> | Reason: <short reason>",
                prompt.toString()
        );

        return aiRecommendation;
    }

    public String generateWeekendFamilyPlan(Integer userId) {

        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        List<FamilyMember> familyMembers = familyMemberRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();
        LocalDate weekendStart = getUpcomingWeekendStart(today);
        LocalDate weekendEnd = weekendStart.getDayOfWeek() == DayOfWeek.FRIDAY ? weekendStart.plusDays(1) : weekendStart;
        LocalDateTime weekendStartDateTime = weekendStart.atStartOfDay();
        LocalDateTime weekendEndDateTime = weekendEnd.atTime(23, 59, 59);

        Integer userNeighborhoodId = user.getNeighborhood() == null ? null : user.getNeighborhood().getId();

        List<Event> upcomingEvents = eventRepository.findEventsByDateBetween(weekendStartDateTime, weekendEndDateTime)
                .stream()
                .filter(event -> "ACTIVE".equals(event.getStatus()))
                .filter(event -> userNeighborhoodId == null
                        || event.getNeighborhood() == null
                        || event.getNeighborhood().getId().equals(userNeighborhoodId))
                .toList();

        List<Initiative> upcomingInitiatives = initiativeRepository.findInitiativesByDateAfter(weekendStart.minusDays(1))
                .stream()
                .filter(initiative -> initiative.getDate() != null && !initiative.getDate().isAfter(weekendEnd))
                .filter(initiative -> "ACTIVE".equals(initiative.getStatus()))
                .filter(initiative -> userNeighborhoodId == null
                        || initiative.getNeighborhood() == null
                        || initiative.getNeighborhood().getId().equals(userNeighborhoodId))
                .toList();

        if (upcomingEvents.isEmpty() && upcomingInitiatives.isEmpty()) {
            throw new ApiException("No upcoming events or initiatives found for the weekend plan");
        }

        StringBuilder prompt = new StringBuilder();

        prompt.append("Family Members:\n");
        if (familyMembers.isEmpty()) {
            prompt.append("- Main user only. No family members are registered.\n");
        } else {
            for (FamilyMember member : familyMembers) {
                prompt.append("- Name: ")
                        .append(member.getName())
                        .append(" | Age: ")
                        .append(member.getAge())
                        .append(" | Gender: ")
                        .append(member.getGender())
                        .append(" | Relation: ")
                        .append(member.getRelation())
                        .append("\n");
            }
        }

        prompt.append("\nUpcoming Events:\n");
        for (Event event : upcomingEvents) {
            prompt.append("- ID ")
                    .append(event.getId())
                    .append(" | ")
                    .append(event.getTitle())
                    .append(" | Date: ")
                    .append(event.getDate())
                    .append(" | Location: ")
                    .append(event.getLocation())
                    .append(" | Paid: ")
                    .append(event.getIsPaid())
                    .append(" | Price: ")
                    .append(event.getPrice())
                    .append(" | Description: ")
                    .append(event.getDescription())
                    .append("\n");
        }

        prompt.append("\nUpcoming Initiatives:\n");
        for (Initiative initiative : upcomingInitiatives) {
            prompt.append("- ID ")
                    .append(initiative.getId())
                    .append(" | ")
                    .append(initiative.getTitle())
                    .append(" | Date: ")
                    .append(initiative.getDate())
                    .append(" | Category: ")
                    .append(initiative.getCategory())
                    .append(" | Description: ")
                    .append(initiative.getDescription())
                    .append("\n");
        }

        String weekendPlan = openAIService.askAI(
                """
                You are an AI family activity planner for a smart neighborhood platform.
                Build a practical weekend plan using only the provided events and initiatives.
                Rules:
                - Use a friendly Arabic tone.
                - Choose activities suitable for the family members' ages and relations.
                - Include a Friday plan and a Saturday plan.
                - Mention event or initiative titles exactly as provided.
                - Do not invent activities, locations, prices, or dates.
                - If there are not enough activities, create a lighter plan instead of inventing.
                - Keep the plan concise.
                Return only this format:
                Friday:
                - Morning: <activity title> | <why it fits>
                - Evening: <activity title> | <why it fits>

                Saturday:
                - Morning: <activity title> | <why it fits>
                - Evening: <activity title> | <why it fits>

                Notes:
                - <short practical note>
                """,
                prompt.toString()
        );

        if (weekendPlan == null || weekendPlan.isBlank() || "ERROR_FALLBACK".equals(weekendPlan)) {
            throw new ApiException("AI weekend planner failed. Please try again later");
        }

        return weekendPlan;
    }

    private LocalDate getUpcomingWeekendStart(LocalDate today) {
        if (today.getDayOfWeek() == DayOfWeek.FRIDAY) {
            return today;
        }

        LocalDate date = today;
        while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusDays(1);
        }
        return date;
    }


















    // Walaa
//    private EventOutDTO convertToOutDTO(Event event) {
//        return new EventOutDTO(
//                event.getId(),
//                event.getTitle(),
//                event.getDescription(),
//                event.getDate(),
//                event.getLocation(),
//                event.getIsPaid(),
//                event.getPrice(),
//                event.getMaxParticipants(),
//                event.getStatus()
//        );
//    }


}
