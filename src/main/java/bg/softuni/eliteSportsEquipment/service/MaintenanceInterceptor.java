package bg.softuni.eliteSportsEquipment.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!request.getRequestURI().equals("/maintenance")) {
            LocalDateTime now = LocalDateTime.now();

            //Unable to access website between 4 and 5 am every sunday
            if ((now.getHour() >= 4 && now.getHour() <= 5) && now.getDayOfWeek().name().equals("SUNDAY")) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
