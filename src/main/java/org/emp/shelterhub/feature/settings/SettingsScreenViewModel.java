package org.emp.shelterhub.feature.settings;

import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.feature.settings.data.UserRole;
import org.emp.shelterhub.lib.domain.IsValidatePasswordUseCase; // Poprawny import dla walidacji

// hasła

public class SettingsScreenViewModel {

  private User currentUser;
  private IsValidatePasswordUseCase validatePasswordUseCase;

  public SettingsScreenViewModel() {
    currentUser =
        new User(
            "user123",
            "adminUser",
            "admin@shelterhub.com",
            "111-222-333",
            UserRole.ADMINISTRATOR,
            "hashed_password_123");
    validatePasswordUseCase = new IsValidatePasswordUseCase();
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public boolean updatePassword(String currentPassword, String newPassword) {
    // Walidacja nowego hasła za pomocą IsValidatePasswordUseCase
    if (!validatePasswordUseCase.passwordIsValidate(newPassword)) {
      System.out.println(
          "Nowe hasło nie spełnia wymagań walidacji: musi mieć od 8 do 12 znaków, zawierać cyfrę, małą literę, dużą literę i znak specjalny.");
      return false;
    }

    if (("hashed_" + currentPassword).equals(currentUser.getPasswordHash())) {
      currentUser.setPasswordHash("hashed_" + newPassword);
      System.out.println(
          "Hasło dla użytkownika " + currentUser.getUsername() + " zostało zmienione.");
      return true;
    } else {
      System.out.println(
          "Niepoprawne obecne hasło dla użytkownika " + currentUser.getUsername() + ".");
      return false;
    }
  }

  public void updateUserDetails(String email, String phoneNumber) {
    currentUser.setEmail(email);
    currentUser.setPhoneNumber(phoneNumber);
    System.out.println(
        "Dane użytkownika " + currentUser.getUsername() + " zostały zaktualizowane.");
  }

  public void deleteAccount() {
    System.out.println("Konto użytkownika " + currentUser.getUsername() + " zostało usunięte.");
  }
}
