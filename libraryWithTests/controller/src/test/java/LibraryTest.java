import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public final class LibraryTest {

    private static String file = "E:\\MailJava\\libraryWithTests\\books.txt";
    private final FileBookFactory fileBookFactory = new FileBookFactory(file);
    private final int capacity = fileBookFactory.books().size() + 5;
    private final Library library = new Library(capacity, fileBookFactory);
    private final OutputStream testOut = new ByteArrayOutputStream();
    private final Book newBook = new Book("someBook", new Author("someAuthor"));

    final Injector injector = Guice.createInjector();

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void itShouldThrowExceptionWhenCapacityIsNotEnough() {
        int thisTestCapacity = fileBookFactory.books().size() - 1;
        injector.getInstance(LibraryFactory.class).library(thisTestCapacity,
               injector.getInstance(BooksFactory.class).fileBookFactory(file));
    }

    @Test
    public void booksOrderInLibraryIsEqualToOrderInFile() {
        int i;
        for (i = 0; i < fileBookFactory.books().size(); i++)
            assertEquals(fileBookFactory.getBook(i), library.getInformationAboutBook(i));

        for (int j = i; j < library.getBooks().size(); j++)
            assertNull(library.getBook(j));
    }

    @Test
    public void TakenBookInfoAndCellNumberArePrintedToConsole() {
        System.setOut(new PrintStream(testOut));
        int cellNumber = 15;
        library.takeBook(cellNumber);
        assertThat(testOut.toString(), containsString(Integer.toString(cellNumber)));
        assertThat(testOut.toString(), containsString(library.getInformationAboutBook(cellNumber)));
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsExceptionWhenCellIsEmpty() {
        library.takeBook(101);
    }

    @Test
    public void returnedBookMatchesTheRequestedOne() {
        System.setOut(new PrintStream(testOut));
        int cellNumber = 50;
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
        library.addBook(newBook);
        assertThat(firstEmptyCell, equalTo(library.getBooks().indexOf(newBook)));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testThrowsExceptionWhenIsNoEmptyCellForAddedBook(){
        int thisTestCapacity = fileBookFactory.books().size();
        Library thisTestLibrary = new Library(thisTestCapacity, fileBookFactory);
        thisTestLibrary.addBook(newBook);
    }

    @Test
    public void allLibraryContentIsPrintedToConsole(){
        System.setOut(new PrintStream(testOut));
        library.printLibrary();
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        assertThat(testOut.toString(), equalTo(GSON.toJson(library.getBooks())+'\r'+'\n'));
    }
}
