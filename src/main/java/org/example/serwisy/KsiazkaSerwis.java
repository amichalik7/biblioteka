package org.example.serwisy;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.model.Ksiazka;
import org.example.model.Uzytkownik;

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
            while (resultSet.next()) {
                ile = resultSet.getInt("stan_magazynowy");
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
             PreparedStatement statement = connection.prepareStatement("select * from ksiazki where stan_magazynowy>0")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getInt(4));
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
    }

    //usun ksiazke

}

