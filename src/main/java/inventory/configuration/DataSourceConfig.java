package inventory.configuration;

import inventory.credentials.DatabaseCredentials;
import inventory.credentials.SecretsManagerUtil;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
  @Bean
  public DataSource dataSource() {
    DatabaseCredentials credentials = SecretsManagerUtil.getDatabaseCredentials();

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl(credentials.getUrl());
    dataSource.setUsername(credentials.getUsername());
    dataSource.setPassword(credentials.getPassword());

    return dataSource;
  }
}
