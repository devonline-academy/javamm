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
 * @link https://junit.org/junit5/docs/current/user-guide/
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class DivBinaryCalculator_UnitTest {

    private final BinaryCalculator calculator = new DivBinaryCalculator();

    @Test
    @Order(1)
    void Should_return_3() {
        assertEquals(3, calculator.calculate(12, 4));
    }

    @Test
    @Order(2)
    void Should_throw_ArithmeticException_if_second_argument_is_0() {
        final ArithmeticException exception = assertThrows(ArithmeticException.class, () ->
                calculator.calculate(12, 0));
        assertEquals("/ by zero", exception.getMessage());
    }
}