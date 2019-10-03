package Exchange.Interfaces;

import java.util.List;

public class OrderValidationResult implements IOrderValidationResult {
    IOrder _order;
    boolean _isValid;
    List<String> _errors;

    public OrderValidationResult(IOrder order, boolean isValid, List<String> errors)
    {
        _order = order;
        _isValid = isValid;
        _errors = errors;
    }
    @Override
    public IOrder getOrder() {
        return _order;
    }

    @Override
    public boolean isValid() {
        return _isValid;
    }

    @Override
    public List<String> errors() {
        return _errors;
    }
}