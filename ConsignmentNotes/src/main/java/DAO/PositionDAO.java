package DAO;

import commons.DAO;
import entity.ConsignmentNote;
import entity.Position;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements DAO<Position> {
    private final @NotNull Connection connection;

    public PositionDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Position get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT No, price, product, quantity, consignment_note_No " +
                        "FROM positions " +
                        "WHERE No = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Position(resultSet.getInt("no"),
                        resultSet.getDouble("price"),
                        resultSet.getString("product"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("consignment_note_no"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with No " + id + " not found");
    }

    @Override
    public @NotNull List<Position> all() {
        final List<Position> all = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM positions")) {
                while (resultSet.next()) {
                    all.add(new Position(resultSet.getInt("no"),
                            resultSet.getDouble("price"),
                            resultSet.getString("product"),
                            resultSet.getInt("quantity"),
                            resultSet.getInt("consignment_note_no")));
                }
                return all;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    @Override
    public void create(@NotNull Position entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO positions(price, product, quantity, consignment_note_No) " +
                "VALUES (?, ?, ?, ?)")) {
            preparedStatement.setDouble(1, entity.getPrice());
            preparedStatement.setString(2, entity.getProduct());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.setInt(4, entity.getConsignmentNoteNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Position entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE positions " +
                "SET price = ?, product = ?, quantity = ?, consignment_note_No = ?" +
                "WHERE No = ?")) {
            preparedStatement.setDouble(1, entity.getPrice());
            preparedStatement.setString(2, entity.getProduct());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.setInt(4, entity.getConsignmentNoteNo());
            preparedStatement.setInt(5, entity.getNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull Position entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM positions " +
                "WHERE No = ?")) {
            preparedStatement.setInt(1, entity.getNo());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with No = " + entity.getNo() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
