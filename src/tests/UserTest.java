package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import models.User;
import services.UserDao;

public class UserTest {
    private UserDao dao;

    @Before
    public void setup() {
        dao = new UserDao();

        try {
            User testUser1 = new User("1", "Ruff", "Malt", "ruff", "ruff", true);
            dao.create(testUser1);
        } catch (EntityAlreadyExistsException e) {
        }
    }

    @Test
    public void testCreateUserWithExistingUsername() {
        User testUser1 = new User("123", "Ruff", "Malt", "ruff", "ruff", true);
        assertThrows(EntityAlreadyExistsException.class, () -> dao.create(testUser1),
                "User creation should fail as username used already exists.");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testCheckUserIfValidWithInvalidCredentials() throws EntityNotFoundException {
        String username = "ruff";
        String password = "1234";

        assertInstanceOf(User.class, dao.checkUserIfValid(username, password),
                "Check user if valid should fail as username or password is incorrect.");
    }

    @Test
    public void testCheckUserIfValidWithValidCredentials() throws EntityNotFoundException {
        String username = "ruff";
        String password = "ruff";

        assertInstanceOf(User.class, dao.checkUserIfValid(username, password),
                "Check user if valid should return successful as username is password is valid.");
    }

    @Test
    public void testCheckUserIfExistingWithValidUsername() {
        String username = "ruff";
        assertTrue("Check user if exists should return successful as username is existing.",
                dao.checkUserIfExisting(username));
    }

    @Test
    public void testCheckUserIfExistingWithInvalidUsername() {
        String username = "notexisting";
        assertFalse("Check user if exists should return unsuccessful as username does not exist.",
                dao.checkUserIfExisting(username));
    }

    @Test
    public void testDeleteUserWithNotExistingUser() {
        String id = "999";
        assertThrows(EntityNotFoundException.class, () -> dao.delete(id),
                "Delete user should return unsuccessful as the user does not exist.");
    }

    @Test
    public void testGetUserWithNotExistingUser() {
        String id = "999";
        assertThrows(EntityNotFoundException.class, () -> dao.get(id),
                "Get user details return unsucessful as the user does not exist.");
    }
}
