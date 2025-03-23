package org.emp.shelterhub.lib.domain;

public class IsValidatePasswordUseCase {

    Boolean passwordIsValidate(String password) {
        if(password == null) return false;
        return password.length() >= 8 && password.length() <= 12;
    }

}
