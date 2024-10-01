package org.example.serwisy;

import org.example.model.Ksiazka;
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
    String rola = "";

    public void run() {
        do {
            menu();
            wybor = sc.nextInt();
            switch (wybor) {
                case 1:
                    boolean zalogowany = logowanie();
                    if (zalogowany && rola.equals("user")) {
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
                    if (zalogowany && rola.equals("admin")) {
                        do {
                            menuAdmin();
                            wybor = sc.nextInt();
                            sc.nextLine();
                            switch (wybor) {
                                case 1:
                                    System.out.print("Podaj tytuł książki: ");
                                    String tytul = sc.nextLine();
                                    System.out.print("Podaj autora książki: ");
                                    String autor = sc.nextLine();
                                    System.out.print("Ile sztuk chcesz wprowadzić na stan: ");
                                    int ileSzt = sc.nextInt();
                                    Ksiazka ksiazka = new Ksiazka(tytul, autor, ileSzt);
                                    ks.dodajKsiazke(ksiazka);
                                    break;
                                case 2:
                                    System.out.print("Podaj id ksiązki którą chcesz usunąć: ");
                                    int idUsun = sc.nextInt();
                                    ks.usunKsiazke(idUsun);
                                    break;
                                case 3:
                                    System.out.println("Oto lista ksiżek: ");
                                    ks.wyswietlWszystkieKsiazki();
                                    break;
                                case 4:
                                    System.out.println("Lista wypożyczonych książek: ");
                                    ks.wyswietlWypozyczoneKsiazki();
                                    break;
                                case 5:
                                    System.out.print("Podaj id ksiązki którą chcesz zmodyfikować: ");
                                    int idMod = sc.nextInt();
                                    System.out.println("1. Dodaj egzemplarz");
                                    System.out.println("2. Usuń egzemplarz");
                                    System.out.print("Co wybierasz? ");
                                    wybor = sc.nextInt();
                                    if (wybor == 1) {
                                        ks.dodajEgzemplarz(idMod);
                                    } else if (wybor == 2) {
                                        ks.usunEgzemplarz(idMod);
                                    } else {
                                        System.out.println("Nieprawidłowa opcja");
                                    }
                                    break;
                            }
                        } while (wybor != 6);
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
            rola = us.podajRole(email, haslo);

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

    void menuAdmin() {
        System.out.println("Co chcesz zrobić?");
        System.out.println("1. Dodać książkę");
        System.out.println("2. Usunąć ksiązkę");
        System.out.println("3. Zobaczyć dostępne ksiązki");
        System.out.println("4. Zobaczyć wypożyczone ksiązki");
        System.out.println("5. Zmienić ilość sztuk książki");
        System.out.println("6. Wyloguj");
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

