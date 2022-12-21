package by.klubnikov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceTest {

    private static final String EMPTY_OR_NULL_NAME_ERROR_MESSAGE = "Name could not be empty or null";
    private static final String NULLABLE_BIRTHDAY_OR_USER_ERROR_MESSAGE = "User or date of birth is null";
    private static final String NULLABLE_DATE_ERROR_MESSAGE = "Compare date must not be null";
    private static final String NULLABLE_BIRTHDAY_DATE_ERROR_MESSAGE = "Date of birth could not be null";
    private static final String VALID_NAME = "Alex";
    private static final LocalDate VALID_BIRTHDAY_DATE = LocalDate.parse("1989-02-04");
    private static final LocalDate ANOTHER_DATE = LocalDate.parse("1991-12-16");
    private static final String ANOTHER_NAME = "Ann";
    private final Users VALID_USER = new Users(VALID_NAME, VALID_BIRTHDAY_DATE);

    private UsersService usersService = null;

    @BeforeEach
    public void initializeUsersService() {
        usersService = new UsersService(new ArrayList<>());
    }

    @Test
    void testIsBirthdayWithNullableUser() {
        CustomFieldException exception = assertThrows(CustomFieldException.class, () ->
                usersService.isBirthDay(null, VALID_BIRTHDAY_DATE));
        assertEquals(NULLABLE_BIRTHDAY_OR_USER_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testIsBirthdayWithUsersNullableBirthday() {
        Users nullDateUser = new Users(VALID_NAME, null);
        CustomFieldException exception = assertThrows(CustomFieldException.class, () ->
                usersService.isBirthDay(nullDateUser, VALID_BIRTHDAY_DATE));

        assertEquals(NULLABLE_BIRTHDAY_OR_USER_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testIsBirthdayWithNullableDate() {
        CustomFieldException exception = assertThrows(CustomFieldException.class, () -> 
            usersService.isBirthDay(VALID_USER, null));

        assertEquals(NULLABLE_DATE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testIsBirthdayTrue() throws CustomFieldException {
        assertTrue(usersService.isBirthDay(VALID_USER, VALID_BIRTHDAY_DATE));
    }

    @Test
    void testIsBirthdayFalse() throws CustomFieldException {
        assertFalse(usersService.isBirthDay(VALID_USER, ANOTHER_DATE));
    }

    @Test
    void testCreateNewUserWithNullableName() {
        CustomFieldException exception = assertThrows(CustomFieldException.class, () ->
                usersService.createNewUser(null, VALID_BIRTHDAY_DATE));
        assertEquals(EMPTY_OR_NULL_NAME_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testCreateNewUserWithEmptyName() {
        CustomFieldException exception = assertThrows(CustomFieldException.class, () ->
                usersService.createNewUser("", VALID_BIRTHDAY_DATE));
        assertEquals(EMPTY_OR_NULL_NAME_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testCreateNewUserWithNullableBirthday() {
        CustomFieldException exception = assertThrows(CustomFieldException.class, () ->
                usersService.createNewUser(VALID_NAME, null));
        assertEquals(NULLABLE_BIRTHDAY_DATE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testCreateNewUser() throws Exception {
        usersService.createNewUser(VALID_NAME, VALID_BIRTHDAY_DATE);
        assertEquals(1, usersService.getUsers().size());
        assertTrue(usersService.getUsers().contains(VALID_USER));
    }

    @Test
    void testRemoveUser() throws Exception {
        usersService.createNewUser(VALID_NAME, VALID_BIRTHDAY_DATE);
        usersService.createNewUser(ANOTHER_NAME, ANOTHER_DATE);
        usersService.removeUser(VALID_NAME);
        assertEquals(1, usersService.getUsers().size());
        assertFalse(usersService.getUsers().contains(VALID_USER));
    }
}
