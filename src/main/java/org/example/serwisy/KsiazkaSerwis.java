package org.example.serwisy;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.model.Ksiazka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KsiazkaSerwis {
    MysqlDataSource dataSource = new MysqlDataSource();

    public KsiazkaSerwis() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/biblioteka");
        dataSource.setUser("root");
        dataSource.setPassword("admin");
    }


    public int ileKsiazek(String tytul, String autor) {
        int ile = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select stan_magazynowy from ksiazki where tytul=? and autor=?")) {
            statement.setString(1, tytul);
            statement.setString(2, autor);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ile = resultSet.getInt("stan_magazynowy");
            }
            return ile;

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return ile;
    }

    public int ileKsiazek(int id) {
        int ile = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select stan_magazynowy from ksiazki where id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int ileWypozyczonych = ileWypozyczen(id);
            while (resultSet.next()) {
                ile = resultSet.getInt("stan_magazynowy");
            }
            return ile - ileWypozyczonych;

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return ile;
    }

    public int ileWypozyczen(int id) {
        int ile = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select count(*) from uzytkownik_ksiazka where id_ksiazki=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ile = resultSet.getInt(1);
            }
            return ile;

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return ile;
    }

    public void dodajKsiazke(Ksiazka ksiazka) {
        int ile = ileKsiazek(ksiazka.getTytul(), ksiazka.getAutor());
        if (ile == 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("insert into ksiazki (tytul, autor, stan_magazynowy) values (?, ?, ?)")) {
                statement.setString(1, ksiazka.getTytul());
                statement.setString(2, ksiazka.getAutor());
                statement.setInt(3, ksiazka.getStanMagazynowy());
                statement.executeUpdate();
                System.out.println("Książka została dodana");
            } catch (Exception e) {
                System.out.println("Błąd");
            }
        } else {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("update ksiazki set stan_magazynowy=? where tytul=? and autor=? ")) {
                statement.setInt(1, ile + ksiazka.getStanMagazynowy());
                statement.setString(2, ksiazka.getTytul());
                statement.setString(3, ksiazka.getAutor());
                statement.executeUpdate();
                System.out.println("Książka jest już w bazie, zwiększam dostępne ilości");
            } catch (Exception e) {
                System.out.println("Błąd");
            }

        }
    }

    public void wyswietlKsiazki() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from ksiazki")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (ileKsiazek(resultSet.getInt("id")) > 0) {
                    System.out.print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + ileKsiazek(resultSet.getInt(1)));
                    System.out.println();
                }
            }

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
    }
    public void wyswietlWszystkieKsiazki() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from ksiazki")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                    System.out.print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getInt(4));
                    System.out.println();
                }
        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
    }
    public int ileWszystkichKsiazek( int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select stan_magazynowy from ksiazki where id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return 0;
    }

    public void wyswietlWypozyczoneKsiazki() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select uzytkownicy.imie, uzytkownicy.nazwisko, uzytkownik_ksiazka.id_ksiazki, ksiazki.tytul, ksiazki.autor from uzytkownik_ksiazka join ksiazki on uzytkownik_ksiazka.id_ksiazki=ksiazki.id join uzytkownicy on uzytkownik_ksiazka.id_uzytkownika=uzytkownicy.id ")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getString("imie") + " " + resultSet.getString("nazwisko") + " " +resultSet.getInt("id_ksiazki") + " " + resultSet.getString("tytul") + " " + resultSet.getString("autor"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
    }

    public void usunKsiazke(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from ksiazki where id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Książka została usunięta");
        } catch (Exception e) {
            System.out.println("Błąd");
        }
    }

    public void dodajEgzemplarz(int id){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("update ksiazki set stan_magazynowy=? where id=?")) {

            statement.setInt(1, ileWszystkichKsiazek(id)+1);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Dodano do magazynu jeden egzemplarz książki");
        } catch (Exception e) {
            System.out.println("Błąd");
        }
    }
    public void usunEgzemplarz(int id){
        if (ileWszystkichKsiazek(id)>1){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("update ksiazki set stan_magazynowy=? where id=?")) {
            statement.setInt(1, ileWszystkichKsiazek(id)-1);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Z magazynu usunięto jeden egzemplarz książki");
        } catch (Exception e) {
            System.out.println("Błąd");
        }
        }
        else {
            System.out.println("To był ostatni egzemplarz tej ksiązki. Usuwam książkę z bazy");
            usunKsiazke(id);
        }
    }

}

