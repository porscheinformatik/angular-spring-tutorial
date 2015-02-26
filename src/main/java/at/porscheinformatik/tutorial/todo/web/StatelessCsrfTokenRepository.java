package at.porscheinformatik.tutorial.todo.web;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.Assert;

public class StatelessCsrfTokenRepository implements CsrfTokenRepository
{
    private static final Logger LOG = LoggerFactory.getLogger(StatelessCsrfTokenRepository.class);

    private static final int RANDOM_BYTES = 10;

    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";

    private static final String DEFAULT_CSRF_COOKIE_NAME = "CSRF-TOKEN";

    private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;

    private String headerName = DEFAULT_CSRF_HEADER_NAME;

    private String cookieName = DEFAULT_CSRF_COOKIE_NAME;

    private Random random;

    private Mac mac;

    public StatelessCsrfTokenRepository()
    {
        this(new SecureRandom());
    }

    public StatelessCsrfTokenRepository(Random random)
    {
        this.random = random;
        byte[] passcode = new byte[RANDOM_BYTES];
        random.nextBytes(passcode);
        mac = createMac(passcode);
    }

    /**
     * Sets the {@link HttpServletRequest} parameter name that the {@link CsrfToken} is expected to appear on
     * 
     * @param parameterName the new parameter name to use
     */
    public void setParameterName(String parameterName)
    {
        Assert.hasLength(parameterName, "parameterName cannot be null or empty");
        this.parameterName = parameterName;
    }

    /**
     * Sets the header name that the {@link CsrfToken} is expected to appear on and the header that the response will
     * contain the {@link CsrfToken}.
     *
     * @param headerName the new header name to use
     */
    public void setHeaderName(String headerName)
    {
        Assert.hasLength(headerName, "headerName cannot be null or empty");
        this.headerName = headerName;
    }

    /**
     * Sets the cookie name used to send the {@link CsrfToken} to the client and that will be checked in later requests
     * against header or parameter.
     *
     * @param cookieName the new cookie name to use
     */
    public void setCookieName(String cookieName)
    {
        Assert.hasLength(cookieName, "cookieName cannot be null or empty");
        this.cookieName = cookieName;
    }

    /**
     * Set a passcode for HMAC calculation. This has to be set in clustered environments. Default is a random value.
     * 
     * @param passcode the new passcode to use
     */
    public void setPasscode(String passcode)
    {
        Assert.hasLength(passcode, "passcode cannot be null or empty");
        this.mac = createMac(passcode.getBytes());
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request)
    {
        return new StatelessCsrfToken(headerName, parameterName, createNewToken());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response)
    {
        Cookie cookie = null;
        if (token != null)
        {
            cookie = new Cookie(cookieName, token.getToken());
            cookie.setPath(request.getContextPath());
            cookie.setSecure(request.isSecure());
        }
        else
        {
            cookie = new Cookie(cookieName, null);
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookieName.equals(cookie.getName()))
                {
                    String token = cookie.getValue();
                    if (verifyToken(token))
                    {
                        return new StatelessCsrfToken(headerName, parameterName, token);
                    }
                }
            }
        }

        return null;
    }

    private static Mac createMac(byte[] passcode)
    {
        try
        {
            SecretKeySpec signingKey = new SecretKeySpec(passcode, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return mac;
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new IllegalArgumentException("Problem creating HMAC");
        }
    }

    private boolean verifyToken(String token)
    {
        if (token != null)
        {
            try
            {
                byte[] tokenBytes = Base64.getDecoder().decode(token);
                byte[] hmac = new byte[tokenBytes.length - RANDOM_BYTES];
                System.arraycopy(tokenBytes, RANDOM_BYTES, hmac, 0, hmac.length);
                byte[] calculatedHmac = calculateRFC2104HMAC(Arrays.copyOf(tokenBytes, RANDOM_BYTES));
                return Arrays.equals(hmac, calculatedHmac);
            }
            catch (Exception e)
            {
                LOG.warn("Could not decode CSRF token", e);
                return false;
            }
        }
        return false;
    }

    private String createNewToken()
    {
        byte[] randomBytes = new byte[RANDOM_BYTES];
        random.nextBytes(randomBytes);
        byte[] hmac = calculateRFC2104HMAC(randomBytes);
        byte[] token = Arrays.copyOf(randomBytes, randomBytes.length + hmac.length);
        System.arraycopy(hmac, 0, token, randomBytes.length, hmac.length);
        return Base64.getEncoder().encodeToString(token);
    }

    private byte[] calculateRFC2104HMAC(byte[] data)
    {
        return mac.doFinal(data);
    }
}
