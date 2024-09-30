package org.example.serwisy;
import org.example.model.Uzytkownik;
import java.util.Scanner;
public class ApplicationSerwis {
    Welcome welcome = new Welcome();
    KsiazkaSerwis ks = new KsiazkaSerwis();
    UzytkownikSerwis us = new UzytkownikSerwis();
    UzytkownikKsiazkaSerwis uks = new UzytkownikKsiazkaSerwis();
    Scanner sc = new Scanner(System.in);
    int wybor;
    int idZalogowanegoUzytkownika = 0;

    public void run() {
        do {
            menu();
            wybor = sc.nextInt();
            switch (wybor) {
                case 1:
                    boolean zalogowany = logowanie();
                    if (zalogowany) {
                        do {
                            menuZalogowany();
                            wybor = sc.nextInt();
                            switch (wybor) {
                                case 1:
                                    System.out.print("Podaj id ksiązki: ");
                                    uks.wypozycz(idZalogowanegoUzytkownika, sc.nextInt());
                                    break;
                                case 2:
                                    System.out.print("Podaj id ksiązki: ");
                                    uks.zwroc(idZalogowanegoUzytkownika, sc.nextInt());
                                    break;
                                case 3:
                                    ks.wyswietlKsiazki();
                                    break;
                                case 4:
                                    uks.sprawdzSwojeKsiazki(idZalogowanegoUzytkownika);
                                    break;

                            }
                        } while (wybor != 5);
                    }
                    break;
                case 2:
                    dodajUzytkownika();
            }
        } while (wybor != 0);


    }

    static void menu() {
        System.out.println("Witamy w naszej bibliotece. Co chcesz zrobić?");
        System.out.println("1. Logowanie");
        System.out.println("2. Nowy użytkownik");
        System.out.println("0. Koniec");
        System.out.print("Wybierz opcję: ");
    }

    boolean logowanie() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj email: ");
        String email = sc.next();

        System.out.print("Podaj hasło: ");
        String haslo = sc.next();
        boolean zalogowany = welcome.login(email, haslo);
        if (zalogowany) {
            System.out.println("Użytkownik zalogowany pomyślnie");
            idZalogowanegoUzytkownika = us.podajId(email, haslo);
            System.out.println("id zalogowanego uzytkownika " + idZalogowanegoUzytkownika);
        } else {
            System.out.println("Nieprawidłowa nazwa użytkownika lub hasło");
        }
        return zalogowany;
    }

    void menuZalogowany() {
        System.out.println("Co chcesz zrobić?");
        System.out.println("1. Wypożyczyć książkę");
        System.out.println("2. Oddać ksiązkę");
        System.out.println("3. Zobaczyć dostępne ksiązki");
        System.out.println("4. Zobaczyć Twoje wypożyczone ksiązki");
        System.out.println("5. Wyloguj");
        System.out.print("Wybierz jedną z opcji: ");
    }

    void dodajUzytkownika() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj imię: ");
        String imie = sc.next();
        System.out.print("Podaj nazwisko: ");
        String nazwisko = sc.next();
        System.out.print("Podaj email: ");
        String email = sc.next();
        System.out.print("Podaj haslo: ");
        String haslo = sc.next();
        Uzytkownik uzytkownik = new Uzytkownik(imie, nazwisko, email, haslo);
        welcome.dodajUzytkownika(uzytkownik);
    }
}

