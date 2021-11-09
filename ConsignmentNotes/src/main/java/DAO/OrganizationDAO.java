package DAO;

import commons.DAO;
import entity.Organization;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class OrganizationDAO implements DAO<Organization> {

    private final @NotNull Connection connection;

    public OrganizationDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Organization get(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, TIN, current_account " +
                "FROM organizations " +
                "WHERE TIN = ?")) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Organization(resultSet.getString("name"),
                        resultSet.getString("tin"),
                        resultSet.getString("current_account"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with TIN " + id + " not found");
    }

    @Override
    public @NotNull List<Organization> all() {
        final List<Organization> all = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM organizations")) {
                while (resultSet.next()) {
                    all.add(new Organization(resultSet.getString("name"),
                            resultSet.getString("tin"),
                            resultSet.getString("current_account")));
                }
                return all;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    @Override
    public void create(@NotNull Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO organizations(name, TIN, current_account) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getTIN());
            preparedStatement.setString(3, entity.getCurrentAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE organizations " +
                "SET name = ?, current_account = ? " +
                "WHERE TIN = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getCurrentAccount());
            preparedStatement.setString(3, entity.getTIN());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM organizations " +
                "WHERE TIN = ?")) {
            preparedStatement.setString(1, entity.getTIN());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with No = " + entity.getTIN() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
