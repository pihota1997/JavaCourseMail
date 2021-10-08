import lombok.*;

@Data
@AllArgsConstructor
public class Book {
    private String title;
    private Author author;

    public String getTitle() {
        return title;
    }
}
