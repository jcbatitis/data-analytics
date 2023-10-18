package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import main.exceptions.CustomDateTimeParseException;
import main.exceptions.EntityAlreadyExistsException;
import main.exceptions.EntityNotFoundException;
import main.models.Post;
import main.services.PostDao;

public class PostTest {
    private PostDao dao;

    @Before
    public void setup() {
        dao = new PostDao();

        try {
            Post testPost1 = new Post("459", "This is a sample content.", "Albert", 5, 10, "10/10/2010 00:10");
            dao.create(testPost1);
        } catch (CustomDateTimeParseException e) {
        } catch (EntityAlreadyExistsException e) {
        }
    }

    @Test
    public void testPostCreationInvalidId() throws Exception {
        Post testPost1 = new Post("458", "This is a sample content.", "Albert", 5, 10, "10/10/2010 10:10");
        Post testPost2 = new Post("459", "This is a sample content.", "Albert", 5, 10, "10/10/2010 00:10");

        assertAll("Post creation should fail as provided ID is already existing.",
                () -> assertThrows(EntityAlreadyExistsException.class, () -> dao.create(testPost1)),
                () -> assertThrows(EntityAlreadyExistsException.class, () -> dao.create(testPost2)));

    }

    @Test(expected = CustomDateTimeParseException.class)
    public void testPostCreationInvalidDateTime() throws Exception {
        Post testPost1 = new Post("123", "This is a sample content.", "Albert", 5, 10, "10/10/10 10:10");
        Post testPost2 = new Post("123", "This is a sample content.", "Albert", 5, 10, "10/10/2010 24:10");

        assertAll("Post creation should fail as provided date time is invalid format.",
                () -> assertFalse(dao.create(testPost1)),
                () -> assertFalse(dao.create(testPost2)));
    }

    @Test
    public void testDeletePostWithNotExistingPost() {
        String id = "999";
        assertThrows(EntityNotFoundException.class, () -> dao.delete(id),
                "Delete post should return unsucessful as the post does not exist.");
    }

    @Test
    public void testGetPostWithNotExistingPost() {
        String id = "999";
        assertThrows(EntityNotFoundException.class, () -> dao.get(id),
                "Get post details should return unsucessful as the post does not exist.");
    }
}
