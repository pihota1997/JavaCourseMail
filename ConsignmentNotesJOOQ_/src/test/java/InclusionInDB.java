import DAO.ConsignmentNoteDAO;
import DAO.OrganizationDAO;
import DAO.PositionDAO;
import DAO.ProductDAO;
import commons.JDBCCredentials;
import entity.ConsignmentNote;
import entity.Organization;
import entity.Position;
import entity.Product;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class InclusionInDB {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addAllToDB() {
        addToOrganisations();
        addToProducts();
        addToConsignmentNotes();
        addToPositions();
    }

    private static void addToOrganisations() {
        try {
            Connection connection = getConnection();
            OrganizationDAO dao = new OrganizationDAO(connection);
            dao.create(new Organization("Organization1", "0001", "0000"));
            dao.create(new Organization("Organization2", "0002", "0000"));
            dao.create(new Organization("Organization3", "0003", "0000"));
            dao.create(new Organization("Organization4", "0004", "0000"));
            dao.create(new Organization("Organization5", "0005", "0000"));
            dao.create(new Organization("Organization6", "0006", "0000"));
            dao.create(new Organization("Organization7", "0007", "0000"));
            dao.create(new Organization("Organization8", "0008", "0000"));
            dao.create(new Organization("Organization9", "0009", "0000"));
            dao.create(new Organization("Organization10", "0010", "0000"));
            dao.create(new Organization("Organization11", "0011", "0000"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addToProducts() {
        try {
            Connection connection = getConnection();
            ProductDAO dao = new ProductDAO(connection);
            dao.create(new Product("Product1", "0100"));
            dao.create(new Product("Product2", "0200"));
            dao.create(new Product("Product3", "0300"));
            dao.create(new Product("Product4", "0400"));
            dao.create(new Product("Product5", "0500"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addToConsignmentNotes() {
        try {
            Connection connection = getConnection();
            ConsignmentNoteDAO dao = new ConsignmentNoteDAO(connection);
            dao.create(new ConsignmentNote(1, LocalDate.of(2020, 2, 1), "0001"));
            dao.create(new ConsignmentNote(2, LocalDate.of(2020,3,1), "0001"));
            dao.create(new ConsignmentNote(3, LocalDate.of(2020,5,1), "0001"));
            dao.create(new ConsignmentNote(4, LocalDate.of(2020,2,2), "0002"));
            dao.create(new ConsignmentNote(5, LocalDate.of(2020,2,8), "0002"));
            dao.create(new ConsignmentNote(6, LocalDate.of(2020,2,15), "0002"));
            dao.create(new ConsignmentNote(7, LocalDate.of(2020,10,1), "0003"));
            dao.create(new ConsignmentNote(8, LocalDate.of(2020,2,1), "0003"));
            dao.create(new ConsignmentNote(9, LocalDate.of(2021,2,1), "0004"));
            dao.create(new ConsignmentNote(10, LocalDate.of(2020,8,18), "0005"));
            dao.create(new ConsignmentNote(11, LocalDate.of(2020,8,18), "0006"));
            dao.create(new ConsignmentNote(12, LocalDate.of(2019,10,20), "0007"));
            dao.create(new ConsignmentNote(13, LocalDate.of(2020,8,20), "0008"));
            dao.create(new ConsignmentNote(14, LocalDate.of(2021,8,18), "0009"));
            dao.create(new ConsignmentNote(15, LocalDate.of(2020,1,1), "0011"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addToPositions() {
        try {
            Connection connection = getConnection();
            PositionDAO dao = new PositionDAO(connection);
            dao.create(new Position(1, new BigDecimal(100), "0100", 10, 1));
            dao.create(new Position(2, new BigDecimal(200), "0200", 20, 1));
            dao.create(new Position(3, new BigDecimal(300), "0100", 30, 2));
            dao.create(new Position(4, new BigDecimal(100), "0300", 10, 3));
            dao.create(new Position(5, new BigDecimal(200), "0200", 5, 4));
            dao.create(new Position(6, new BigDecimal(50), "0100", 15, 5));
            dao.create(new Position(7, new BigDecimal(15), "0500", 25, 6));
            dao.create(new Position(8, new BigDecimal(20), "0300", 5, 7));
            dao.create(new Position(9, new BigDecimal(2), "0300", 5, 8));
            dao.create(new Position(10, new BigDecimal(200), "0500", 1, 9));
            dao.create(new Position(11, new BigDecimal(1), "0100", 5, 10));
            dao.create(new Position(12, new BigDecimal(2), "0200", 2, 10));
            dao.create(new Position(13, new BigDecimal(100), "0300", 5, 11));
            dao.create(new Position(14, new BigDecimal(200), "0200", 100, 12));
            dao.create(new Position(15, new BigDecimal(2000), "0100", 50, 13));
            dao.create(new Position(16, new BigDecimal(200), "0200", 5, 14));
            dao.create(new Position(17, new BigDecimal(500), "0500", 3, 15));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
