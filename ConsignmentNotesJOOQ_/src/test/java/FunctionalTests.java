import commons.FlywayInitializer;
import commons.Functional;
import commons.JDBCCredentials;
import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class FunctionalTests {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @BeforeClass
    public static void createDataInDB() {
        FlywayInitializer.initDB();
        InclusionInDB.addAllToDB();
    }

    @Test
    public void shouldSelectTenOrganizationsThatDeliveredMoreProducts() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            List<Organization> SQLList = functional.selectTenOrganizationsThatDeliveredMoreProducts();
            SQLList.forEach(System.out::println);
            assertTrue(SQLList.size() == 10);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldSelectOrganizationsBySelectedProducts() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            Map<Product, Integer> request = new HashMap<>();
            request.put(new Product("Product1", "0100"), 10);
            request.put(new Product("Product4", "0400"), 1);
            request.put(new Product("Product2", "0200"), 20);
            Map<Organization, BigDecimal> SQLMap = functional.selectOrganizationsBySelectedProducts(request);
            for (Map.Entry<Organization, BigDecimal> pair : SQLMap.entrySet()) {
                System.out.println(pair.getKey() + " " + pair.getValue());
            }
            assertTrue(SQLMap.size() == 4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldCalculateProductQuantityAndSumForPeriod() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            LocalDate from = LocalDate.of(2020, 03, 01);
            LocalDate to = LocalDate.of(2020, 05, 01);

            StringBuilder SQLResult = functional.calculateProductQuantityAndSumForPeriod(from, to);
            String correctResult = "2020-03-01 Product{name='Product1', internalCode='0100'} 30 9000.0" + "\n" +
                    "2020-05-01 Product{name='Product3', internalCode='0300'} 10 1000.0" + "\n" +
                    "Total sum for the period 2020-03-01 2020-05-01: 10000.0";

            System.out.println(SQLResult);

            assertEquals(correctResult, SQLResult.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldCalculateAveragePriceForPeriod() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            LocalDate from = LocalDate.of(2020, 01, 01);
            LocalDate to = LocalDate.of(2020,05,01);

            StringBuilder SQLResult = functional.calculateAveragePriceForPeriod(from, to);
            String correctResult = "Product{name='Product1', internalCode='0100'} 150.0\n" +
                    "Product{name='Product2', internalCode='0200'} 200.0\n" +
                    "Product{name='Product3', internalCode='0300'} 51.0\n" +
                    "Product{name='Product5', internalCode='0500'} 257.5\n";

            System.out.println(SQLResult);

            assertEquals(correctResult, SQLResult.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldSelectProductsListSuppliedByOrganizations() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            LocalDate from = LocalDate.of(2020,01,01);
            LocalDate to = LocalDate.of(2020, 02,10);

            StringBuilder SQLResult = functional.selectProductsListSuppliedByOrganizations(from, to);
            String correctResult = "Product{name='Product1', internalCode='0100'} " +
                    "Organization{name='Organization1', TIN='0001', currentAccount='0000'}\n" +
                    "Product{name='Product2', internalCode='0200'} " +
                    "Organization{name='Organization1', TIN='0001', currentAccount='0000'}\n" +
                    "Product{name='Product1', internalCode='0100'} " +
                    "Organization{name='Organization2', TIN='0002', currentAccount='0000'}\n" +
                    "Product{name='Product2', internalCode='0200'} " +
                    "Organization{name='Organization2', TIN='0002', currentAccount='0000'}\n" +
                    "Product{name='Product3', internalCode='0300'} " +
                    "Organization{name='Organization3', TIN='0003', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization4', TIN='0004', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization5', TIN='0005', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization6', TIN='0006', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization7', TIN='0007', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization8', TIN='0008', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization9', TIN='0009', currentAccount='0000'}\n" +
                    "Product{name='null', internalCode='null'} " +
                    "Organization{name='Organization10', TIN='0010', currentAccount='0000'}\n" +
                    "Product{name='Product5', internalCode='0500'} " +
                    "Organization{name='Organization11', TIN='0011', currentAccount='0000'}\n";
            System.out.println(SQLResult);

            assertEquals(correctResult, SQLResult.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
