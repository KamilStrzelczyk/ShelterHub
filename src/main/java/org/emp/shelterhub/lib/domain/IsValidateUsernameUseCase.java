package org.emp.shelterhub.lib.domain;

public class IsValidateUsernameUseCase {
  Boolean usernameIsValidate(String username) {
    if (username == null) return false; // czy nie jest null
    return !username.isEmpty()
        && // nie jest puste
        username.length() <= 16; // sprawdza długość
  }
}
