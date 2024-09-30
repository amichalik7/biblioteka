package org.example.serwisy;

import org.example.model.Uzytkownik;

public class Welcome {

    private UzytkownikSerwis uzytkownikSerwis = new UzytkownikSerwis();

       public boolean login(String email, String haslo) {
           return uzytkownikSerwis.login(email,haslo);
    }

    public boolean dodajUzytkownika(Uzytkownik uzytkownik) {
        return uzytkownikSerwis.dodajUzytkownika(uzytkownik);
    }
}
