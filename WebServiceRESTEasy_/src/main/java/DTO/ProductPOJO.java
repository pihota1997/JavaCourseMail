package DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;

public final class ProductPOJO {

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String manufacturer;

    @JsonProperty
    private int quantity;

    @JsonCreator
    public ProductPOJO(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("manufacturer") String manufacturer,
                       @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
    }
}
