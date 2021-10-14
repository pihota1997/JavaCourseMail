
public class Application {
    public static void main(String[] args) {
        LibraryFactory libraryFactory = new LibraryFactory();
        Library library = libraryFactory.createLibrary();
        library.addBook("Пикник на обочине", "Стругацкие А. и Б.");
        library.addBook("1984", "Оруэлл Дж.");
        library.addBook("Гарри Поттер", "Роулинг Дж.");
        library.addBook("Вокруг света за 80 дней", "Верн Ж.");
        library.addBook("Таинственный остров", "Верн Ж.");
        library.authorBooks();
    }
}
