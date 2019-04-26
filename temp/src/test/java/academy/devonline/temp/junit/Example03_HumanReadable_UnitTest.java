package academy.devonline.temp.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class Example03_HumanReadable_UnitTest {

    @Test
    @Order(1)
    void Should_return_3() {
        assertEquals(3, 3);
    }

    @Test
    @Order(2)
    void Should_throw_ArithmeticException() {
        final ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            int a = 12 / 0;
        });
        assertEquals("/ by zero", exception.getMessage());
    }
}
