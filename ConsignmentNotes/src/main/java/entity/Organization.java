package entity;

import org.jetbrains.annotations.NotNull;

public final class Organization {
    private @NotNull String name;
    private @NotNull String TIN;
    private @NotNull String currentAccount;

    public Organization(@NotNull String name, @NotNull String TIN, @NotNull String currentAccount) {
        this.name = name;
        this.TIN = TIN;
        this.currentAccount = currentAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", TIN='" + TIN + '\'' +
                ", currentAccount='" + currentAccount + '\'' +
                '}';
    }
}
