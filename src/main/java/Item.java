import java.math.BigDecimal;

/**
 * Created by zhanglei53 on 2017/11/3.
 */
public class Item {
    private String orderId;
    private String pin;
    private BigDecimal amount;

    public Item(String orderId, String pin, BigDecimal amount) {
        this.orderId = orderId;
        this.pin = pin;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}