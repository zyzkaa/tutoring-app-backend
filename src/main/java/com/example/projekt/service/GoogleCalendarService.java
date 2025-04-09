package com.example.projekt.service;

import com.example.projekt.model.LessonState;
import com.example.projekt.repository.LessonSlotRepository;
import com.example.projekt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class GoogleCalendarService {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final LessonSlotRepository lessonSlotRepository;

    private record CalendarDto(
            String id,
            String summary
    ){}

    private record CalendarList(
            List<CalendarDto> calendars
    ){}

    public void addLessonsToCalendar(OAuth2AuthenticationToken authentication){
        String registrationId = authentication.getAuthorizedClientRegistrationId();
        String principalName = authentication.getName();

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(registrationId, principalName);
        String accessToken = client.getAccessToken().getTokenValue();

        RestClient restClient = RestClient.create();
        String eventUrl = "https://www.googleapis.com/calendar/v3/calendars/primary/events";
        String calendarUrl = "https://www.googleapis.com/calendar/v3/calendars";
        String calendarName = "koreczki inc. calendar";

        var calendarList = restClient.get()
                .uri("https://www.googleapis.com/calendar/v3/users/me/calendarList")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(CalendarList.class);

        String calendarId = calendarList.calendars().stream()
                .filter(calendar -> calendar.summary.equals(calendarName))
                .map(calendar -> calendar.id())
                .findFirst().orElse(null);

        if(calendarId != null){
            restClient.post()
                    .uri("https://www.googleapis.com/calendar/v3/calendars/" + calendarId + "/clear")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .toBodilessEntity();
        } else {
            Map<String, Object> calendarData = Map.of(
                    "summary", calendarName,
                    "description", "all synced lessons will appear here",
                    "timeZone", "Europe/Warsaw",
                    "colorId", "9",
                    "defaultReminders", List.of(
                            Map.of("method", "popup", "minutes", 10)
                    )
            );

            var calendar = restClient.post()
                    .uri("https://www.googleapis.com/calendar/v3/calendars")
                    .header("Authorization", "Bearer " + accessToken)
                    .body(calendarData)
                    .retrieve()
                    .body(CalendarDto.class);

            calendarId = calendar.id();
        }

        if(calendarId == null){
            throw new RuntimeException("Couldn't add lessons to Google Calendar");
        }

        String role = authentication.getPrincipal().getAuthorities().toString();
        String email = authentication.getPrincipal().getAttribute("email");
        String calendarUri = "https://www.googleapis.com/calendar/v3/calendars/" + calendarId + "/events";
        if(role.equals("ROLE_USER")){
            var lessons = lessonSlotRepository.findLessonSlotsByStudentEmailAndState(email, LessonState.BOOKED);
            lessons.forEach(lesson -> {
                        Map<String, Object> lessonData = Map.of(
                                "summary", lesson.getSubject().getName(),
                                "description", lesson.getMode().toString(),
                                "start", Map.of(
                                        "dateTime", lesson.getTime(),
                                        "timeZone", "Europe/Warsaw"
                                ),
                                "end", Map.of(
                                        "dateTime", lesson.getTime().plusHours(1),
                                        "timeZone", "Europe/Warsaw"
                                )
                        );

                        restClient.post()
                                .uri(calendarUri)
                                .header("Authorization", "Bearer " + accessToken)
                                .body(lessonData)
                                .retrieve()
                                .toBodilessEntity();
                    });

        } else if(role.equals("ROLE_TEACHER")){
            var lessons = lessonSlotRepository.findLessonSlotsByTeacherEmailAndState(email, LessonState.BOOKED);
            lessons.forEach(lesson -> {
                Map<String, Object> lessonData = Map.of(
                        "summary", lesson.getSubject().getName(),
                        "description", lesson.getMode().toString() + " " + lesson.getStudent().getFirstName() + " " + lesson.getStudent().getLastName(),
                        "start", Map.of(
                                "dateTime", lesson.getTime(),
                                "timeZone", "Europe/Warsaw"
                        ),
                        "end", Map.of(
                                "dateTime", lesson.getTime().plusHours(1),
                                "timeZone", "Europe/Warsaw"
                        )
                );

                restClient.post()
                        .uri(calendarUri)
                        .header("Authorization", "Bearer " + accessToken)
                        .body(lessonData)
                        .retrieve()
                        .toBodilessEntity();
            });
        }

    }
}
