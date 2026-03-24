package codes_all;
	
	import java.math.BigDecimal;

	public class MileageExpenseResult {

	    private final boolean valid;
	    private final BigDecimal rate;
	    private final BigDecimal totalAmount;

	    public MileageExpenseResult(boolean valid, BigDecimal rate, BigDecimal totalAmount) {
	        this.valid = valid;
	        this.rate = rate;
	        this.totalAmount = totalAmount;
	    }

	    public boolean isValid() {
	        return valid;
	    }

	    public BigDecimal getRate() {
	        return rate;
	    }

	    public BigDecimal getTotalAmount() {
	        return totalAmount;
	    }
	}

