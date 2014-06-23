package at.porscheinformatik.tutorial.todo;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @Column(length = 40)
    private String username;

    @Column(length = 60)
    private String password;

    private boolean enabled;

    @ElementCollection
    @Column(name = "AUTHORITY", length = 50)
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USERNAME"))
    private Set<String> authorities;

    public String getUsername()
    {
        return username;
    }

    public boolean isEnabled()
    {
        return enabled;
    }
}
