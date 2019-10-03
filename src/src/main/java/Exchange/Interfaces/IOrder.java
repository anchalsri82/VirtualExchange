package Exchange.Interfaces;

public interface IOrder {
    public SideType getSide();
    public long getQuantity();
    public double getPrice();
}
