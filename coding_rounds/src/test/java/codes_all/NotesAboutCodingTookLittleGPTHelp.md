BigDecimal for all money + mileage math (no floating-point bugs)

To run from maven - 
mvn test

To run from ide-
Right click → Run TestNG Test

Why Testng
TestNG is good for:
✔ Data-driven tests
✔ Dependency-based tests
✔ Parallel execution
✔ Flexible configuration
Later, you could easily extend this to:
@DataProvider for multiple rate periods
Boundary testing
Negative tests
Regression suites

Why Map as return type -
because we want output in the format-
{
  "valid": true,
  "rate": 0.435,
  "total_amount": 21.75
}

When valid is false-
Instead of throwing exceptions, the function should:
✔ Catch validation errors
✔ Return a consistent object
✔ Set "valid": false
✔ Include an "error" message

setScale(2, RoundingMode.HALF_UP)
setScale(2)
→ Keep 2 digits after the decimal point
example, 5.1 becomes 5.10
RoundingMode.HALF_UP
→ Tells Java how to round when extra digits exist
RoundingMode.HALF_UP means rounding up to next digit
example, 5.124 -> 5.12
5.125 -> 5.13

Without rounding-
value.setScale(2);
and rounding is needed →  Exception!
ArithmeticException: Rounding necessary
Java forces you to choose how to round.

doubleValue() is a method of BigDecimal that:
Converts a BigDecimal into a primitive double.
Example-
BigDecimal bd = new BigDecimal("0.435");

double d = bd.doubleValue();
d == 0.435   // true (approximately)

why we used it -
output in json format.
JSON numbers are usually represented as double
Map<String, Object> can store Double
So this makes output look like:
{
  "valid": true,
  "rate": 0.435,
  "total_amount": 21.75
}
instead of 
{
  "rate": "0.435"
}

doubleValue() Can Lose Precision
example, double d=0.1 can become 0.1111110 etc

BigDecimal wont lose precision

Not mandatory to convert BigDecimal to double.

If you want zero floating-point risk, you can keep BigDecimal in the result:
result.put("rate", rate);
result.put("total_amount", totalAmount);
{
  "rate": 0.435,
  "total_amount": 21.75
}

One liner answer from gpt to summarise-
“I used BigDecimal for precision, handled validation gracefully with structured responses, and added TestNG coverage including boundary and invalid cases.”