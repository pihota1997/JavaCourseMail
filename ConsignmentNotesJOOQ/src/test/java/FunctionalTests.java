import commons.FlywayInitializer;
import commons.Functional;
import commons.JDBCCredentials;
import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Result;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
            Result<OrganizationsRecord> SQLList = functional.selectTenOrganizationsThatDeliveredMoreProducts();

            assertEquals(10, SQLList.size());
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldSelectOrganizationsBySelectedProducts() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            Map<ProductsRecord, Integer> request = new HashMap<>();
            request.put(new ProductsRecord("Product1", "0100"), 10);
            request.put(new ProductsRecord("Product4", "0400"), 1);
            request.put(new ProductsRecord("Product2", "0200"), 20);
            Map<OrganizationsRecord, BigDecimal> SQLMap = functional.selectOrganizationsBySelectedProducts(request);

            assertEquals(4, SQLMap.size());
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldCalculateProductQuantityAndSumForPeriod() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            LocalDate from = LocalDate.of(2020, 3, 1);
            LocalDate to = LocalDate.of(2020, 5, 1);
            Result<Record5<LocalDate, String, String, BigDecimal, BigDecimal>> SQLResult =
                    functional.calculateProductQuantityAndSumForPeriod(from, to);

            assertEquals(LocalDate.of(2020, 3, 1), SQLResult.getValue(0, 0));
            assertEquals("Product1", SQLResult.getValue(0, 1));
            assertEquals("0100", SQLResult.getValue(0, 2));
            assertEquals(new BigDecimal(30), SQLResult.getValue(0, 3));
            assertEquals(new BigDecimal("9000.00"), SQLResult.getValue(0, 4));

            assertEquals(LocalDate.of(2020, 5, 1), SQLResult.getValue(1, 0));
            assertEquals("Product3", SQLResult.getValue(1, 1));
            assertEquals("0300", SQLResult.getValue(1, 2));
            assertEquals(new BigDecimal(10), SQLResult.getValue(1, 3));
            assertEquals(new BigDecimal("1000.00"), SQLResult.getValue(1, 4));

        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldCalculateAveragePriceForPeriod() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            LocalDate from = LocalDate.of(2020, 1, 1);
            LocalDate to = LocalDate.of(2020,5,1);

            Result<Record3<String, String, BigDecimal>> SQLResult = functional.calculateAveragePriceForPeriod(from, to);

            assertEquals("Product1", SQLResult.getValue(0, 0));
            assertEquals("0100", SQLResult.getValue(0, 1));
            assertEquals(new BigDecimal("150.00"), SQLResult.getValue(0, 2));

            assertEquals("Product2", SQLResult.getValue(1, 0));
            assertEquals("0200", SQLResult.getValue(1, 1));
            assertEquals(new BigDecimal("200.00"), SQLResult.getValue(1, 2));

            assertEquals("Product3", SQLResult.getValue(2, 0));
            assertEquals("0300", SQLResult.getValue(2, 1));
            assertEquals(new BigDecimal("51.00"), SQLResult.getValue(2, 2));

            assertEquals("Product5", SQLResult.getValue(3, 0));
            assertEquals("0500", SQLResult.getValue(3, 1));
            assertEquals(new BigDecimal("257.50"), SQLResult.getValue(3, 2));

        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldSelectProductsListSuppliedByOrganizations() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.getLogin(), CREDS.getPassword())) {
            Functional functional = new Functional(connection);
            LocalDate from = LocalDate.of(2020,1,1);
            LocalDate to = LocalDate.of(2020, 2,10);

            Result<Record5<String, String, String, String, String>> SQLResult =
                    functional.selectProductsListSuppliedByOrganizations(from, to);

            assertEquals("0001", SQLResult.getValue(0, 1));
            assertEquals("0100", SQLResult.getValue(0, 4));

            assertEquals("0001", SQLResult.getValue(1, 1));
            assertEquals("0200", SQLResult.getValue(1, 4));

            assertEquals("0002", SQLResult.getValue(2, 1));
            assertEquals("0100", SQLResult.getValue(2, 4));

            assertEquals("0004", SQLResult.getValue(5, 1));
            assertNull(SQLResult.getValue(5, 4));

        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }
}
