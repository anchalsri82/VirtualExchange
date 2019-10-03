package Exchange.Cli;
import Exchange.Interfaces.IOrder;
import Exchange.Interfaces.IOrderValidationResult;
import Exchange.Interfaces.OrderValidationResult;
import Exchange.Interfaces.SideType;
import Exchange.Virtual.Order;
import java.util.ArrayList;
import java.util.List;

//TODO: Write unit test for InputValidator
public class InputValidator {
    public static IOrderValidationResult Validate(String[] inputs)
    {
        IOrder order = null;
        boolean isValid = true;
        SideType side = SideType.Buy;
        long quantity = 0;
        double price = 0.0D;

        List<String> errors = new ArrayList<String>();

        if (inputs == null || inputs.length < 3 || inputs.length > 3) {
            errors.add("Wrong number of arguments, expected 3 arguments in the format [Side] [Quantity] [Price] ");
        }
        else {

            if (inputs[0].isBlank() || inputs[0].isEmpty() ||
                    !(inputs[0].contentEquals("B") || inputs[0].contentEquals("S"))) {
                errors.add(String.format("Side {%s} is blank or invalid", inputs[0]));
            } else {
                side = inputs[0].contentEquals("B") ? SideType.Buy : SideType.Sell;
            }

            if (!Utils.tryParseLong(inputs[1])) {
                errors.add(String.format("Quantity {%s} is blank or invalid", inputs[1]));
            } else {
                quantity = Long.parseLong(inputs[1]);
                if (quantity <= 0)
                    errors.add(String.format("Quantity {%d} should be greater than 0", quantity));
            }

            if (!Utils.tryParseDouble(inputs[2])) {
                errors.add(String.format("Price {%s} is blank or invalid", inputs[2]));
            } else {
                price = Double.parseDouble(inputs[2]);
                if (price <= 0.0D)
                    errors.add(String.format("Price {%d} should be greater than 0", price));
            }
        }
        if(errors.size() == 0) {
            order = new Order(side, quantity, price);
        }
        else {
            isValid = false;
        }

        IOrderValidationResult orderValidationResult =  new OrderValidationResult(order, isValid, errors);
        return orderValidationResult;
    }
}