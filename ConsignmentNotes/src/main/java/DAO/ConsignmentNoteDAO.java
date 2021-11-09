package DAO;

import commons.DAO;
import entity.ConsignmentNote;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsignmentNoteDAO implements DAO<ConsignmentNote> {

    private final @NotNull Connection connection;

    public ConsignmentNoteDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull ConsignmentNote get(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT No, date, organization " +
                "FROM consignment_notes " +
                "WHERE No = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new ConsignmentNote(resultSet.getInt("no"),
                        resultSet.getDate("date"),
                        resultSet.getString("organization"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with No " + id + " not found");
    }

    @Override
    public @NotNull List<ConsignmentNote> all() {
        final List<ConsignmentNote> all = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM consignment_notes")) {
                while (resultSet.next()) {
                    all.add(new ConsignmentNote(resultSet.getInt("no"),
                            resultSet.getDate("date"),
                            resultSet.getString("organization")));
                }
                return all;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    @Override
    public void create(@NotNull ConsignmentNote entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO consignment_notes(no, date, organization) " +
                "VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, entity.getNo());
            preparedStatement.setDate(2, entity.getDate());
            preparedStatement.setString(3, entity.getOrganizationTIN());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull ConsignmentNote entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE consignment_notes " +
                "SET date = ?, organization = ? " +
                "WHERE No = ?")) {
            preparedStatement.setDate(1, entity.getDate());
            preparedStatement.setString(2, entity.getOrganizationTIN());
            preparedStatement.setInt(3, entity.getNo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull ConsignmentNote entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM consignment_notes " +
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
