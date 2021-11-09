package DAO;

import commons.DAO;
import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {
    private final @NotNull Connection connection;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }


    @Override
    public @NotNull Product get(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, internal_code " +
                "FROM products " +
                "WHERE internal_code = ?")) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Product(resultSet.getString("name"),
                        resultSet.getString("internal_code"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with internal_code " + id + " not found");
    }

    @Override
    public @NotNull List<Product> all() {
        final List<Product> all = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {
                while (resultSet.next()) {
                    all.add(new Product(resultSet.getString("name"),
                            resultSet.getString("internal_code")));
                }
                return all;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    @Override
    public void create(@NotNull Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products(name, internal_code) " +
                "VALUES (?, ?)")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getInternalCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products " +
                "SET name = ?" +
                "WHERE internal_code = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getInternalCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products " +
                "WHERE internal_code = ?")) {
            preparedStatement.setString(1, entity.getInternalCode());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with internal_code = " + entity.getInternalCode() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
