package Exchange.Virtual;
import Exchange.Interfaces.ITradeInfo;
import Exchange.Interfaces.SideType;

public class TradeInfo implements ITradeInfo {
    SideType _side;
    long _quantity;
    double _price;

    public TradeInfo(SideType side, long quantity, double price) {
        _side = side;
        _quantity = quantity;
        _price = price;
    }

    public SideType getSide() {
        return _side;
    }

    @Override
    public long getQuantity() {
        return _quantity;
    }

    @Override
    public double getPrice() {
        return _price;
    }
}
