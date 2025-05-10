package yeoun.auth.infrastructure;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import yeoun.common.SuccessResponse;

public class CookieUtil {

    private CookieUtil() {}

    public static void addCookie(HttpServletResponse response, String name, String value, Long maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(maxAge)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static String getTokenFromCookies(String tokenType, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> tokenType.equals(cookie.getName()))
                    .findFirst();
            if (tokenCookie.isPresent()) {
                return tokenCookie.get().getValue();
            }
        }
        return null;
    }

    public static void logout(HttpServletResponse response) {
        addCookie(response, "accessToken", "", 0L);
        addCookie(response, "refreshToken", "", 0L);
    }
}
