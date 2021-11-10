package commons;

import entity.Organization;
import entity.Product;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static generated.Tables.*;
import static org.jooq.impl.DSL.*;

public class Functional {

    private final @NotNull Connection connection;

    public Functional(@NotNull Connection connection) {
        this.connection = connection;
    }

    public List<Organization> selectTenOrganizationsThatDeliveredMoreProducts() {
        final List<Organization> organizationLists = new LinkedList<>();
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record3<String, String, String>> records = context
                .select(ORGANIZATIONS.NAME, ORGANIZATIONS.TIN, ORGANIZATIONS.CURRENT_ACCOUNT)
                .from(POSITIONS).innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO
                        .eq(CONSIGNMENT_NOTES.NO))
                .innerJoin(ORGANIZATIONS).on(CONSIGNMENT_NOTES.ORGANIZATION.eq(ORGANIZATIONS.TIN))
                .groupBy(ORGANIZATIONS.TIN)
                .orderBy(sum(POSITIONS.QUANTITY).desc())
                .limit(10)
                .fetch();


        for (Record record : records) {
            organizationLists.add(new Organization(record.getValue(ORGANIZATIONS.NAME),
                    record.getValue(ORGANIZATIONS.TIN), record.getValue(ORGANIZATIONS.CURRENT_ACCOUNT)));
        }
        return organizationLists;
    }

    public Map<Organization, BigDecimal> selectOrganizationsBySelectedProducts(Map<Product, Integer> list) {
        final Map<Organization, BigDecimal> result = new HashMap<>();
        list.forEach((product, quantity) -> {
            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final Result<Record5<String, String, String, BigDecimal, BigDecimal>> records = context
                    .select(ORGANIZATIONS.NAME, ORGANIZATIONS.TIN, ORGANIZATIONS.CURRENT_ACCOUNT,
                            sum(POSITIONS.QUANTITY).as("total_quantity"),
                            round(sum(POSITIONS.PRICE.multiply(POSITIONS.QUANTITY)),2).as("sum"))
                    .from(PRODUCTS).innerJoin(POSITIONS).on(PRODUCTS.INTERNAL_CODE.eq(POSITIONS.PRODUCT))
                    .innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO.eq(CONSIGNMENT_NOTES.NO))
                    .innerJoin(ORGANIZATIONS).on(CONSIGNMENT_NOTES.ORGANIZATION.eq(ORGANIZATIONS.TIN))
                    .groupBy(ORGANIZATIONS.TIN, POSITIONS.PRODUCT)
                    .having(POSITIONS.PRODUCT.eq(product.getInternalCode()))
                    .and(sum(POSITIONS.QUANTITY).gt(BigDecimal.valueOf(quantity)))
                    .fetch();

            for (Record record : records) {
                Organization organization = new Organization(record.getValue(ORGANIZATIONS.NAME),
                        record.getValue(ORGANIZATIONS.TIN), record.getValue(ORGANIZATIONS.CURRENT_ACCOUNT));
                BigDecimal sum = (BigDecimal) record.get(4);
                if (result.containsKey(organization)) {
                    result.replace(organization, sum.add(result.get(organization)));
                } else {
                    result.put(organization, sum);
                }
            }
        });
        return result;
    }

    public StringBuilder calculateProductQuantityAndSumForPeriod(LocalDate from, LocalDate to) {
        StringBuilder result = new StringBuilder();
        BigDecimal totalPriceForPeriod = null;

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record5<LocalDate, String, String, BigDecimal, BigDecimal>> records = context
                .select(CONSIGNMENT_NOTES.DATE, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE,
                        sum(POSITIONS.QUANTITY).as("total_amount"),
        round(sum(POSITIONS.PRICE.multiply(POSITIONS.QUANTITY)),2).as("total_price"))
                .from(PRODUCTS).innerJoin(POSITIONS).on(PRODUCTS.INTERNAL_CODE.eq(POSITIONS.PRODUCT))
                .innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO.eq(CONSIGNMENT_NOTES.NO))
                .where(CONSIGNMENT_NOTES.DATE.between(from).and(CONSIGNMENT_NOTES.DATE))
                .groupBy(CONSIGNMENT_NOTES.DATE, PRODUCTS.INTERNAL_CODE)
                .fetch();


        for (Record record : records) {
            result.append(record.getValue(CONSIGNMENT_NOTES.DATE) + " "
            + new Product(record.getValue(PRODUCTS.NAME), record.getValue(PRODUCTS.INTERNAL_CODE))
            + " " + record.get(3) + " " + record.get(4) + "\n");
            totalPriceForPeriod.add((BigDecimal) record.get(4));
        }

        result.append("Total sum for the period " + from + " " + to + ": " + totalPriceForPeriod);
        return result;
    }


    public StringBuilder calculateAveragePriceForPeriod(LocalDate from, LocalDate to) {
        StringBuilder result = new StringBuilder();

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record3<String, String, BigDecimal>> records = context
                .select(PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE,
                        round(avg(POSITIONS.PRICE), 2).as("avverage_price"))
                .from(PRODUCTS).innerJoin(POSITIONS).on(PRODUCTS.INTERNAL_CODE.eq(POSITIONS.PRODUCT))
                .innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO.eq(CONSIGNMENT_NOTES.NO))
                .where(CONSIGNMENT_NOTES.DATE.ge(from)).and(CONSIGNMENT_NOTES.DATE.le(to))
                .groupBy(PRODUCTS.INTERNAL_CODE)
                .fetch();

        for (Record record : records) {
            result.append(new Product(record.getValue(PRODUCTS.NAME), record.getValue(PRODUCTS.INTERNAL_CODE))
                    + " " + record.get(2) + " " + record.get(3) + "\n");
        }

        return result;
    }

    public StringBuilder selectProductsListSuppliedByOrganizations(LocalDate from, LocalDate to) {
        StringBuilder result = new StringBuilder();

        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        final Result<Record5<String, String, String, String, String>> records = context
                .select(ORGANIZATIONS.NAME.as("organization"), ORGANIZATIONS.TIN,
                        ORGANIZATIONS.CURRENT_ACCOUNT, PRODUCTS.NAME.as("product"),
                        PRODUCTS.INTERNAL_CODE)
                .from(ORGANIZATIONS).leftJoin(CONSIGNMENT_NOTES).on(ORGANIZATIONS.TIN
                        .eq(CONSIGNMENT_NOTES.ORGANIZATION).and(CONSIGNMENT_NOTES.DATE.ge(from))
                        .and(CONSIGNMENT_NOTES.DATE.le(to)))
                .leftJoin(POSITIONS).on(CONSIGNMENT_NOTES.NO.eq(POSITIONS.CONSIGNMENT_NOTE_NO))
                .leftJoin(PRODUCTS).on(POSITIONS.PRODUCT.eq(PRODUCTS.INTERNAL_CODE))
                .groupBy(ORGANIZATIONS.TIN, PRODUCTS.INTERNAL_CODE)
                .orderBy(ORGANIZATIONS.TIN)
                .fetch();

        for (Record record : records) {
            result.append(new Product(record.getValue(PRODUCTS.NAME), record.getValue(PRODUCTS.INTERNAL_CODE))
                    + " " + new Organization(record.getValue(ORGANIZATIONS.NAME),
                    record.getValue(ORGANIZATIONS.TIN), record.getValue(ORGANIZATIONS.CURRENT_ACCOUNT)) + "\n");
        }

        return result;
    }
}
