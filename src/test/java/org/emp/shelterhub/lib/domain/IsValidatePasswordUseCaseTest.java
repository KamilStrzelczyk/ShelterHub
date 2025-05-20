package org.emp.shelterhub.lib.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsValidatePasswordUseCaseTest {

    IsValidatePasswordUseCase validationTest = new IsValidatePasswordUseCase();
    @Test
    void passwordIsTooShort() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "te12$";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordIsTooLong() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "test1#231231231";
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
    @Test
    void passwordWithoutLetter() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "1234567#%890";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordWithoutBigLetter() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "asd567#%890";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordWithoutSmallLetter() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "ASD4567#%890";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordWithoutNumber() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "ksnASD*%^asd";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordWithoutSpecialChar() {
        // GIVEN
        Boolean resultExpected = false;
        String password = "kSA124124";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
    @Test
    void passwordIsCorrect() {
        // GIVEN
        Boolean resultExpected = true;
        String password = "Pas$wor4";
        // WHEN
        Boolean result = validationTest.passwordIsValidate(password);
        // THEN
        assertEquals(resultExpected, result);
    }
}