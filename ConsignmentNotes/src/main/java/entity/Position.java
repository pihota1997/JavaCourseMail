package entity;

import org.jetbrains.annotations.NotNull;

public class Position {
    private @NotNull int No;
    private @NotNull double price;
    private @NotNull String product;
    private @NotNull int quantity;
    private @NotNull int consignmentNoteNo;

    public Position(@NotNull int no,
                    @NotNull double price,
                    @NotNull String product,
                    @NotNull int quantity,
                    @NotNull int consignmentNoteNo) {
        No = no;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
        this.consignmentNoteNo = consignmentNoteNo;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getConsignmentNoteNo() {
        return consignmentNoteNo;
    }

    public void setConsignmentNoteNo(int consignmentNoteNo) {
        this.consignmentNoteNo = consignmentNoteNo;
    }

    @Override
    public String toString() {
        return "ConsignmentNotePosition{" +
                "No=" + No +
                ", price=" + price +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", consignmentNoteNo=" + consignmentNoteNo +
                '}';
    }
}
