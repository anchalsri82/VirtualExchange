package Exchange.Cli;
import Exchange.Interfaces.IOrderLevelInfo;
import Exchange.Interfaces.IOrderValidationResult;
import Exchange.Virtual.OrderBook;

import java.util.Scanner;
import java.util.SortedMap;

//TODO: Write unit test for CliHandler
public class CliHandler {
    public static void main(String[] args) throws Exception {
        Scanner s1 = new Scanner(System.in);
        String input;
        var orderBook = new OrderBook();
        do {
            System.out.println("\n\nEnter an order in format [Side] [Quantity] [Price] : ");
            input = s1.nextLine();
            String[] inputs = input.split(" ");
            IOrderValidationResult orderValidationResult = InputValidator.Validate(inputs);

            if(orderValidationResult != null && orderValidationResult.isValid()
                    && orderValidationResult.getOrder() != null ) {
                var trades = orderBook.NewOrder(orderValidationResult.getOrder());

                if(trades.size()>0) {
                    System.out.println("Trades in response to last order:");
                    for (var trade : trades) {
                        System.out.println(String.format("%d@%.3f", trade.getQuantity(), trade.getPrice()));
                    }
                }

                System.out.println("\t\t\t\tSell Orders");
                SortedMap<Double, IOrderLevelInfo> sellBook  = orderBook.GetSellBook();
                printBook(sellBook, "\t\t\t\t", true);

                System.out.println("\tBuy Orders");
                var buyBook = orderBook.GetBuyBook();
                printBook(buyBook, "\t", false);
            }
            else if(orderValidationResult != null && !orderValidationResult.isValid() && orderValidationResult.errors() != null ) {
                System.out.println("Errors during validation:");
                for(var error : orderValidationResult.errors()) {
                    System.out.println(error);
                }
            }
            else
            {
                System.out.println("Errors during validation:");
            }
        } while (!input.equalsIgnoreCase("exit"));
    }

    private static void printBook(SortedMap<Double, IOrderLevelInfo> book, String prefix, boolean isReversePrint)
    {
        if(book.size() > 0) {
            int start = 0;
            int end = book.size() - 1;
            if (isReversePrint) {
                int temp = start;
                start = end;
                end = temp;
            }
            var values = book.values().toArray();
            for (int i = start; isReversePrint ? i >= end : i <= end; ) {
                IOrderLevelInfo value = (IOrderLevelInfo) values[i];
                var message = String.format("%s %d@%.3f", prefix, value.getQuantity(), value.getPrice());
                System.out.println(message);
                if(isReversePrint)
                    --i;
                else
                    ++i;
            }
        }
    }
}