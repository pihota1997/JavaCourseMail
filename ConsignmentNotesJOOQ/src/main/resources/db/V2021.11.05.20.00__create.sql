CREATE TABLE products
(
    name          VARCHAR NOT NULL,
    internal_code VARCHAR NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (internal_code)
);

CREATE TABLE organizations
(
    name            VARCHAR     NOT NULL,
    tin             VARCHAR(10) NOT NULL,
    current_account VARCHAR(20) NOT NULL,
    CONSTRAINT organizations_pk PRIMARY KEY (tin)
);

CREATE TABLE consignment_notes
(
    No           SERIAL      NOT NULL,
    date         DATE        NOT NULL,
    organization VARCHAR(10) NOT NULL REFERENCES organizations (tin) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT consignment_notes_pk PRIMARY KEY (No)
);

CREATE TABLE positions
(
    No                  SERIAL         NOT NULL,
    price               NUMERIC(19, 2) NOT NULL,
    product             VARCHAR        NOT NULL REFERENCES products (internal_code) ON UPDATE CASCADE ON DELETE RESTRICT,
    quantity            INT            NOT NULL,
    consignment_note_No INT            NOT NULL REFERENCES consignment_notes (No) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT positions_pk PRIMARY KEY (No)
);