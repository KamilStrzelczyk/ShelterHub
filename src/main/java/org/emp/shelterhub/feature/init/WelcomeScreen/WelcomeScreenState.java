package org.emp.shelterhub.feature.init.WelcomeScreen;

import org.emp.shelterhub.feature.settings.data.User;

public class WelcomeScreenState {
  public final boolean isLoading;
  public final User user;
  public final String errorMessage;

  public WelcomeScreenState(boolean isLoading, User user, String errorMessage) {
    this.isLoading = isLoading;
    this.user = user;
    this.errorMessage = errorMessage;
  }

  public static WelcomeScreenState initialState() {
    return new WelcomeScreenState(false, null, null);
  }

  public WelcomeScreenState withLoading(boolean loading) {
    return new WelcomeScreenState(loading, this.user, this.errorMessage);
  }

  public WelcomeScreenState withUser(User user) {
    return new WelcomeScreenState(this.isLoading, user, this.errorMessage);
  }

  public WelcomeScreenState withErrorMessage(String errorMessage) {
    return new WelcomeScreenState(this.isLoading, this.user, errorMessage);
  }
}
