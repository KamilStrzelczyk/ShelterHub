package org.emp.shelterhub.lib.domain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class IsValidateUsernameUseCaseTest {
    IsValidateUsernameUseCase validationTest = new IsValidateUsernameUseCase();
    @Test
    void usernameTooShort() {
        // GIVEN
        Boolean resultExpected = false;
        String username = "";
        // WHEN
        Boolean result = validationTest.usernameIsValidate(username);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void usernameTooLong() {
        // GIVEN
        Boolean resultExpected = false;
        String username = "jasgdfyuasiofhapsieufhpaiusdhfasdgfasdfiasjdfpoispdf";
        // WHEN
        Boolean result = validationTest.usernameIsValidate(username);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void usernameIsNull() {
        // GIVEN
        Boolean resultExpected = false;
        String username = null;
        // WHEN
        Boolean result = validationTest.usernameIsValidate(username);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void usernameIsCorrect() {
        // GIVEN
        Boolean resultExpected = true;
        String username = "Test";
        // WHEN
        Boolean result = validationTest.usernameIsValidate(username);
        // THEN
        assertEquals(resultExpected, result);
    }
}
