package by.klubnikov;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


public class UsersService {

    private List<Users> users;

    public UsersService(List<Users> users) {
        this.users = users;
    }

    public List<Users> getUsers() {
        return users;
    }

    public List<Users> createNewUser(String name, LocalDate dateOfBirth) throws Exception {
        validateUser(name, dateOfBirth);
        Users user = new Users(name, dateOfBirth);
        users.add(user);
        return users;
    }

    public void removeUser(String name) {
        users = users.stream().filter(it -> !it.name.equals(name)).collect(Collectors.toList());
    }

    public boolean isBirthDay(Users user, LocalDate date) throws CustomFieldException {
        if (isNull(user) || isNull(user.dateOfBirth)) {
            throw new CustomFieldException("User or date of birth is null");
        }
        if (isNull(date)) {
            throw new CustomFieldException("Compare date must not be null");
        }
        return date.getDayOfMonth() == user.dateOfBirth.getDayOfMonth() && date.getMonth().equals(user.dateOfBirth.getMonth());
    }

    private void validateUser(String name, LocalDate dateOfBirth) throws Exception {
        if (isNull(name) || name.isBlank()) {
            throw new CustomFieldException("Name could not be empty or null");
        }
        if (isNull(dateOfBirth)) {
            throw new CustomFieldException("Date of birth could not be null");
        }
    }
}

class Users {
    public String name;
    public LocalDate dateOfBirth;

    public Users(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
}

class CustomFieldException extends Exception {
    private String message;

    public CustomFieldException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
