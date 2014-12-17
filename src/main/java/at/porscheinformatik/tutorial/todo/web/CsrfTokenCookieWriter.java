package at.porscheinformatik.tutorial.todo.web;

import static org.springframework.util.StringUtils.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.StringUtils;

/**
 * Adds the CSRF token as cookie to be read by AngularJS.
 */
public class CsrfTokenCookieWriter implements HeaderWriter
{
    private final CsrfTokenRepository tokenRepository;
    private String cookieName;

    /**
     * @param csrfTokenRepository the token repo
     * @param csrfCookieName cookies' name
     */
    public CsrfTokenCookieWriter(CsrfTokenRepository csrfTokenRepository, String csrfCookieName)
    {
        this.tokenRepository = csrfTokenRepository;
        this.cookieName = csrfCookieName;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response)
    {
        CsrfToken token = tokenRepository.loadToken(request);
        if (token == null)
        {
            token = tokenRepository.generateToken(request);
        }

        String contextPath = request.getContextPath();
        Cookie csrfCookie = new Cookie(cookieName, token.getToken());
		csrfCookie.setPath(isEmpty(contextPath) ? "/" : contextPath);
		response.addCookie(csrfCookie);
    }
}
