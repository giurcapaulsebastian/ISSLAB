package domainDTO;

import java.io.Serializable;

public class User implements IHasId<Integer>, Serializable {
    private Integer idUser;
    private String username;
    private String password;

    public User(Integer idUser, String username, String password) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Integer getId() { return this.idUser; }

    @Override
    public void setId(Integer integer) { this.idUser = integer; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
