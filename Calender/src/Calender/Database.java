package Calender;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private final Statement statement;

    public Database(JFrame frame, String USER, String PASS) {
        try {
            String url = "jdbc:mysql://localhost:3306/";
            Connection connection = DriverManager.getConnection(url, USER, PASS);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String createDB = "CREATE DATABASE IF NOT EXISTS `Calendar`;";
            statement.execute(createDB);
            String use = "use `Calendar`;";
            statement.execute(use);
            String createTB = "CREATE TABLE IF NOT EXISTS `calendar`.`calendar` (`ID` INT NOT NULL AUTO_INCREMENT , `Title` TEXT NOT NULL , `Description` TEXT NOT NULL , " +
                    "`Date` TEXT NOT NULL , `Time` TEXT NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;";
            statement.execute(createTB);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame,"Failed to connect database.\nPlease check your user name or password","Connection Failure",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Event> getEvents(String date) {
        ArrayList<Event> events = new ArrayList<>();
        String select = "SELECT * FROM `calendar` WHERE `Date` = '"+date+"';";
        try{
            ResultSet rs = statement.executeQuery(select);
            while ((rs.next())) {
                Event e = new Event();
                e.setID(rs.getInt("ID"));
                e.setTitle(rs.getString("Title"));
                e.setDescription(rs.getString("Description"));
                e.setDateTimeFromString(rs.getString("Date")+" | "+rs.getString("Time"));
                events.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public boolean hasEvent(String date) {
        boolean hasEvent;
        String select = "SELECT * FROM `calendar` WHERE `Date` = '"+date+"';";
        try{
            ResultSet rs = statement.executeQuery(select);
            hasEvent = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasEvent;
    }

    public void createEvent(Event e) {
        String insert = "INSERT INTO `calendar`(`Title`, `Description`, `Date`, `Time`)" +
                " VALUES ('"+e.getTitle()+"','"+e.getDescription()+"'," +
                "'"+e.getDateToString()+"','"+e.getTimeToString()+"')";

        try {
            statement.execute(insert);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateEvent(Event e) {
        String update = "UPDATE `calendar` SET `Title`='"+e.getTitle()+"'," +
                "`Description`='"+e.getDescription()+"',`Date`='"+e.getDateToString()+"'," +
                "`Time`='"+e.getTimeToString()+"' WHERE `ID` = "+e.getID()+";";
        try {
            statement.execute(update);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteEvent(int ID) {
        String delete = "DELETE FROM `calendar` WHERE `ID` = "+ID+";";
        try {
            statement.execute(delete);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
