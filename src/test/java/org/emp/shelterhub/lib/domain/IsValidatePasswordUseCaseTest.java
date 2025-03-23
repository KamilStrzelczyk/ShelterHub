package org.emp.shelterhub.lib.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsValidatePasswordUseCaseTest {

    IsValidatePasswordUseCase validationTest = new IsValidatePasswordUseCase();
    @Test
    void passwordIsTooShort() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "test";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordIsTooLong() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "test1231231231";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordIsNull() {
        // GIVEN
        Boolean resultExpected = false;
        String password = null;
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
}