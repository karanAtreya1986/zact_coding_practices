package codes_all;

	
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class MileageExpenseCalculatorTest {

    /* ---------------- VALID TESTS ---------------- */

    @Test
    public void shouldCalculateExpenseBeforeOct2025() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("50"),
                        LocalDate.of(2025, 10, 15),
                        List.of("Alice")
                );

        assertTrue((Boolean) result.get("valid"));

        assertEquals(result.get("rate"), 0.435);
        assertEquals(result.get("total_amount"), 21.75);
    }

    @Test
    public void shouldCalculateExpenseForNov2025() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10"),
                        LocalDate.of(2025, 11, 10),
                        List.of("Bob")
                );

        assertTrue((Boolean) result.get("valid"));

        assertEquals(result.get("rate"), 0.367);
        assertEquals(result.get("total_amount"), 3.67);
    }

    @Test
    public void shouldRoundToTwoDecimals() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10.55"),
                        LocalDate.of(2026, 3, 5),
                        List.of("Carol")
                );

        assertTrue((Boolean) result.get("valid"));

        // 10.55 × 0.491 = 5.17805 → 5.18
        assertEquals(result.get("total_amount"), 5.18);
    }

    /* ---------------- INVALID: MILES ---------------- */

    @Test
    public void shouldFailWhenMilesIsZero() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        BigDecimal.ZERO,
                        LocalDate.now(),
                        List.of("Alice")
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(result.get("error"), "Miles must be greater than 0");
    }

    @Test
    public void shouldFailWhenMilesIsNegative() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("-5"),
                        LocalDate.now(),
                        List.of("Bob")
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(result.get("error"), "Miles must be greater than 0");
    }

    @Test
    public void shouldFailWhenMilesHasTooManyDecimals() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10.123"),
                        LocalDate.now(),
                        List.of("Alice")
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(
                result.get("error"),
                "Miles may have at most 2 decimal places"
        );
    }

    /* ---------------- INVALID: ATTENDEES ---------------- */

    @Test
    public void shouldFailWhenNoAttendees() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10"),
                        LocalDate.now(),
                        List.of()
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(
                result.get("error"),
                "At least one attendee is required"
        );
    }

    @Test
    public void shouldFailWhenTooManyAttendees() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10"),
                        LocalDate.now(),
                        List.of("A", "B", "C", "D", "E", "F")
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(
                result.get("error"),
                "Maximum 5 attendees allowed"
        );
    }

    /* ---------------- INVALID: NULL INPUTS ---------------- */

    @Test
    public void shouldFailWhenMilesIsNull() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        null,
                        LocalDate.now(),
                        List.of("Alice")
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(result.get("error"), "Miles must not be null");
    }

    @Test
    public void shouldFailWhenDateIsNull() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10"),
                        null,
                        List.of("Alice")
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(
                result.get("error"),
                "Expense date must not be null"
        );
    }

    @Test
    public void shouldFailWhenAttendeesIsNull() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("10"),
                        LocalDate.now(),
                        null
                );

        assertFalse((Boolean) result.get("valid"));
        assertEquals(
                result.get("error"),
                "Attendees must not be null"
        );
    }

    /* ---------------- BOUNDARY DATE TEST ---------------- */

    @Test
    public void shouldUseCorrectRateOnBoundaryDate() {

        Map<String, Object> result =
                MileageExpenseCalculator.mileage_expense(
                        new BigDecimal("1"),
                        LocalDate.of(2025, 11, 30),
                        List.of("Alice")
                );

        assertTrue((Boolean) result.get("valid"));

        assertEquals(result.get("rate"), 0.367);
        assertEquals(result.get("total_amount"), 0.37);
    }
}