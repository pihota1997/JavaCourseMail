import DAO.ConsignmentNoteDAO;
import DAO.OrganizationDAO;
import DAO.PositionDAO;
import DAO.ProductDAO;
import commons.JDBCCredentials;
import generated.tables.records.ConsignmentNotesRecord;
import generated.tables.records.OrganizationsRecord;
import generated.tables.records.PositionsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class InclusionInDB {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword());
    }

    public static void addAllToDB() {
        addToOrganisations();
        addToProducts();
        addToConsignmentNotes();
        addToPositions();
    }

    private static void addToOrganisations() {
        try {
            @NotNull Connection connection = getConnection();
            OrganizationDAO dao = new OrganizationDAO(connection);
            dao.create(new OrganizationsRecord("Organization1", "0001", "0000"));
            dao.create(new OrganizationsRecord("Organization2", "0002", "0000"));
            dao.create(new OrganizationsRecord("Organization3", "0003", "0000"));
            dao.create(new OrganizationsRecord("Organization4", "0004", "0000"));
            dao.create(new OrganizationsRecord("Organization5", "0005", "0000"));
            dao.create(new OrganizationsRecord("Organization6", "0006", "0000"));
            dao.create(new OrganizationsRecord("Organization7", "0007", "0000"));
            dao.create(new OrganizationsRecord("Organization8", "0008", "0000"));
            dao.create(new OrganizationsRecord("Organization9", "0009", "0000"));
            dao.create(new OrganizationsRecord("Organization10", "0010", "0000"));
            dao.create(new OrganizationsRecord("Organization11", "0011", "0000"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addToProducts() {
        try {
            Connection connection = getConnection();
            ProductDAO dao = new ProductDAO(connection);
            dao.create(new ProductsRecord("Product1", "0100"));
            dao.create(new ProductsRecord("Product2", "0200"));
            dao.create(new ProductsRecord("Product3", "0300"));
            dao.create(new ProductsRecord("Product4", "0400"));
            dao.create(new ProductsRecord("Product5", "0500"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addToConsignmentNotes() {
        try {
            Connection connection = getConnection();
            ConsignmentNoteDAO dao = new ConsignmentNoteDAO(connection);
            dao.create(new ConsignmentNotesRecord(1, LocalDate.of(2020, 2, 1), "0001"));
            dao.create(new ConsignmentNotesRecord(2, LocalDate.of(2020,3,1), "0001"));
            dao.create(new ConsignmentNotesRecord(3, LocalDate.of(2020,5,1), "0001"));
            dao.create(new ConsignmentNotesRecord(4, LocalDate.of(2020,2,2), "0002"));
            dao.create(new ConsignmentNotesRecord(5, LocalDate.of(2020,2,8), "0002"));
            dao.create(new ConsignmentNotesRecord(6, LocalDate.of(2020,2,15), "0002"));
            dao.create(new ConsignmentNotesRecord(7, LocalDate.of(2020,10,1), "0003"));
            dao.create(new ConsignmentNotesRecord(8, LocalDate.of(2020,2,1), "0003"));
            dao.create(new ConsignmentNotesRecord(9, LocalDate.of(2021,2,1), "0004"));
            dao.create(new ConsignmentNotesRecord(10, LocalDate.of(2020,8,18), "0005"));
            dao.create(new ConsignmentNotesRecord(11, LocalDate.of(2020,8,18), "0006"));
            dao.create(new ConsignmentNotesRecord(12, LocalDate.of(2019,10,20), "0007"));
            dao.create(new ConsignmentNotesRecord(13, LocalDate.of(2020,8,20), "0008"));
            dao.create(new ConsignmentNotesRecord(14, LocalDate.of(2021,8,18), "0009"));
            dao.create(new ConsignmentNotesRecord(15, LocalDate.of(2020,1,1), "0011"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addToPositions() {
        try {
            Connection connection = getConnection();
            PositionDAO dao = new PositionDAO(connection);
            dao.create(new PositionsRecord(1, new BigDecimal(100), "0100", 10, 1));
            dao.create(new PositionsRecord(2, new BigDecimal(200), "0200", 20, 1));
            dao.create(new PositionsRecord(3, new BigDecimal(300), "0100", 30, 2));
            dao.create(new PositionsRecord(4, new BigDecimal(100), "0300", 10, 3));
            dao.create(new PositionsRecord(5, new BigDecimal(200), "0200", 5, 4));
            dao.create(new PositionsRecord(6, new BigDecimal(50), "0100", 15, 5));
            dao.create(new PositionsRecord(7, new BigDecimal(15), "0500", 25, 6));
            dao.create(new PositionsRecord(8, new BigDecimal(20), "0300", 5, 7));
            dao.create(new PositionsRecord(9, new BigDecimal(2), "0300", 5, 8));
            dao.create(new PositionsRecord(10, new BigDecimal(200), "0500", 1, 9));
            dao.create(new PositionsRecord(11, new BigDecimal(1), "0100", 5, 10));
            dao.create(new PositionsRecord(12, new BigDecimal(2), "0200", 2, 10));
            dao.create(new PositionsRecord(13, new BigDecimal(100), "0300", 5, 11));
            dao.create(new PositionsRecord(14, new BigDecimal(200), "0200", 100, 12));
            dao.create(new PositionsRecord(15, new BigDecimal(2000), "0100", 50, 13));
            dao.create(new PositionsRecord(16, new BigDecimal(200), "0200", 5, 14));
            dao.create(new PositionsRecord(17, new BigDecimal(500), "0500", 3, 15));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
