package Exchange.Interfaces;

import java.util.List;

public interface IOrderValidationResult {
    IOrder getOrder();
    boolean isValid();
    List<String> errors();
}
