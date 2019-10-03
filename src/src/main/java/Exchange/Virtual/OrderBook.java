package Exchange.Virtual;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import Exchange.Interfaces.*;
import java.util.*;

public final class OrderBook implements IOrderBook {

    //sortedMap.ceilingKey(28d)

    SortedMap<Double, IOrderLevelInfo> _buyBook = new TreeMap<Double, IOrderLevelInfo>(Collections.reverseOrder());
    SortedMap<Double, IOrderLevelInfo> _sellBook = new TreeMap<Double, IOrderLevelInfo>();

    HashMap<Double, IOrderLevelInfo> _buyMap;
    HashMap<Double, IOrderLevelInfo> _sellMap;

    public OrderBook(HashMap<Double, IOrderLevelInfo> buyMap,
            HashMap<Double, IOrderLevelInfo> sellMap)
    {
        _buyMap = buyMap;
        _sellMap = sellMap;
        Utils.ConvertToTreeMap((Map<Double, IOrderLevelInfo>)_buyMap, (Map<Double, IOrderLevelInfo>)_buyBook);
        Utils.ConvertToTreeMap((Map<Double, IOrderLevelInfo>)_sellMap, (Map<Double, IOrderLevelInfo>)_sellBook);
    }
    public OrderBook()
    {
        _buyMap = new HashMap<Double, IOrderLevelInfo>();
        _sellMap = new HashMap<Double, IOrderLevelInfo>();
    }

    @Override
    public List<ITradeInfo> NewOrder(IOrder order) throws Exception {
        //TODO: Use command pattern here
        var side = order.getSide();
        if (side.equals(SideType.Buy)) {
            return NewBuyOrder(order);
        } else if (side.equals(SideType.Sell)) {
           return NewSellOrder(order);
        }
        throw new Exception(String.format("Unsupported Side Type : {0}", side));
    }

    @Override
    public SortedMap<Double, IOrderLevelInfo> GetBuyBook() {
        //TODO: Implement deep copy
        return _buyBook;
    }

    @Override
    public SortedMap<Double, IOrderLevelInfo> GetSellBook() {
        //TODO: Implement deep copy
        return _sellBook;
    }

    private List<ITradeInfo> NewSellOrder(IOrder order) {
        List<ITradeInfo> result = new ArrayList<ITradeInfo>();
        var orderPrice = order.getPrice();
        long orderQuantity = order.getQuantity();

        if (_sellMap.containsKey(orderPrice)) {
            var levelInfo =  _sellMap.get(orderPrice);
            var newQuantity = order.getQuantity() + levelInfo.getQuantity();
            update(newQuantity, orderPrice, _sellMap, _sellBook);
        } else if (!_buyBook.isEmpty()) {
            long pendingQuantity = order.getQuantity();
            double bestBidPrice = (double) _buyBook.firstKey();

            while (bestBidPrice >= orderPrice && pendingQuantity > 0) {
                var bestBid = _buyBook.get(bestBidPrice);
                long bestBidQuantity = bestBid.getQuantity();
                long tradeQuantity = 0;
                double tradePrice = bestBidPrice;

                if (pendingQuantity > bestBidQuantity) {
                    tradeQuantity = bestBidQuantity;
                    pendingQuantity -= bestBidQuantity;

                    remove(bestBidPrice, _buyMap, _buyBook);
                } else {
                    tradeQuantity = pendingQuantity;
                    pendingQuantity = 0;
                    long newQuantity = bestBidQuantity - tradeQuantity;
                    update(newQuantity, tradePrice, _buyMap, _buyBook);
                }
                result.add(new TradeInfo(SideType.Sell, tradeQuantity, bestBidPrice));
                if (!_buyBook.isEmpty()) {
                    bestBidPrice = _buyBook.firstKey();
                } else
                    break;
            }
            if(pendingQuantity > 0)
            {
                add(pendingQuantity, orderPrice, _sellMap, _sellBook);
            }
        }
        else
        {
            add(orderQuantity, orderPrice, _sellMap, _sellBook);
        }
        return result;
    }

    private List<ITradeInfo> NewBuyOrder(IOrder order) {
        List<ITradeInfo> trades = new ArrayList<ITradeInfo>();
        var orderPrice = order.getPrice();
        long orderQuantity = order.getQuantity();

        if (_buyMap.containsKey(orderPrice)) {
            var levelInfo = _buyMap.get(orderPrice);
            var newQuantity = order.getQuantity() + levelInfo.getQuantity();
            update(newQuantity, orderPrice, _buyMap, _buyBook);
        } else if (!_sellBook.isEmpty()) {
            var bestAskPrice = _sellBook.firstKey();
            long pendingQuantity = order.getQuantity();

            while (bestAskPrice <= orderPrice && pendingQuantity > 0) {
                var bestAsk = _sellBook.get(bestAskPrice);
                long bestAskQuantity = bestAsk.getQuantity();
                long tradeQuantity = 0;

                if (pendingQuantity > bestAskQuantity) {
                    tradeQuantity =  bestAskQuantity;
                    pendingQuantity -= bestAskQuantity;
                    remove(bestAskPrice, _sellMap, _sellBook);
                } else {
                    tradeQuantity =  pendingQuantity;
                    pendingQuantity = 0;
                    var newQuantity = bestAskQuantity - tradeQuantity;
                    update(newQuantity, orderPrice, _sellMap, _sellBook);
                }
                trades.add(new TradeInfo(SideType.Buy, tradeQuantity, bestAskPrice));
                if (!_sellBook.isEmpty())
                    bestAskPrice = _sellBook.firstKey();
                else
                    break;
            }
            if(pendingQuantity > 0)
                add(pendingQuantity, orderPrice, _buyMap, _buyBook);
        }
        else {
            add(orderQuantity, orderPrice, _buyMap, _buyBook);
        }
        return trades;
    }
    void remove(double price, HashMap<Double, IOrderLevelInfo> map,
                SortedMap<Double, IOrderLevelInfo> book) {
        book.remove(price);
        map.remove(price);
    }
    void update(long quantity, double price, HashMap<Double, IOrderLevelInfo> map,
                SortedMap<Double, IOrderLevelInfo> book) {
        remove(price, map, book);
        add(quantity, price, map, book);
    }
    void add(long quantity, double price, HashMap<Double, IOrderLevelInfo> map,
             SortedMap<Double, IOrderLevelInfo> book) {
        IOrderLevelInfo orderLevelInfo = new OrderLevelInfo(quantity, price);
        book.put(price, orderLevelInfo);
        map.put(price, orderLevelInfo);
    }
}