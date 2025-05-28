package org.emp.shelterhub.feature.settings;

import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.lib.domain.IsValidatePasswordUseCase;
import org.emp.shelterhub.lib.infrastructure.repository.UserRepository;

public class SettingsScreenViewModel {

  private User currentUser = UserRepository.getInstance().getLoggedUser();
  private IsValidatePasswordUseCase validatePasswordUseCase = new IsValidatePasswordUseCase();

  public User getCurrentUser() {
    return currentUser;
  }

  public boolean updatePassword(String currentPassword, String newPassword) {
    if (!validatePasswordUseCase.passwordIsValidate(newPassword)) {
      System.out.println(
          "Nowe hasło nie spełnia wymagań walidacji: musi mieć od 8 do 12 znaków, zawierać cyfrę, małą literę, dużą literę i znak specjalny.");
      return false;
    }

    if (currentPassword.equals(currentUser.getPassword())) {
      currentUser.setPassword(newPassword);
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
