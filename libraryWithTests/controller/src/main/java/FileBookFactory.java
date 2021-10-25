import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedList;

public final class FileBookFactory{
    @NotNull
    private static final Type listBooksType = new TypeToken<LinkedList<Book>>() {
    }.getType();

    @NotNull
    private String fileName;

    public FileBookFactory(@NotNull String fileName) {
        this.fileName = fileName;
    }

    @NotNull
    public LinkedList<Book> books() {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listBooksType);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getBook(int cellNumber) {
        return books().get(cellNumber).getName() + " " + books().get(cellNumber).getAuthor().getName();
    }

    public void setFileName(@NotNull String fileName) {
        this.fileName = fileName;
    }
}
