package Exchange.Interfaces;

public interface ITradeInfo {
    public double getPrice();
    public long getQuantity();
    public SideType getSide();
}
