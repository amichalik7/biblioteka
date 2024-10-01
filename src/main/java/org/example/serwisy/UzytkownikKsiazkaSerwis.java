package org.example.serwisy;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UzytkownikKsiazkaSerwis {

    MysqlDataSource dataSource = new MysqlDataSource();
    public UzytkownikKsiazkaSerwis() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/biblioteka");
        dataSource.setUser("root");
        dataSource.setPassword("admin");
    }

    public void wypozycz(int idUzytkownika, int idKsiazki) {
        KsiazkaSerwis ks = new KsiazkaSerwis();
        if (ks.ileKsiazek(idKsiazki) > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("insert into uzytkownik_ksiazka (id_uzytkownika, id_ksiazki) values (?, ?) ")) {
                statement.setInt(1, idUzytkownika);
                statement.setInt(2, idKsiazki);
                int resultSet = statement.executeUpdate();
                if (resultSet > 0) {
                    System.out.println("Książka została wypożyczona");

                }
            } catch (Exception e) {
                System.out.println("Błąd" + e.getMessage());
            }
        }
        else System.out.println("Brak dostępnej ilości");
    }
    public void zwroc(int idUzytkownika, int idKsiazki) {
        KsiazkaSerwis ks = new KsiazkaSerwis();
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("delete from uzytkownik_ksiazka where id_uzytkownika=? and id_ksiazki=?")) {
                statement.setInt(1, idUzytkownika);
                statement.setInt(2, idKsiazki);
                int resultSet = statement.executeUpdate();
                if (resultSet > 0) {
                    System.out.println("Książka została zwrócona");

                }
            } catch (Exception e) {
                System.out.println("Błąd" + e.getMessage());
            }
    }
    public void sprawdzSwojeKsiazki(int idUzytkownika) {
        KsiazkaSerwis ks = new KsiazkaSerwis();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select uzytkownik_ksiazka.id_ksiazki, ksiazki.tytul, ksiazki.autor from uzytkownik_ksiazka inner join ksiazki on uzytkownik_ksiazka.id_ksiazki = ksiazki.id where id_uzytkownika = ?;")) {
            statement.setInt(1, idUzytkownika);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt("id_ksiazki") +" " + resultSet.getString("tytul") + " " + resultSet.getString("autor"));
            }
        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
    }

}
