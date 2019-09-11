package model;

import controller.SQLController;
import controller.ScannerController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Measurements {
    private int id;
    private int userId;
    private String registrationDate;
    private int weight;
    private int height;
    private int hips;
    private int chest;
    private int waist;
    private int arms;


    public Measurements(int id, int userId, String registrationDate, int weight, int height, int hips, int chest, int waist, int arms) {
        this.id = id;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.weight = weight;
        this.height = height;
        this.hips = hips;
        this.chest = chest;
        this.waist = waist;
        this.arms = arms;
    }

    public Measurements() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHips() {
        return hips;
    }

    public void setHips(int hips) {
        this.hips = hips;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getArms() {
        return arms;
    }

    public void setArms(int arms) {
        this.arms = arms;
    }

    public void saveToDB(Connection conn) throws SQLException {
        try {
            if (this.id == 0) {
                String sql = "INSERT INTO measurements(user_id, reg_date, weight, height, hips, chest, waist, arms) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                String[] generatedColumns = {"measurements_id"};
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql, generatedColumns);
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, this.registrationDate);
                preparedStatement.setInt(3, this.weight);
                preparedStatement.setInt(4, this.height);
                preparedStatement.setInt(5, this.hips);
                preparedStatement.setInt(6, this.chest);
                preparedStatement.setInt(7, this.waist);
                preparedStatement.setInt(8, this.arms);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE users SET login=?, email=?, password=? WHERE user_id=?";
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, this.registrationDate);
                preparedStatement.setInt(3, this.weight);
                preparedStatement.setInt(4, this.height);
                preparedStatement.setInt(5, this.hips);
                preparedStatement.setInt(6, this.chest);
                preparedStatement.setInt(7, this.waist);
                preparedStatement.setInt(8, this.arms);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Niepoprawne dane.");
        }
    }


    public void deleteByDate(Connection conn, String registrationDate) throws SQLException {
        if (!registrationDate.equals("")) {
            String sql = "DELETE FROM measurements WHERE reg_date=?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, registrationDate);
            preparedStatement.executeUpdate();
            System.out.println("Wymiary z podanej daty zostały usunięte.\n");
            this.registrationDate = "";
        }
    }

    static public Measurements loadByDate(Connection conn, String registrationDate) throws SQLException {
        String sql = "SELECT * FROM measurements WHERE reg_date=?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, registrationDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Measurements loadedMeasurements = new Measurements();
            loadedMeasurements.id = resultSet.getInt("measurements_id");
            loadedMeasurements.userId = resultSet.getInt("user_id");
            loadedMeasurements.registrationDate = resultSet.getString("reg_date");
            loadedMeasurements.weight = resultSet.getInt("weight");
            loadedMeasurements.height = resultSet.getInt("height");
            loadedMeasurements.hips = resultSet.getInt("hips");
            loadedMeasurements.chest = resultSet.getInt("chest");
            loadedMeasurements.waist = resultSet.getInt("waist");
            loadedMeasurements.arms = resultSet.getInt("arms");
            System.out.println(loadedMeasurements);
            return loadedMeasurements;
        }
        return null;
    }

    static public Measurements[] loadAllMeasurements(Connection conn) throws SQLException {
        ArrayList<Measurements> measurements = new ArrayList<>();
        String sql = "SELECT * FROM measurements";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Measurements loadedMeasurements = new Measurements();
            loadedMeasurements.id = resultSet.getInt("measurements_id");
            loadedMeasurements.userId = resultSet.getInt("user_id");
            loadedMeasurements.registrationDate = resultSet.getString("reg_date");
            loadedMeasurements.weight = resultSet.getInt("weight");
            loadedMeasurements.height = resultSet.getInt("height");
            loadedMeasurements.hips = resultSet.getInt("hips");
            loadedMeasurements.chest = resultSet.getInt("chest");
            loadedMeasurements.waist = resultSet.getInt("waist");
            loadedMeasurements.arms = resultSet.getInt("arms");
            measurements.add(loadedMeasurements);
        }
        Measurements[] allMeasurements = new Measurements[measurements.size()];
        allMeasurements = measurements.toArray(allMeasurements);
        return allMeasurements;
    }

    static public Measurements[] loadAllByUserId(Connection conn, int id) throws SQLException {
        ArrayList<Measurements> measurements = new ArrayList<>();
        String sql = "SELECT * FROM measurements WHERE user_id=?;";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Measurements loadedMeasurements = new Measurements();
            loadedMeasurements.id = resultSet.getInt("measurements_id");
            loadedMeasurements.userId = resultSet.getInt("user_id");
            loadedMeasurements.registrationDate = resultSet.getString("reg_date");
            loadedMeasurements.weight = resultSet.getInt("weight");
            loadedMeasurements.height = resultSet.getInt("height");
            loadedMeasurements.hips = resultSet.getInt("hips");
            loadedMeasurements.chest = resultSet.getInt("chest");
            loadedMeasurements.waist = resultSet.getInt("waist");
            loadedMeasurements.arms = resultSet.getInt("arms");
            measurements.add(loadedMeasurements);
        }
        for (Measurements record: measurements
        ) {
            System.out.println(record.toString());
        }

        return checkIfExist(measurements);

    }

    private static Measurements[] checkIfExist(ArrayList<Measurements> measurements) {
        Measurements[] uArray = new Measurements[measurements.size()];
        if (measurements.size() == 0) {
            System.out.println("Brak takiego użytkownika w bazie danych");
            return null;
        }
        uArray = measurements.toArray(uArray);
        return uArray;
    }

    @Override
    public String toString() {
        return
                " data='" + registrationDate + '\'' +
                ", waga=" + weight + "kg" +
                ", wzrost=" + height  +"cm" +
                ", obwód bioder=" + hips + "cm" +
                ", obwod klatki piersiowej=" + chest + "cm" +
                ", obwód w talii=" + waist + "cm" +
                ", obwód ramienia=" + arms +"cm" + '\n';
    }
}
