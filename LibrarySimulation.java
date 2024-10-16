package kp1;

import java.util.HashMap;
import java.util.Map;

public class LibrarySimulation {
    public static void main(String[] args) {
        Map<String, Integer> bookList = new HashMap<>();     //Список наших книг
        bookList.put("(Філософія перемоги)", 3);
        bookList.put("(Джава для чайніков)", 2);
        bookList.put("(Шо таке бекенд та чого він кончився)", 1);
        bookList.put("(Вишмат)", 4);

        Library library = new Library(bookList); // Бібліотека з 4 книгами

        Thread student1 = new Thread(new Student("Богдан", library, "(Філософія перемоги)"));
        Thread student2 = new Thread(new Student("Ваня", library, "(Джава для чайніков)"));
        Thread student3 = new Thread(new Student("Микитка", library, "(Біологія 8 клас)"));
        Thread student4 = new Thread(new Student("Назар", library, "(Шо таке бекенд та чого він кончився)"));
        Thread student5 = new Thread(new Student("Саша", library, "(Вишмат)"));
        Thread student6 = new Thread(new Student("Коля", library, "(Джава для чайніков)"));
        Thread student7 = new Thread(new Student("Віталік", library, "(Філософія перемоги)"));

        student1.start();
        student2.start();
        student3.start();
        student4.start();
        student5.start();
        student6.start();
        student7.start();

        try {
            Thread.sleep(10000); // Бібліотека працює 10 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        library.closeLibrary(); 
    }
}