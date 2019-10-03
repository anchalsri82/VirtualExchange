package Exchange.Virtual;
import Exchange.Interfaces.*;

public class Order implements IOrder {

	private SideType _side;
	private long _quantity;
	private double _price;

	public Order(SideType side, long quantity, double price) {
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
