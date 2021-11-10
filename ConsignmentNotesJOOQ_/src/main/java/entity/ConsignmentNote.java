package entity;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.time.LocalDate;

public final class ConsignmentNote {
    private @NotNull int No;
    private @NotNull LocalDate date;
    private @NotNull String organizationTIN;

    public ConsignmentNote(@NotNull int no, @NotNull LocalDate date, @NotNull String organizationTIN) {
        No = no;
        this.date = date;
        this.organizationTIN = organizationTIN;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int No) {
        No = No;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOrganizationTIN() {
        return organizationTIN;
    }

    public void setOrganizationTIN(String organizationTIN) {
        this.organizationTIN = organizationTIN;
    }

    @Override
    public String toString() {
        return "ConsignmentNote{" +
                "No=" + No +
                ", date=" + date +
                ", organizationTIN='" + organizationTIN + '\'' +
                '}';
    }
}
