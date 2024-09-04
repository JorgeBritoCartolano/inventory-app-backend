package inventory.credentials;

public class DatabaseCredentials {

  private final String username;
  private final String password;
  private final String url;

  public DatabaseCredentials(String username, String password, String url) {
    this.username = username;
    this.password = password;
    this.url = url;
  }

  // Getters
  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getUrl() {
    return url;
  }
}
