package at.porscheinformatik.tutorial.todo.web;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            tokenRepository.saveToken(token, request, response);
        }

        response.addCookie(new Cookie(cookieName, token.getToken()));
    }
}
