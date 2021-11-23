package commons;

import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static generated.Tables.*;
import static org.jooq.impl.DSL.*;

public class Functional {

    private final @NotNull Connection connection;

    public Functional(@NotNull Connection connection) {
        this.connection = connection;
    }

    public Result<OrganizationsRecord> selectTenOrganizationsThatDeliveredMoreProducts() {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        return context
                .select(ORGANIZATIONS.NAME, ORGANIZATIONS.TIN, ORGANIZATIONS.CURRENT_ACCOUNT)
                .from(POSITIONS).innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO
                        .eq(CONSIGNMENT_NOTES.NO))
                .innerJoin(ORGANIZATIONS).on(CONSIGNMENT_NOTES.ORGANIZATION.eq(ORGANIZATIONS.TIN))
                .groupBy(ORGANIZATIONS.TIN)
                .orderBy(sum(POSITIONS.QUANTITY).desc())
                .limit(10)
                .fetchInto(ORGANIZATIONS);
    }

    public Map<OrganizationsRecord, BigDecimal> selectOrganizationsBySelectedProducts(Map<ProductsRecord, Integer> list) {
        final Map<OrganizationsRecord, BigDecimal> result = new HashMap<>();
        list.forEach((product, quantity) -> {
            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final Result<Record5<String, String, String, BigDecimal, BigDecimal>> records = context
                    .select(ORGANIZATIONS.NAME, ORGANIZATIONS.TIN, ORGANIZATIONS.CURRENT_ACCOUNT,
                            sum(POSITIONS.QUANTITY).as("total_quantity"),
                            round(sum(POSITIONS.PRICE.multiply(POSITIONS.QUANTITY)), 2).as("sum"))
                    .from(PRODUCTS).innerJoin(POSITIONS).on(PRODUCTS.INTERNAL_CODE.eq(POSITIONS.PRODUCT))
                    .innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO.eq(CONSIGNMENT_NOTES.NO))
                    .innerJoin(ORGANIZATIONS).on(CONSIGNMENT_NOTES.ORGANIZATION.eq(ORGANIZATIONS.TIN))
                    .groupBy(ORGANIZATIONS.TIN, POSITIONS.PRODUCT)
                    .having(POSITIONS.PRODUCT.eq(product.getInternalCode()))
                    .and(sum(POSITIONS.QUANTITY).gt(BigDecimal.valueOf(quantity)))
                    .fetch();

            for (Record record : records) {
                OrganizationsRecord organization = new OrganizationsRecord(record.getValue(ORGANIZATIONS.NAME),
                        record.getValue(ORGANIZATIONS.TIN), record.getValue(ORGANIZATIONS.CURRENT_ACCOUNT));
                BigDecimal sum = (BigDecimal) record.get(4);
                if (result.containsKey(organization)) {
                    assert sum != null;
                    result.replace(organization, sum.add(result.get(organization)));
                } else {
                    result.put(organization, sum);
                }
            }
        });
        return result;
    }

    public Result<Record5<LocalDate, String, String, BigDecimal, BigDecimal>> calculateProductQuantityAndSumForPeriod(LocalDate from, LocalDate to) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        return context
                .select(CONSIGNMENT_NOTES.DATE, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE,
                        sum(POSITIONS.QUANTITY).as("total_amount"),
                        round(sum(POSITIONS.PRICE.multiply(POSITIONS.QUANTITY)), 2).as("total_price"))
                .from(PRODUCTS).innerJoin(POSITIONS).on(PRODUCTS.INTERNAL_CODE.eq(POSITIONS.PRODUCT))
                .innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO.eq(CONSIGNMENT_NOTES.NO))
                .where(CONSIGNMENT_NOTES.DATE.between(from).and(to))
                .groupBy(CONSIGNMENT_NOTES.DATE, PRODUCTS.INTERNAL_CODE)
                .fetch();
    }


    public Result<Record3<String, String, BigDecimal>> calculateAveragePriceForPeriod(LocalDate from, LocalDate to) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        return context
                .select(PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE,
                        round(avg(POSITIONS.PRICE), 2).as("average_price"))
                .from(PRODUCTS).innerJoin(POSITIONS).on(PRODUCTS.INTERNAL_CODE.eq(POSITIONS.PRODUCT))
                .innerJoin(CONSIGNMENT_NOTES).on(POSITIONS.CONSIGNMENT_NOTE_NO.eq(CONSIGNMENT_NOTES.NO))
                .where(CONSIGNMENT_NOTES.DATE.ge(from)).and(CONSIGNMENT_NOTES.DATE.le(to))
                .groupBy(PRODUCTS.INTERNAL_CODE)
                .fetch();

    }

    public Result<Record5<String, String, String, String, String>> selectProductsListSuppliedByOrganizations(LocalDate from, LocalDate to) {
        final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

        return context
                .select(ORGANIZATIONS.NAME, ORGANIZATIONS.TIN,
                        ORGANIZATIONS.CURRENT_ACCOUNT, PRODUCTS.NAME,
                        PRODUCTS.INTERNAL_CODE)
                .from(ORGANIZATIONS).leftJoin(CONSIGNMENT_NOTES).on(ORGANIZATIONS.TIN
                        .eq(CONSIGNMENT_NOTES.ORGANIZATION).and(CONSIGNMENT_NOTES.DATE.ge(from))
                        .and(CONSIGNMENT_NOTES.DATE.le(to)))
                .leftJoin(POSITIONS).on(CONSIGNMENT_NOTES.NO.eq(POSITIONS.CONSIGNMENT_NOTE_NO))
                .leftJoin(PRODUCTS).on(POSITIONS.PRODUCT.eq(PRODUCTS.INTERNAL_CODE))
                .groupBy(ORGANIZATIONS.TIN, PRODUCTS.INTERNAL_CODE)
                .orderBy(ORGANIZATIONS.TIN)
                .fetch();
    }
}
