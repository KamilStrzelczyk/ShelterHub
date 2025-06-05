package org.emp.shelterhub.lib.domain;

public class IsValidatePasswordUseCase {

  public Boolean passwordIsValidate(String password) {
    if (password == null) return false; // czy nie jest null
    return password.length() >= 8
        && password.length() <= 12
        && // sprawdza długość
        password.matches(".*\\d.*")
        && // czy ma liczbę
        password.matches(".*[a-z].*")
        && // czy ma małą literę
        password.matches(".*[A-Z].*")
        && // czy ma dużą literę
        password.matches(".*[^a-zA-Z0-9].*"); // czy ma znak specjalny
  }
}
