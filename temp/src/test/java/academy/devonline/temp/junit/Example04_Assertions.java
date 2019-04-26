package academy.devonline.temp.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Disabled
class Example04_Assertions {

    @Test
    void simple_assertions() {
        assertEquals(2, 2);
        assertEquals(0.222, 0.222, 0.000001);
        assertEquals("hello", "hello");
        assertEquals(List.of(1, 2, 3), Arrays.asList(1, 2, 3));
        assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});

        assertNotEquals(2, 3);

        assertSame(Boolean.TRUE, Boolean.parseBoolean("true"));
        assertNotSame(Integer.MAX_VALUE, Integer.valueOf(Integer.MAX_VALUE));

        assertNull(Map.of().get("hello"));
        assertNotNull(Map.of().getOrDefault("hello", "default"));

        assertTrue(Boolean.parseBoolean("true"));
        assertFalse(Boolean.parseBoolean("false"));

        assertFalse(Boolean.parseBoolean("true"), "Custom error message");
    }

    @Test
    void test_exceptions() {
        final ArithmeticException exception = assertThrows(ArithmeticException.class, () -> System.out.println(2 / 0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void manual_fail() {
        fail("Custom fail");
    }

    @Test
    void manual_error() {
        throw new RuntimeException("Custom exception");
    }
}
