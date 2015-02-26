package at.porscheinformatik.tutorial.todo.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.web.csrf.CsrfToken;

public class StatelessCsrfTokenRepositoryTest
{
    private static final String COOKIE_NAME = "MY-COOKIE";
    private static MockServletContext servletContext = new MockServletContext("/myapp");

    @Test
    public void loadTokenWorks()
    {
        StatelessCsrfTokenRepository csrfTokenRepository = new StatelessCsrfTokenRepository();
        csrfTokenRepository.setCookieName(COOKIE_NAME);

        CsrfToken token = csrfTokenRepository.generateToken(new MockHttpServletRequest(servletContext));
        CsrfToken loadedToken = loadToken(csrfTokenRepository, token);

        assertThat(loadedToken, equalTo(token));
    }

    @Test
    public void hmacWorks()
    {
        StatelessCsrfTokenRepository csrfTokenRepository = new StatelessCsrfTokenRepository();
        csrfTokenRepository.setCookieName(COOKIE_NAME);
        CsrfToken token = csrfTokenRepository.generateToken(new MockHttpServletRequest(servletContext));

        csrfTokenRepository.setPasscode("NEW Passcode");
        CsrfToken loadedToken = loadToken(csrfTokenRepository, token);

        assertThat(loadedToken, is(nullValue()));
    }

    private static CsrfToken loadToken(StatelessCsrfTokenRepository csrfTokenRepository, CsrfToken token)
    {
        MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
        request.addHeader(token.getHeaderName(), token.getToken());
        request.setCookies(new Cookie(COOKIE_NAME, token.getToken()));
        CsrfToken loadedToken = csrfTokenRepository.loadToken(request);
        return loadedToken;
    }

}
