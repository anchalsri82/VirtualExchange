package Exchange.Interfaces;
import java.util.List;
import java.util.SortedMap;

//TODO: We should split this class into IOrderHandler and IOrderBook
public interface IOrderBook {
    List<ITradeInfo> NewOrder (IOrder order) throws Exception;
    SortedMap<Double, IOrderLevelInfo> GetBuyBook();
    SortedMap<Double, IOrderLevelInfo> GetSellBook();
}
