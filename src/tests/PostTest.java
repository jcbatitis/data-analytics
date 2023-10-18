package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import exceptions.CustomDateTimeParseException;
import exceptions.EntityNotFoundException;
import models.Post;
import services.PostDao;
import utils.DatabaseUtil;

public class PostTest {
    private Connection connection;
    private PostDao dao;

    @Before
    public void setup() {
        DatabaseUtil db = DatabaseUtil.getInstance();
        connection = db.getConnection();
        dao = new PostDao();
    }

    @Test
    public void testPostCreation() throws Exception {
        Post testPost1 = new Post("123", "This is a sample content.", "Albert", 5, 10, "10/10/10 10:10");
        Post testPost2 = new Post("123", "This is a sample content.", "Albert", 5, 10, "10/10/2010 24:10");

        assertAll(() -> assertFalse(dao.create(testPost1)), () -> assertFalse(dao.create(testPost2)));
    }

    @Test(expected = CustomDateTimeParseException.class)
    public void testPostCreationInvalidDateTime() throws Exception {
        Post testPost1 = new Post("123", "This is a sample content.", "Albert", 5, 10, "10/10/10 10:10");
        Post testPost2 = new Post("123", "This is a sample content.", "Albert", 5, 10, "10/10/2010 24:10");

        assertAll(() -> assertFalse(dao.create(testPost1)), () -> assertFalse(dao.create(testPost2)));
    }
}
