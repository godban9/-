package kp1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

class Library {
    private final Map<String, Semaphore> books;
    private boolean isOpen;

    public Library(Map<String, Integer> bookList) {
        books = new HashMap<>();
        for (Map.Entry<String, Integer> entry : bookList.entrySet()) {
            books.put(entry.getKey(), new Semaphore(entry.getValue()));       // Ініціалізуємо кожну книгу з відповідною кількістю доступних екземплярів
        }
        this.isOpen = true; // Бібліотека відкрита!!!
    }

    public synchronized void openLibrary() {
        isOpen = true;
    }

    public synchronized void closeLibrary() {
        isOpen = false;
        System.out.println("==========БІБЛІОТЕКА ЗАЧИНЕНА=========");
    }

    public void borrowBook(String studentName, String bookTitle) {
        if (!isOpen) {
            System.out.println(studentName + " спробував взяти книгу але його вигнали бо бібліотеку зачинено");
            System.out.println("--------------------------------------------");
            return;
        }

        Semaphore availableBooks = books.get(bookTitle);
        if (availableBooks == null) {
            System.out.println(studentName + " спробував взяти книгу " + bookTitle + ", але її немає зараз в наявності");
            System.out.println("--------------------------------------------");
            return;
        }

        try {
            System.out.println(studentName + " хоче взяти " + bookTitle + "...");
            System.out.println("--------------------------------------------");
            availableBooks.acquire(); // !!!Взяти одну книгу!!!
            System.out.println(studentName + " взяв почитати " + bookTitle + ".");
            System.out.println("--------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Метод для повернення книги
    public void returnBook(String studentName, String bookTitle) {
        if (!isOpen) {
            System.out.println(studentName + " спробував повернути книгу але бібліотека вже зачиненна");
            System.out.println("--------------------------------------------");
            return;
        }

        Semaphore availableBooks = books.get(bookTitle);
        if (availableBooks == null) {
            System.out.println(bookTitle + " нема в цій бібліотеці");
            System.out.println("--------------------------------------------");
            return;
        }

        System.out.println(studentName + " хоче повернути книгу " + bookTitle + "...");
        System.out.println("--------------------------------------------");
        availableBooks.release(); 
        System.out.println(studentName + " успішнову повернув книгу " + bookTitle + ".");
        System.out.println("--------------------------------------------");
    }
}
class Student implements Runnable {
    private final String name;
    private final Library library;
    private final String bookTitle;

    public Student(String name, Library library, String bookTitle) {
        this.name = name;
        this.library = library;
        this.bookTitle = bookTitle;
    }

    @Override
    public void run() {
        library.borrowBook(name, bookTitle);

        // Симуляція часу використання книги студентом
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        library.returnBook(name, bookTitle);
    }
}