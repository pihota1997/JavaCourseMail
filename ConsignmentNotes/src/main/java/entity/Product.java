package entity;

import org.jetbrains.annotations.NotNull;

public final class Product {
    private @NotNull String name;
    private @NotNull String internalCode;

    public Product(@NotNull String name, @NotNull String internalCode) {
        this.name = name;
        this.internalCode = internalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", internalCode='" + internalCode + '\'' +
                '}';
    }
}
