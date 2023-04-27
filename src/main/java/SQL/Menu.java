package SQL;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public static Scanner sc = new Scanner(System.in);

    public static void menu() {
        boolean continueExecution=true;
        int option;
        while(continueExecution) {
            menuHeader();
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1: {
                    conexion.createFlowerShop();
                    break;
                }
                case 2: {
                    conexion.addproduct();
                    break;
                }
                case 3: {
                    conexion.deleteTree();
                    break;
                }
                case 4: {
                    conexion.deleteFlower();
                    break;
                }
                case 5: {
                    conexion.deleteDecoration();
                    break;
                }
                case 6: {
                    conexion.printStoreStockCategories();
                    break;
                }
                case 7: {
                    conexion.printStoreStockProducts();
                    break;
                }
                case 8: {
                   conexion.printStoreTotalValue();
                    break;
                }
                case 9: {
                    try {
                        conexion.ticketInsert();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 10: {
                    conexion.printSales();
                    break;
                }
                case 11: {
                    conexion.printTotalSales();
                    break;
                }
                case 0: {
                    continueExecution=false;
                    sc.close();
                }
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }
    public static void menuHeader() {
        System.out.println("Choose and option: ");
        System.out.println("1: Create Flower Shop");
        System.out.println("2: Adding Product");
        System.out.println("3: Remove tree");
        System.out.println("4: Remove flower");
        System.out.println("5: Remove decoration");
        System.out.println("6: Printing stock by categories");
        System.out.println("7: Printing stock - full stock");
        System.out.println("8: Printing total stock value");
        System.out.println("9: Generating purchase ticket");
        System.out.println("10: Listing purchase tickets");
        System.out.println("11: Printing total purchases");
        System.out.println("0: Exit");
    };

}
