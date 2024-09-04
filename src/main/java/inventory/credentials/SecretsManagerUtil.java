package inventory.credentials;

import org.json.JSONObject;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretsManagerUtil {

  private static final String SECRET_NAME = "inventoryDB";
  private static final Region REGION = Region.of("eu-west-3");

  public static DatabaseCredentials getDatabaseCredentials() {

    SecretsManagerClient client = SecretsManagerClient.builder().region(REGION).build();

    GetSecretValueRequest getSecretValueRequest =
        GetSecretValueRequest.builder().secretId(SECRET_NAME).build();

    GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

    String secret = getSecretValueResponse.secretString();

    JSONObject secretJson = new JSONObject(secret);

    String username = secretJson.getString("username");
    String password = secretJson.getString("password");
    String engine = secretJson.getString("engine");
    String host = secretJson.getString("host");
    int port = secretJson.getInt("port");

    String url = "jdbc:" + engine + "://" + host + ":" + port + "/inventory";

    return new DatabaseCredentials(username, password, url);
  }
}
