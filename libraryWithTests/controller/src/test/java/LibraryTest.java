import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public final class LibraryTest {
    @NotNull
    private final FileBookFactory fileBookFactory = Mockito.mock(FileBookFactory.class);
    private final OutputStream testOut = new ByteArrayOutputStream();
    private final int capacity = 3;
    private final Library library = new Library(capacity, fileBookFactory);
    private final Book firstBook = new Book("first book", new Author("first author"));
    private final Book secondBook = new Book("second book", new Author("second author"));
    private final Book thirdBook = new Book("third book", new Author("third author"));

    @Before
    public void addBooksInLibrary(){
        library.addBook(firstBook);
        library.addBook(secondBook);
        Mockito.when(fileBookFactory.getBook(0)).thenReturn("first book first author");
        Mockito.when(fileBookFactory.getBook(1)).thenReturn("second book second author");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void itShouldThrowExceptionWhenCapacityIsNotEnough() {
        LinkedList<Book> linkedList = new LinkedList<>();
        linkedList.add(firstBook);
        Mockito.when(fileBookFactory.books()).thenReturn(linkedList);
        new Library(fileBookFactory.books().size()-1, fileBookFactory);
    }

    @Test
    public void booksOrderInLibraryIsEqualToOrderInFile() {
        assertEquals(fileBookFactory.getBook(0), library.getInformationAboutBook(0));
        assertEquals(fileBookFactory.getBook(1), library.getInformationAboutBook(1));
        assertNull(library.getBook(2));
    }

    @Test
    public void TakenBookInfoAndCellNumberArePrintedToConsole() {
        System.setOut(new PrintStream(testOut));
        int cellNumber = 1;
        library.takeBook(cellNumber);
        assertThat(testOut.toString(), containsString(Integer.toString(cellNumber)));
        assertThat(testOut.toString(), containsString(library.getInformationAboutBook(cellNumber)));
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsExceptionWhenCellIsEmpty() {
        library.takeBook(capacity-1);
    }

    @Test
    public void returnedBookMatchesTheRequestedOne() {
        System.setOut(new PrintStream(testOut));
        int cellNumber = 1;
        library.takeBook(cellNumber);
        assertThat(testOut.toString(), containsString(library.getBook(cellNumber).getName()));
        assertThat(testOut.toString(), containsString(library.getBook(cellNumber).getAuthor().getName()));
    }

    @Test
    public void addedBookIsPlacedInFirstEmptyCell() {
        int firstEmptyCell = 0;
        for (int i = 0; i < library.getBooks().size(); i++) {
            if (isEmptyOrNullString().matches(library.getBooks().get(i))) {
                firstEmptyCell = i;
                break;
            }
        }
        library.addBook(thirdBook);
        assertThat(firstEmptyCell, equalTo(library.getBooks().indexOf(thirdBook)));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testThrowsExceptionWhenIsNoEmptyCellForAddedBook(){
        library.addBook(firstBook);
        library.addBook(secondBook);
    }

    @Test
    public void allLibraryContentIsPrintedToConsole(){
        System.setOut(new PrintStream(testOut));
        library.printLibrary();
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        assertThat(testOut.toString(), equalTo(GSON.toJson(library.getBooks())+'\r'+'\n'));
    }
}
