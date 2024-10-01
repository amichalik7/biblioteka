package org.example.serwisy;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.model.Uzytkownik;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UzytkownikSerwis {
    MysqlDataSource dataSource = new MysqlDataSource();

    public UzytkownikSerwis() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/biblioteka");
        dataSource.setUser("root");
        dataSource.setPassword("admin");
    }


    public boolean login(String email, String haslo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select 1 from uzytkownicy where email=? and haslo=?")) {
            statement.setString(1, email);
            statement.setString(2, haslo);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return false;
    }
    public int podajId(String email, String haslo){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from uzytkownicy where email=? and haslo=?")) {
            statement.setString(1, email);
            statement.setString(2, haslo);
            ResultSet resultSet = statement.executeQuery();
            int id=0;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return 0;
    }

    public String podajRole(String email, String haslo){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from uzytkownicy where email=? and haslo=?")) {
            statement.setString(1, email);
            statement.setString(2, haslo);
            ResultSet resultSet = statement.executeQuery();
            String rola = "";
            while (resultSet.next()) {
                rola = resultSet.getString("rola");
            }
            return rola;

        } catch (Exception e) {
            System.out.println("Błąd" + e.getMessage());
        }
        return "";
    }

    public boolean dodajUzytkownika(Uzytkownik uzytkownik) {
        if (!login(uzytkownik.getEmail(), uzytkownik.getHaslo())) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("insert into uzytkownicy (imie, nazwisko, email, haslo) values (?, ?, ?, ?)")) {
                statement.setString(1, uzytkownik.getImie());
                statement.setString(2, uzytkownik.getNazwisko());
                statement.setString(3, uzytkownik.getEmail());
                statement.setString(4, uzytkownik.getHaslo());
                statement.executeUpdate();
                System.out.println("Użytkownik został dodany");
                return true;
            } catch (Exception e) {
                System.out.println("Błąd");
            }
        }
        System.out.println("Użytkownik o podanym loginie i haśle już istnieje");
        return false;
    }
}
