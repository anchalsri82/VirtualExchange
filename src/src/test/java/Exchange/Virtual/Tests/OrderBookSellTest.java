package Exchange.Virtual.Tests;

import Exchange.Interfaces.IOrder;
import Exchange.Interfaces.IOrderBook;
import Exchange.Interfaces.IOrderLevelInfo;
import Exchange.Interfaces.SideType;
import Exchange.Virtual.Order;
import Exchange.Virtual.OrderBook;
import Exchange.Virtual.OrderLevelInfo;
import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookSellTest {
    IOrderBook orderBook;
    HashMap<Double, IOrderLevelInfo> buyMap;
    HashMap<Double, IOrderLevelInfo> sellMap;

    @org.junit.jupiter.api.Test
    void TestNewSellOrderLtBestBidQuantityPriceLtBestBid() throws Exception {
        orderBook = new OrderBook(buyMap, sellMap);
        IOrder order = new Order(SideType.Sell, 99, 12.5D);
        var trades = orderBook.NewOrder(order);

        Assert.assertEquals(0, trades.size());
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderLeBestBidQuantityPriceEqBestBid() throws Exception {
        orderBook = new OrderBook(buyMap, sellMap);
        IOrder order = new Order(SideType.Sell, 299, 12.0D);
        var trades = orderBook.NewOrder(order);
        /*for(var trade: trades) {
            System.out.println(String.format("Executed %g@%g",
                    trade.getQuantity(), trade.getPrice()));
        }*/
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(299, trade.getQuantity());
            Assert.assertEquals(12.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderEqBestBidQuantityPriceEqBestBid() throws Exception {
        IOrder order = new Order(SideType.Sell, 300, 12.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(300, trade.getQuantity());
            Assert.assertEquals(12.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderGrBestBidQuantityPriceEqBestBid() throws Exception {
        IOrder order = new Order(SideType.Sell, 301, 12.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(300, trade.getQuantity());
            Assert.assertEquals(12.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderEqBestBidQuantityPriceEqBestBid2() throws Exception {
        IOrder order = new Order(SideType.Sell, 300, 11.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(1, trades.size());
        if(trades.size() >=1) {
            var trade = trades.get(0);
            Assert.assertEquals(300, trade.getQuantity());
            Assert.assertEquals(12.0D, trade.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderGrBestBidQuantityPriceEqBestBid2() throws Exception {
        IOrder order = new Order(SideType.Sell, 301, 11.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(2, trades.size());
        if(trades.size() >=2) {
            var trade1 = trades.get(0);
            Assert.assertEquals(300, trade1.getQuantity());
            Assert.assertEquals(12.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(1, trade2.getQuantity());
            Assert.assertEquals(11.0D, trade2.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderGrBestBid2QuantityPriceEqBestBid2() throws Exception {
        IOrder order = new Order(SideType.Sell, 501, 11.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(2, trades.size());
        if(trades.size() >=2) {
            var trade1 = trades.get(0);
            Assert.assertEquals(300, trade1.getQuantity());
            Assert.assertEquals(12.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(200, trade2.getQuantity());
            Assert.assertEquals(11.0D, trade2.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderGrBestBid2QuantityPriceEqBestBid3() throws Exception {
        IOrder order = new Order(SideType.Sell, 501, 10.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(3, trades.size());
        if(trades.size() >=3) {
            var trade1 = trades.get(0);
            Assert.assertEquals(300, trade1.getQuantity());
            Assert.assertEquals(12.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(200, trade2.getQuantity());
            Assert.assertEquals(11.0D, trade2.getPrice());
            var trade3 = trades.get(2);
            Assert.assertEquals(1, trade3.getQuantity());
            Assert.assertEquals(10.0D, trade3.getPrice());
        }
    }

    @org.junit.jupiter.api.Test
    void TestNewSellOrderGrBestBid3QuantityPriceEqBestBid3() throws Exception {
        IOrder order = new Order(SideType.Sell, 601, 10.0D);
        var trades = orderBook.NewOrder(order);
        Assert.assertEquals(3, trades.size());
        if(trades.size() >=3) {
            var trade1 = trades.get(0);
            Assert.assertEquals(300, trade1.getQuantity());
            Assert.assertEquals(12.0D, trade1.getPrice());
            var trade2 = trades.get(1);
            Assert.assertEquals(200, trade2.getQuantity());
            Assert.assertEquals(11.0D, trade2.getPrice());
            var trade3 = trades.get(2);
            Assert.assertEquals(100, trade3.getQuantity());
            Assert.assertEquals(10.0D, trade3.getPrice());
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