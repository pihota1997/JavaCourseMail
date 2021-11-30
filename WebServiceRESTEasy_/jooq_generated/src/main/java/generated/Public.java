/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.tables.Products;
import generated.tables.Roles;
import generated.tables.UserRoles;
import generated.tables.Users;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.products</code>.
     */
    public final Products PRODUCTS = Products.PRODUCTS;

    /**
     * The table <code>public.roles</code>.
     */
    public final Roles ROLES = Roles.ROLES;

    /**
     * The table <code>public.user_roles</code>.
     */
    public final UserRoles USER_ROLES = UserRoles.USER_ROLES;

    /**
     * The table <code>public.users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Products.PRODUCTS,
            Roles.ROLES,
            UserRoles.USER_ROLES,
            Users.USERS
        );
    }
}