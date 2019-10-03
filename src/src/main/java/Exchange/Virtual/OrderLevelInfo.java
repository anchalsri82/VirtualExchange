package Exchange.Virtual;
import Exchange.Interfaces.*;

public class OrderLevelInfo implements IOrderLevelInfo {

    long _quantity = 0;
    double _price = 0.0d;

    public OrderLevelInfo(long quantity, double price) {
        this._quantity = quantity;
        this._price = price;
    }
    public OrderLevelInfo(OrderLevelInfo copyFrom) {
        this._quantity = copyFrom.getQuantity();
        this._price = copyFrom.getPrice();
    }

    @Override
    public long getQuantity() {
        return _quantity;
    }

    @Override
    public double getPrice() { return _price; }
}
