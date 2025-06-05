package org.emp.shelterhub.feature.about;

import java.io.IOException;
import java.util.Properties;
import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.lib.infrastructure.repository.UserRepository;

public class AboutScreenViewModel {
  private AboutScreenState state;

  public AboutScreenViewModel() {
    String version = loadVersionFromProperties();
    this.state = AboutScreenState.initial(version);
    loadUserData();
  }

  public AboutScreenState getState() {
    return state;
  }

  private void loadUserData() {
    User user = UserRepository.getInstance().getLoggedUser();

    if (user != null) {
      state = state.withUsernameAndRole(user.getUsername(), user.getUserRole().getDisplayName());
    } else {
      state = state.withUsernameAndRole("", "Nieznany");
    }
  }

  private String loadVersionFromProperties() {
    Properties props = new Properties();
    try (var input = getClass().getResourceAsStream("/version.properties")) {
      if (input != null) {
        props.load(input);
        return props.getProperty("app.version", "0.0.1");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "0.0.1";
  }
}
