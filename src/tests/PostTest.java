package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class PostTest {
    @BeforeEach
    void setup() throws Exception {
    }

    @Test
    public void testValidPost() {
        String expectedResults = "Test";

        String test1 = "Test";
        String test2 = "Test";

        assertAll("test2",
                () -> assertEquals(expectedResults, test1),
                () -> assertEquals(expectedResults, test2));
    }

    @Test
    public void testValidIndividualPost() {
        String expectedResults = "Test";

        String test1 = "Test";
        String test2 = "Test";

       assertEquals(expectedResults, test1);
    }
}
