import java.util.ArrayList;
import java.util.Scanner;

/*
 * Personal Library Manager
 *
 * This console application allows a user to:
 * - View all books
 * - Add new books
 * - Search for books by title
 * - Update a book's reading status
 * - Remove books
 * - Filter books by reading status
 *
 * The program demonstrates:
 * - Object-oriented programming
 * - Classes and objects
 * - ArrayLists
 * - Methods
 * - Loops
 * - Input validation
 * - Exception handling
 *
 * Author: Caleb Gandee
 * Date: 7/16/2026
 */

public class LibraryManager {

    /*
     * The Book class represents one book in the library.
     */
    static class Book {

        private int id;
        private String title;
        private String author;
        private String status;

        /*
         * Constructor used to create a new Book object.
         */
        public Book(int id, String title, String author, String status) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.status = status;
        }

        /*
         * Returns the book's unique ID.
         */
        public int getId() {
            return id;
        }

        /*
         * Returns the book's title.
         */
        public String getTitle() {
            return title;
        }

        /*
         * Returns the book's author.
         */
        public String getAuthor() {
            return author;
        }

        /*
         * Returns the book's current reading status.
         */
        public String getStatus() {
            return status;
        }

        /*
         * Changes the book's reading status.
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /*
         * Returns a formatted description of the book.
         */
        @Override
        public String toString() {
            return "ID: " + id
                    + " | Title: " + title
                    + " | Author: " + author
                    + " | Status: " + status;
        }
    }

    /*
     * Stores every book added to the application.
     */
    private static ArrayList<Book> books = new ArrayList<Book>();

    /*
     * Reads user input from the keyboard.
     */
    private static Scanner scanner = new Scanner(System.in);

    /*
     * Program execution begins here.
     */
    public static void main(String[] args) {

        addSampleBooks();

        boolean running = true;

        System.out.println("========================================");
        System.out.println("       PERSONAL LIBRARY MANAGER");
        System.out.println("========================================");

        while (running) {

            displayMenu();

            int choice = readInteger("Choose an option: ");

            switch (choice) {

                case 1:
                    displayAllBooks();
                    break;

                case 2:
                    addBook();
                    break;

                case 3:
                    searchByTitle();
                    break;

                case 4:
                    updateBookStatus();
                    break;

                case 5:
                    removeBook();
                    break;

                case 6:
                    filterBooksByStatus();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid selection. Please choose a menu option."
                    );
                    break;
            }
        }

        scanner.close();

        System.out.println();
        System.out.println(
                "Thank you for using Personal Library Manager."
        );
    }

    /*
     * Displays the main menu.
     */
    private static void displayMenu() {

        System.out.println();
        System.out.println("1. View All Books");
        System.out.println("2. Add a Book");
        System.out.println("3. Search by Title");
        System.out.println("4. Update Reading Status");
        System.out.println("5. Remove a Book");
        System.out.println("6. Filter by Reading Status");
        System.out.println("0. Exit");
        System.out.println();
    }

    /*
     * Displays every book currently stored in the library.
     */
    private static void displayAllBooks() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("               ALL BOOKS");
        System.out.println("========================================");

        if (books.isEmpty()) {
            System.out.println("Your library is currently empty.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    /*
     * Adds a new book to the library.
     */
    private static void addBook() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              ADD A BOOK");
        System.out.println("========================================");

        String title = readRequiredText("Enter the book title: ");
        String author = readRequiredText("Enter the author: ");
        String status = chooseReadingStatus();

        /*
         * Finds the lowest available positive ID.
         */
        int newBookId = getNextAvailableId();

        Book newBook = new Book(
                newBookId,
                title,
                author,
                status
        );

        books.add(newBook);

        System.out.println();
        System.out.println("Book added successfully.");
        System.out.println(newBook);
    }

    /*
     * Searches for books whose titles contain the text
     * entered by the user.
     */
    private static void searchByTitle() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             SEARCH BOOKS");
        System.out.println("========================================");

        if (books.isEmpty()) {
            System.out.println("Your library is currently empty.");
            return;
        }

        String searchTerm = readRequiredText(
                "Enter part of the book title: "
        ).toLowerCase();

        boolean bookFound = false;

        System.out.println();
        System.out.println("Search Results:");
        System.out.println("----------------------------------------");

        for (Book book : books) {

            String bookTitle = book.getTitle().toLowerCase();

            if (bookTitle.contains(searchTerm)) {
                System.out.println(book);
                bookFound = true;
            }
        }

        if (!bookFound) {
            System.out.println("No matching books were found.");
        }
    }

    /*
     * Updates the reading status of a selected book.
     */
    private static void updateBookStatus() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        UPDATE READING STATUS");
        System.out.println("========================================");

        if (books.isEmpty()) {
            System.out.println("Your library is currently empty.");
            return;
        }

        displayAllBooks();

        int id = readInteger(
                "Enter the ID of the book you want to update: "
        );

        Book selectedBook = findBookById(id);

        if (selectedBook == null) {
            System.out.println("No book was found with that ID.");
            return;
        }

        System.out.println();
        System.out.println("Selected Book:");
        System.out.println(selectedBook);

        String newStatus = chooseReadingStatus();

        selectedBook.setStatus(newStatus);

        System.out.println();
        System.out.println("Reading status updated successfully.");
        System.out.println(selectedBook);
    }

    /*
     * Removes a selected book from the library.
     */
    private static void removeBook() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             REMOVE A BOOK");
        System.out.println("========================================");

        if (books.isEmpty()) {
            System.out.println("Your library is currently empty.");
            return;
        }

        displayAllBooks();

        int id = readInteger(
                "Enter the ID of the book you want to remove: "
        );

        Book selectedBook = findBookById(id);

        if (selectedBook == null) {
            System.out.println("No book was found with that ID.");
            return;
        }

        System.out.println();
        System.out.println("Selected Book:");
        System.out.println(selectedBook);

        String confirmation = readRequiredText(
                "Type YES to remove this book: "
        );

        if (confirmation.equalsIgnoreCase("YES")) {

            books.remove(selectedBook);

            System.out.println("Book removed successfully.");

        } else {

            System.out.println("Removal canceled.");
        }
    }

    /*
     * Displays only books matching a selected reading status.
     */
    private static void filterBooksByStatus() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       FILTER BY READING STATUS");
        System.out.println("========================================");

        if (books.isEmpty()) {
            System.out.println("Your library is currently empty.");
            return;
        }

        String selectedStatus = chooseReadingStatus();
        boolean bookFound = false;

        System.out.println();
        System.out.println("Books with status: " + selectedStatus);
        System.out.println("----------------------------------------");

        for (Book book : books) {

            if (book.getStatus().equals(selectedStatus)) {
                System.out.println(book);
                bookFound = true;
            }
        }

        if (!bookFound) {
            System.out.println(
                    "No books were found with that reading status."
            );
        }
    }

    /*
     * Searches the ArrayList for a book with the specified ID.
     *
     * Returns the matching Book object if found.
     * Returns null if no matching book exists.
     */
    private static Book findBookById(int id) {

        for (Book book : books) {

            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    /*
     * Finds the lowest available positive ID.
     *
     * Example:
     * If the existing IDs are 1, 2, and 4,
     * this method returns 3.
     */
    private static int getNextAvailableId() {

        int availableId = 1;

        while (findBookById(availableId) != null) {
            availableId++;
        }

        return availableId;
    }

    /*
     * Displays the available reading statuses and returns
     * the status selected by the user.
     */
    private static String chooseReadingStatus() {

        while (true) {

            System.out.println();
            System.out.println("Choose a Reading Status:");
            System.out.println("1. Want to Read");
            System.out.println("2. Currently Reading");
            System.out.println("3. Finished");

            int choice = readInteger("Enter your selection: ");

            switch (choice) {

                case 1:
                    return "Want to Read";

                case 2:
                    return "Currently Reading";

                case 3:
                    return "Finished";

                default:
                    System.out.println(
                            "Invalid selection. Please choose 1, 2, or 3."
                    );
                    break;
            }
        }
    }

    /*
     * Reads and validates a whole number entered by the user.
     */
    private static int readInteger(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException exception) {

                System.out.println(
                        "Invalid input. Please enter a whole number."
                );
            }
        }
    }

    /*
     * Reads text entered by the user and prevents empty input.
     */
    private static String readRequiredText(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "This field cannot be empty. Please try again."
            );
        }
    }

    /*
     * Adds sample books when the application starts.
     */
    private static void addSampleBooks() {

        books.add(
                new Book(
                        getNextAvailableId(),
                        "The Hobbit",
                        "J.R.R. Tolkien",
                        "Finished"
                )
        );

        books.add(
                new Book(
                        getNextAvailableId(),
                        "Clean Code",
                        "Robert C. Martin",
                        "Currently Reading"
                )
        );

        books.add(
                new Book(
                        getNextAvailableId(),
                        "The Pragmatic Programmer",
                        "Andrew Hunt and David Thomas",
                        "Want to Read"
                )
        );
    }
}