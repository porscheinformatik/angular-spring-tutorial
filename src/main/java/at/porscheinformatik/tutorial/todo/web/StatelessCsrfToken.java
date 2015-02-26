package at.porscheinformatik.tutorial.todo.web;

import org.springframework.security.web.csrf.CsrfToken;

public class StatelessCsrfToken implements CsrfToken
{
    private static final long serialVersionUID = -702818028369308706L;

    private final String headerName;

    private final String parameterName;

    private final String token;

    public StatelessCsrfToken(String headerName, String parameterName, String token)
    {
        super();
        this.token = token;
        this.parameterName = parameterName;
        this.headerName = headerName;
    }

    @Override
    public String getParameterName()
    {
        return parameterName;
    }

    @Override
    public String getHeaderName()
    {
        return headerName;
    }

    @Override
    public String getToken()
    {
        return token;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((headerName == null) ? 0 : headerName.hashCode());
        result = prime * result + ((parameterName == null) ? 0 : parameterName.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatelessCsrfToken other = (StatelessCsrfToken) obj;
        if (headerName == null)
        {
            if (other.headerName != null)
                return false;
        }
        else if (!headerName.equals(other.headerName))
            return false;
        if (parameterName == null)
        {
            if (other.parameterName != null)
                return false;
        }
        else if (!parameterName.equals(other.parameterName))
            return false;
        if (token == null)
        {
            if (other.token != null)
                return false;
        }
        else if (!token.equals(other.token))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "StatelessCsrfToken [headerName=" + headerName + ", parameterName=" + parameterName + ", token=" + token
            + "]";
    }

}
