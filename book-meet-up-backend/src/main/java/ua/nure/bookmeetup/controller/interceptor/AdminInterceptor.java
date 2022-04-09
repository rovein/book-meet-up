package ua.nure.bookmeetup.controller.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ua.nure.bookmeetup.util.PathsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception exception) throws Exception {
        if (request.getRequestURI().contains("backup")) {
            Files.deleteIfExists(PathsUtil.getResourcePath("backup/backup_data.sql"));
        }
    }

}
