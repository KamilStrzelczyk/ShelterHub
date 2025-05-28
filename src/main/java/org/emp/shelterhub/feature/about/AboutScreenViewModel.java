package org.emp.shelterhub.feature.about;

import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.lib.infrastructure.repository.UserRepository;

public class AboutScreenViewModel {
  private AboutScreenState state;

  public AboutScreenViewModel() {
    this.state = AboutScreenState.initial();
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
}
