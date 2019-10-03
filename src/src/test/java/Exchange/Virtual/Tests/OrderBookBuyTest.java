package Exchange.Virtual.Tests;
import Exchange.Interfaces.*;
import Exchange.Virtual.*;
import junit.framework.Assert;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class OrderBookBuyTest {
    IOrderBook orderBook;
    HashMap<Double, IOrderLevelInfo> buyMap;
    HashMap<Double, IOrderLevelInfo> sellMap;

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderLtBestAskQuantityPriceLtBestAsk() throws Exception {
        orderBook = new OrderBook(buyMap, sellMap);
        IOrder order = new Order(SideType.Buy, 99, 12.5D);
        var trades = orderBook.NewOrder(order);

        Assert.assertEquals(0, trades.size());
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderLeBestAskQuantityPriceEqBestAsk() throws Exception {
        orderBook = new OrderBook(buyMap, sellMap);
        IOrder order = new Order(SideType.Buy, 99, 13.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(99, trade.getQuantity());
            Assert.assertEquals(13.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderEqBestAskQuantityPriceEqBestAsk() throws Exception {
        IOrder order = new Order(SideType.Buy, 100, 13.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(100, trade.getQuantity());
            Assert.assertEquals(13.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderGrBestAskQuantityPriceEqBestAsk() throws Exception {
        IOrder order = new Order(SideType.Buy, 101, 13.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(100, trade.getQuantity());
            Assert.assertEquals(13.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderEqBestAskQuantityPriceEqBestAsk2() throws Exception {
        IOrder order = new Order(SideType.Buy, 100, 14.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(100, trade.getQuantity());
            Assert.assertEquals(13.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderGrBestAskQuantityPriceEqBestAsk2() throws Exception {
        IOrder order = new Order(SideType.Buy, 101, 14.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(2, trades.size());
        if(trades.size() >=2) {
            var trade1 = trades.get(0);
            Assert.assertEquals(100, trade1.getQuantity());
            Assert.assertEquals(13.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(1, trade2.getQuantity());
            Assert.assertEquals(14.0D, trade2.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderGrBestAsk2QuantityPriceEqBestAsk2() throws Exception {
        IOrder order = new Order(SideType.Buy, 301, 14.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(2, trades.size());
        if(trades.size() >=2) {
            var trade1 = trades.get(0);
            Assert.assertEquals(100, trade1.getQuantity());
            Assert.assertEquals(13.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(200, trade2.getQuantity());
            Assert.assertEquals(14.0D, trade2.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderGrBestAsk2QuantityPriceEqBestAsk3() throws Exception {
        IOrder order = new Order(SideType.Buy, 301, 15.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(3, trades.size());
        if(trades.size() >=3) {
            var trade1 = trades.get(0);
            Assert.assertEquals(100, trade1.getQuantity());
            Assert.assertEquals(13.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(200, trade2.getQuantity());
            Assert.assertEquals(14.0D, trade2.getPrice());
            var trade3 = trades.get(2);
            Assert.assertEquals(1, trade3.getQuantity());
            Assert.assertEquals(15.0D, trade3.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewBuyOrderGrBestAsk3QuantityPriceEqBestAsk3() throws Exception {
        IOrder order = new Order(SideType.Buy, 601, 15.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(3, trades.size());
        if(trades.size() >=3) {
            var trade1 = trades.get(0);
            Assert.assertEquals(100, trade1.getQuantity());
            Assert.assertEquals(13.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(200, trade2.getQuantity());
            Assert.assertEquals(14.0D, trade2.getPrice());
            var trade3 = trades.get(2);
            Assert.assertEquals(300, trade3.getQuantity());
            Assert.assertEquals(15.0D, trade3.getPrice());
        }
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        buyMap = new HashMap<Double, IOrderLevelInfo>();
        sellMap = new HashMap<Double, IOrderLevelInfo>();

        buyMap.put(10D, new OrderLevelInfo(100, 10D));
        buyMap.put(11D, new OrderLevelInfo(200, 11D));
        buyMap.put(12D, new OrderLevelInfo(300, 12D));

        sellMap.put(13D, new OrderLevelInfo(100, 13D));
        sellMap.put(14D, new OrderLevelInfo(200, 14D));
        sellMap.put(15D, new OrderLevelInfo(300, 15D));

        orderBook = new OrderBook(buyMap, sellMap);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
}