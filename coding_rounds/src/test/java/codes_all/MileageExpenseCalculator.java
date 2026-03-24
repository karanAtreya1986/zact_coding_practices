package codes_all;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MileageExpenseCalculator {

    public static Map<String, Object> mileage_expense(
            BigDecimal miles,
            LocalDate expenseDate,
            List<String> attendees
    ) {

        Map<String, Object> result = new HashMap<>();

        try {
            validateMiles(miles);
            validateDate(expenseDate);
            validateAttendees(attendees);

            BigDecimal rate = getRateForDate(expenseDate);

            BigDecimal totalAmount = miles
                    .multiply(rate)
                    .setScale(2, RoundingMode.HALF_UP);

            result.put("valid", true);
            result.put("rate", rate.doubleValue());
            result.put("total_amount", totalAmount.doubleValue());
            
            //print the map values
            System.out.println(result);

        } catch (IllegalArgumentException e) {

            result.put("valid", false);
            result.put("error", e.getMessage());
            
            //print the map values
            System.out.println(result);
        }

        return result;
    }

    /* ---------------- Validation ---------------- */

    private static void validateMiles(BigDecimal miles) {

        if (miles == null) {
            throw new IllegalArgumentException("Miles must not be null");
        }

        if (miles.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Miles must be greater than 0");
        }

        if (miles.scale() > 2) {
            throw new IllegalArgumentException("Miles may have at most 2 decimal places");
        }
    }

    private static void validateDate(LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException("Expense date must not be null");
        }
    }

    private static void validateAttendees(List<String> attendees) {

        if (attendees == null) {
            throw new IllegalArgumentException("Attendees must not be null");
        }

        if (attendees.isEmpty()) {
            throw new IllegalArgumentException("At least one attendee is required");
        }

        if (attendees.size() > 5) {
            throw new IllegalArgumentException("Maximum 5 attendees allowed");
        }
    }

    /* ---------------- Rate Logic ---------------- */

    private static BigDecimal getRateForDate(LocalDate date) {

        if (!date.isAfter(LocalDate.of(2025, 10, 31))) {
            return new BigDecimal("0.435");
        }

        if (!date.isBefore(LocalDate.of(2025, 11, 1)) &&
            !date.isAfter(LocalDate.of(2025, 11, 30))) {
            return new BigDecimal("0.367");
        }

        if (!date.isBefore(LocalDate.of(2025, 12, 1)) &&
            !date.isAfter(LocalDate.of(2025, 12, 31))) {
            return new BigDecimal("0.423");
        }

        if (!date.isBefore(LocalDate.of(2026, 1, 1)) &&
            !date.isAfter(LocalDate.of(2026, 1, 30))) {
            return new BigDecimal("0.460");
        }

        return new BigDecimal("0.491");
    }
}