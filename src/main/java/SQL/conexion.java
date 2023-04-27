package SQL;
import javax.swing.*;
import java.sql.*;

//connexion server db4free
public class conexion {

    public static String url = "jdbc:mysql://db4free.net:3306/florist_catalog";
    public static String username = "user_prueba";
    public static String password = "prueba1234";

    public static Connection getConection() {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion is fine");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    };
//connexion localhost
//public class conexion {
//
//    public static String url = "jdbc:mysql://localhost:3306/florist_catalog";
//    public static String username = "root";
//    public static String password = "1234";
//
//    public static Connection getConection() {
//
//        Connection connection;
//        try {
//            connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connexion is fine");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return connection;
//    };

    /*Crear Floristeria.*/
    public static void createFlowerShop() {
        try {
            Connection connection = getConection();
            String nameStore = JOptionPane.showInputDialog("Introduce your name of flower store");
            String query = "INSERT INTO store (name) VALUES ('" + nameStore + "')";
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Store created successfully");
            } else {
                System.out.println("Store creation failed");
            }

            // Update total_value column
            String updateQuery = "UPDATE store SET total_value = total_value + 0.00 WHERE id = LAST_INSERT_ID()";
            statement.executeUpdate(updateQuery);

            connection.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };

    public static void addproduct()  {
        String type = JOptionPane.showInputDialog("Introduce type : Tree,Flower or Decoration");

        switch (type) {
            case "Tree":
                addTree();
                break;
            case "Flower":
                addFlower();
                break;
            case "Decoration":
                addDecoration();
                break;
            default:
                System.out.println("Product is not right");
        }
    };

    //store updater function

        public static void updateStore(Connection conn) throws SQLException {
            String storeUpdate = "UPDATE store \n" +
                    "SET total_value = (\n" +
                    "    SELECT SUM(price*quantity) AS value_sum \n" +
                    "    FROM (\n" +
                    "        SELECT price, quantity FROM flowers\n" +
                    "        UNION ALL\n" +
                    "        SELECT price, quantity FROM trees\n" +
                    "        UNION ALL\n" +
                    "        SELECT price, quantity FROM decorations\n" +
                    "    ) AS product_qty\n" +
                    ")\n" +
                    "WHERE id = 1;";

            Statement statement = conn.createStatement();
            statement.executeUpdate(storeUpdate);
        };



    public static void addTree() {
        try {
            Connection connection = getConection();
            //Transaction
            connection.setAutoCommit(false);

            double height = Double.parseDouble(JOptionPane.showInputDialog("Introduce height of the tree"));
            String name = JOptionPane.showInputDialog("Introduce type of the tree");
            double price = Double.parseDouble(JOptionPane.showInputDialog("Introduce price of the tree"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Introduce quantity of the trees"));

            // Insert a row into the product table
            String productQuery = "INSERT INTO product () VALUES ();";
            Statement productStatement = connection.createStatement();
            productStatement.executeUpdate(productQuery, Statement.RETURN_GENERATED_KEYS);

            // Get the auto-generated idproduct value
            ResultSet productResultSet = productStatement.getGeneratedKeys();
            int productId = 0;
            if (productResultSet.next()) {
                productId = productResultSet.getInt(1);
            }
            Statement statement = connection.createStatement();
            // Insert a row into the trees table with the productId value
            String treeQuery = "INSERT INTO trees (height, name, price, quantity, product_idproduct) VALUES (" + height + ", '" + name + "', " + price + ", " + quantity + ", " + productId + ")";
            statement.executeUpdate(treeQuery);
            //update store value
            updateStore(connection);
            connection.commit();

            int rowsAffected = statement.executeUpdate(treeQuery);
            //
            if (rowsAffected > 0) {
                System.out.println("Tree created successfully");
            } else {
                System.out.println("Tree creation failed");
            }

            connection.close();
            productStatement.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };


    public static void addFlower() {
        try {
            Connection connection = getConection();
            //Transaction
            connection.setAutoCommit(false);
            String color = JOptionPane.showInputDialog("Introduce color of the flower");
            String name = JOptionPane.showInputDialog("Introduce type of the flower");
            double price = Double.parseDouble(JOptionPane.showInputDialog("Introduce price of the flower"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Introduce quantity of the flowers"));

            // Insert a row into the product table
            String productQuery = "INSERT INTO product () VALUES ();";
            Statement productStatement = connection.createStatement();
            productStatement.executeUpdate(productQuery, Statement.RETURN_GENERATED_KEYS);

            // Get the auto-generated idproduct value
            ResultSet productResultSet = productStatement.getGeneratedKeys();
            int productId = 0;
            if (productResultSet.next()) {
                productId = productResultSet.getInt(1);
            }

            String query = "INSERT INTO flowers (color, name, price, quantity,product_idproduct) VALUES ('" + color + "', '" + name + "', " + price + ", " + quantity + ", " + productId + ")";
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            //update store value
            updateStore(connection);
            connection.commit();

            if (rowsAffected > 0) {
                System.out.println("Flower created successfully");
            } else {
                System.out.println("Flower creation failed");
            }

            connection.close();
            productStatement.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };


    public enum Material {
        WOOD, PLASTIC
    };

    public static void addDecoration() {
        try {
            Connection connection = getConection();
            //Transaction
            connection.setAutoCommit(false);
            Material material = Material.valueOf(JOptionPane.showInputDialog("Introduce material (WOOD or PLASTIC)"));
            String name = JOptionPane.showInputDialog("Introduce type of the decoration");
            double price = Double.parseDouble(JOptionPane.showInputDialog("Introduce price of the decoration"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Introduce quantity of the decoration items"));

            // Insert a row into the product table
            String productQuery = "INSERT INTO product () VALUES ();";
            Statement productStatement = connection.createStatement();
            productStatement.executeUpdate(productQuery, Statement.RETURN_GENERATED_KEYS);

            // Get the auto-generated idproduct value
            ResultSet productResultSet = productStatement.getGeneratedKeys();
            int productId = 0;
            if (productResultSet.next()) {
                productId = productResultSet.getInt(1);
            }

            String query = "INSERT INTO decorations (material, name, price, quantity,product_idproduct ) VALUES ('" + material.name() + "', '" + name + "', " + price + ", " + quantity + ", " + productId + ")";
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            //update store value
            updateStore(connection);
            connection.commit();

            if (rowsAffected > 0) {
                System.out.println("Decoration created successfully");
            } else {
                System.out.println("Decoration creation failed");
            }

            connection.close();
            productStatement.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };

    public static void printStoreStock() {
        try {
            Connection connection = getConection();
            Statement statement = connection.createStatement();
            String selectSql = "SELECT trees.name, trees.price, trees.quantity, store.id AS Shop " +
                    "FROM trees " +
                    "INNER JOIN store ON store.id = trees.store_id " +
                    "UNION " +
                    "SELECT flowers.name, flowers.price, flowers.quantity, store.id AS Shop " +
                    "FROM flowers " +
                    "INNER JOIN store ON store.id = flowers.store_id " +
                    "UNION " +
                    "SELECT decorations.name, decorations.price, decorations.quantity, store.id AS Shop " +
                    "FROM decorations " +
                    "INNER JOIN store ON store.id = decorations.store_id";
            ResultSet resultSet = statement.executeQuery(selectSql);
            System.out.println("Total stock in the store ");
            // Get column titles
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i)+"|");
            }
            System.out.println();

            // Print data
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " | " +
                        resultSet.getString(2) + " | " +
                        resultSet.getString(3) + " | " +
                        resultSet.getString(4)+ " | ");
            }
            // Close all
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTree() {
        try {
            Connection connection = getConection();

            int id = Integer.parseInt(JOptionPane.showInputDialog("Introduce ID to delete"));
            String query = "DELETE FROM trees WHERE id = "+id;
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Tree deleted successfully");
            } else {
                System.out.println("Tree deleted failed");
            }

            //update store value
            updateStore(connection);

            connection.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };

    public static void deleteFlower() {
        try {
            Connection connection = getConection();

            int id = Integer.parseInt(JOptionPane.showInputDialog("Introduce ID to delete"));
            String query = "DELETE FROM flowers WHERE id = "+id;
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Flower deleted successfully");
            } else {
                System.out.println("Flower deleted failed");
            }

            //update store value
            updateStore(connection);
            connection.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };

    public static void deleteDecoration() {
        try {
            Connection connection = getConection();

            int id = Integer.parseInt(JOptionPane.showInputDialog("Introduce ID to delete"));
            String query = "DELETE FROM decorations WHERE id = "+id;
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Decoration deleted successfully");
            } else {
                System.out.println("Decoration deleted failed");
            }
            //update store value
            updateStore(connection);
            connection.close();
            statement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };

    public static void printStoreStockCategories() {
        try {
            Connection connection = getConection();
            Statement statement = connection.createStatement();
            String selectSql = "SELECT " +
                    "    'Trees' AS product_category, " +
                    "    SUM(quantity) AS stock_quantity " +
                    "FROM " +
                    "    `trees` " +
                    "UNION ALL " +
                    "SELECT " +
                    "    'Decorations' AS product_category, " +
                    "    SUM(quantity) AS stock_quantity " +
                    "FROM " +
                    "    `decorations` " +
                    "UNION ALL " +
                    "SELECT " +
                    "    'Flowers' AS product_category, " +
                    "    SUM(quantity) AS stock_quantity " +
                    "FROM " +
                    "    `flowers`;";
            ResultSet resultSet = statement.executeQuery(selectSql);
            System.out.println("Total stock in the store by categories : ");
            // Get column titles
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i)+"|");
            }
            System.out.println();

            // Print data
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " | " +
                        resultSet.getString(2) + " | ");
            }
            // Close all
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public static void printStoreStockProducts() {
        try {
            Connection connection = getConection();
            Statement statement = connection.createStatement();
            String selectSql = "SELECT p.idproduct,COALESCE(SUM(t.price * t.quantity), 0) AS trees_total,COALESCE(SUM(d.price * d.quantity), 0) AS decorations_total,COALESCE(SUM(f.price * f.quantity), 0) AS flowers_total\n" +
                    "FROM store s\n" +
                    "INNER JOIN product p ON s.id = p.store_id\n" +
                    "LEFT JOIN trees t ON p.idproduct = t.product_idproduct\n" +
                    "LEFT JOIN decorations d ON p.idproduct = d.product_idproduct\n" +
                    "LEFT JOIN flowers f ON p.idproduct = f.product_idproduct\n" +
                    "GROUP BY p.idproduct;";
            ResultSet resultSet = statement.executeQuery(selectSql);
            System.out.println("Total stock in the store by categories : ");
            // Get column titles
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i)+"|");
            }
            System.out.println();

            // Print data
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "        | " +
                        resultSet.getString(2) + "      | "+
                        resultSet.getString(3) + "        | "+
                        resultSet.getString(4) + "     | ");
            }
            // Close all
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
    public static void printStoreTotalValue() {
        try {
            Connection connection = getConection();
            Statement statement = connection.createStatement();
            String selectSql = "SELECT * FROM store;";
            ResultSet resultSet = statement.executeQuery(selectSql);
            System.out.println("Total stock value in the store  : ");

            // Print data
            while (resultSet.next()) {
                System.out.println(resultSet.getString(3));
            }
            // Close all
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

//Resultado X 4
    public static void ticketInsert() throws SQLException {

        try {
            Connection connection = getConection();
            //Transaction
            connection.setAutoCommit(false);

            // input data from user
            int store_id = Integer.parseInt(JOptionPane.showInputDialog("Enter store ID:"));
            boolean continueAddingProducts = true;

            while (continueAddingProducts) {
                int product_idproduct = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID (or enter 0 to stop adding products):"));

                if (product_idproduct == 0) {
                    continueAddingProducts = false;
                    break;
                }

                int productQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter product quantity:"));
                //compare with quantity from trees,decorations,flowers
                Statement stmt = connection.createStatement();

                ResultSet treesResult = stmt.executeQuery("SELECT quantity FROM trees");
                if (treesResult.next() && productQuantity > treesResult.getInt("quantity")) {
                    System.out.println("Product is not enough");
                    return;
                }

                ResultSet flowersResult = stmt.executeQuery("SELECT quantity FROM flowers");
                if (flowersResult.next() && productQuantity > flowersResult.getInt("quantity")) {
                    System.out.println("Product is not enough");
                    return;
                }

                ResultSet decorationsResult = stmt.executeQuery("SELECT quantity FROM decorations");
                if (decorationsResult.next() && productQuantity > decorationsResult.getInt("quantity")) {
                    System.out.println("Product is not enough");
                    return;
                }

                // If the productQuantity is less than or equal to the quantities in all tables, continue the program
                System.out.println("Product is available");

                // Prepare SQL statement
                String sql = "INSERT INTO Ticket (product_idproduct, productQuantity, store_id) VALUES (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, product_idproduct);
                ps.setInt(2, productQuantity);
                ps.setInt(3, store_id);

                // Execute SQL statement
                int rowsInserted = ps.executeUpdate();

                //update store value
                Statement statement = connection.createStatement();
                String TicketSales = "UPDATE Ticket\n" +
                        "SET SalesTickets = (\n" +
                        "  SELECT productQuantity * price\n" +
                        "  FROM trees\n" +
                        "  WHERE trees.product_idproduct = Ticket.product_idproduct\n" +
                        "  UNION ALL\n" +
                        "  SELECT productQuantity * price\n" +
                        "  FROM flowers\n" +
                        "  WHERE flowers.product_idproduct = Ticket.product_idproduct\n" +
                        "  UNION ALL\n" +
                        "  SELECT productQuantity * price\n" +
                        "  FROM decorations\n" +
                        "  WHERE decorations.product_idproduct = Ticket.product_idproduct\n" +
                        ");";
                statement.executeUpdate(TicketSales);
                connection.commit();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Ticket inserted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to insert ticket.");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    };



    public static void printSales() {
        try {
            Connection connection = getConection();
            Statement statement = connection.createStatement();
            String selectSql = "SELECT * FROM Ticket;";
            ResultSet resultSet = statement.executeQuery(selectSql);
            System.out.println("Sales from tickets : ");
            // Get column titles
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i)+ "|");
            }
            System.out.println();

            // Print data
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "     |      " +
                        resultSet.getString(2) + "    |    "+
                        resultSet.getString(3) + "     |  "+
                        resultSet.getString(4) + "         |"+
                        resultSet.getString(5) + "   ");
            }
            // Close all
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
    public static void printTotalSales() {
        try {
            Connection connection = getConection();
            Statement statement = connection.createStatement();
            String selectSql = "SELECT SUM(SalesTickets) FROM Ticket;";
            ResultSet resultSet = statement.executeQuery(selectSql);
            System.out.println("Total Sales from tickets : ");
            // Get column titles
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i));
            }
            System.out.println();

            // Print data
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) );
            }
            // Close all
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };




}



