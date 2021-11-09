package commons;

import entity.Organization;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entity.Product;
import org.jetbrains.annotations.NotNull;

public class Functional {

    private final @NotNull Connection connection;

    public Functional(@NotNull Connection connection) {
        this.connection = connection;
    }

    public List<Organization> selectTenOrganizationsThatDeliveredMoreProducts() {
        final List<Organization> organizationLists = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT organizations.name, organizations.tin, organizations.current_account " +
                        "FROM positions " +
                        "INNER  JOIN consignment_notes ON positions.consignment_note_No = consignment_notes.No " +
                        "INNER JOIN organizations ON consignment_notes.organization = organizations.tin " +
                        "GROUP BY organizations.tin " +
                        "ORDER BY SUM(quantity) DESC " +
                        "LIMIT 10")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                organizationLists.add(new Organization(resultSet.getString("name"),
                        resultSet.getString("tin"),
                        resultSet.getString("current_account")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return organizationLists;
    }

    public Map<Organization, Double> selectOrganizationsBySelectedProducts(Map<Product, Integer> list) {
        final Map<Organization, Double> result = new HashMap<>();
        list.forEach((product, quantity) -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT organizations.name, organizations.tin, organizations.current_account, " +
                            "SUM(positions.quantity) AS total_quantity, " +
                            "ROUND (SUM(positions.price * positions.quantity), 2) AS sum " +
                            "FROM products " +
                            "INNER JOIN positions ON products.internal_code = positions.product " +
                            "INNER JOIN consignment_notes ON positions.consignment_note_No = consignment_notes.No " +
                            "INNER JOIN organizations ON consignment_notes.organization = organizations.tin " +
                            "GROUP BY organizations.tin, positions.product " +
                            "HAVING positions.product = ? AND SUM(positions.quantity) > ?")) {
                preparedStatement.setString(1, product.getInternalCode());
                preparedStatement.setInt(2, quantity);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Organization organization = new Organization(resultSet.getString("name"),
                            resultSet.getString("tin"),
                            resultSet.getString("current_account"));
                    Double sum = resultSet.getDouble("sum");
                    if (result.containsKey(organization)) {
                        result.replace(organization, result.get(organization) + sum);
                    } else {
                        result.put(organization, sum);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        return result;
    }

    public StringBuilder calculateProductQuantityAndSumForPeriod(Date from, Date to) {
        StringBuilder result = new StringBuilder();
        double totalPriceForPeriod = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT consignment_notes.date, products.name, products.internal_code, SUM(positions.quantity) AS total_amount, " +
                        "ROUND(SUM(positions.price * positions.quantity), 2) AS total_price " +
                        "FROM products " +
                        "INNER JOIN positions ON products.internal_code = positions.product " +
                        "INNER JOIN consignment_notes ON positions.consignment_note_No = consignment_notes.No " +
                        "WHERE consignment_notes.date BETWEEN ? AND ?" +
                        "GROUP BY date, products.internal_code ")) {
            preparedStatement.setDate(1, from);
            preparedStatement.setDate(2, to);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.append(resultSet.getDate("date") + " "
                        + new Product(resultSet.getString("name"), resultSet.getString("internal_code"))
                        + " " + resultSet.getInt("total_amount") + " "
                        + resultSet.getDouble("total_price") + "\n");
                totalPriceForPeriod += resultSet.getDouble("total_price");
            }
            result.append("Total sum for the period " + from + " " + to + ": " + totalPriceForPeriod);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public StringBuilder calculateAveragePriceForPeriod(Date from, Date to) {
        StringBuilder result = new StringBuilder();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT products.name, products.internal_code, " +
                        "ROUND(AVG(positions.price), 2) AS average_price " +
                        "FROM products " +
                        "INNER JOIN positions ON products.internal_code = positions.product " +
                        "INNER JOIN consignment_notes ON positions.consignment_note_No = consignment_notes.No " +
                        "WHERE consignment_notes.date BETWEEN ? AND ?" +
                        "GROUP BY products.internal_code ")) {
            preparedStatement.setDate(1, from);
            preparedStatement.setDate(2, to);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.append(new Product(resultSet.getString("name"), resultSet.getString("internal_code"))
                        + " " + resultSet.getDouble("average_price") + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public StringBuilder selectProductsListSuppliedByOrganizations(Date from, Date to) {
        StringBuilder result = new StringBuilder();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT organizations.name AS organization, organizations.tin, " +
                        "organizations.current_account, products.name AS product, " +
                        "products.internal_code FROM organizations LEFT JOIN consignment_notes ON " +
                        "organizations.tin = consignment_notes.organization " +
                        "AND consignment_notes.date BETWEEN ? AND ? " +
                        "LEFT JOIN positions ON consignment_notes.no = positions.consignment_note_No " +
                        "LEFT JOIN products ON positions.product = products.internal_code " +
                        "GROUP BY organizations.tin, products.internal_code " +
                        "ORDER BY organizations.tin")) {
            preparedStatement.setDate(1, from);
            preparedStatement.setDate(2, to);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.append(new Product(resultSet.getString("product"),
                        resultSet.getString("internal_code"))
                        + " " + (new Organization(resultSet.getString("organization"),
                        resultSet.getString("tin"), resultSet.getString("current_account"))) + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
