package com.gmail.st1tchqwerty;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // CREATE DATABASE mydb;
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/flatdb?serverTimezone=Europe/Kiev";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "10082002Rla";

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            try {
                // create connection
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                initDB();

                while (true) {
                    System.out.println("1: add flat");
                    System.out.println("2: view flats");
                    System.out.println("3: find flats by number of rooms");
                    System.out.println("3: find flats by all parameters");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addFlat(sc);
                            break;
                        case "2":
                            viewFlats();
                            break;
                        case "3":
                            findFlatByRooms(sc);
                            break;
                        case "4":
                            findByParameters(sc);
                            break;
                        default:
                            return;

                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void initDB() throws SQLException {
        Statement st = conn.createStatement();
        try {
            st.execute("DROP TABLE IF EXISTS Flats");
            st.execute("CREATE TABLE Flats (id INT NOT NULL " +
                    "AUTO_INCREMENT PRIMARY KEY, district VARCHAR(20) " + "NOT NULL, adress  VARCHAR(20)"
                    +" ,  area DOUBLE NOT NULL , rooms INT NOT NULL"
                    + ", price INT NOT NULL)");
        } finally {
            st.close();
        }

    }

    private static void addFlat(Scanner sc) throws SQLException {
        System.out.print("Enter flat district: ");
        String district = sc.nextLine();
        System.out.print("Enter flat adress: ");
        String adress = sc.nextLine();
        System.out.print("Enter flat area: ");
        String areas = sc.nextLine();
        double area = Double.parseDouble(areas);
        System.out.print("Enter count of flat rooms: ");
        String roomss = sc.nextLine();
        int rooms=Integer.parseInt(roomss);
        System.out.print("Enter price of flat: ");
        String prices = sc.nextLine();
        int price=Integer.parseInt(roomss);


        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Flats (district, adress, area, rooms, price) VALUES(?, ?, ?, ?, ?)");
        try {
            ps.setString(1, district);
            ps.setString(2, adress);
            ps.setDouble(3, area);
            ps.setInt(4, rooms);
            ps.setInt(5, price);

            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }



    private static void findByParameters(Scanner sc) throws  SQLException{

        System.out.println("Enter district:");
        String district = sc.nextLine();
        System.out.println("Enter address:");
        String adress = sc.nextLine();
        System.out.println("Enter area:");
        String sSquare = sc.nextLine();
        System.out.println("Enter number of rooms:");
        String sNumberOfRooms = sc.nextLine();
        System.out.println("Enter price:");
        String sPrice = sc.nextLine();
        int rooms = Integer.parseInt(sNumberOfRooms);
        double area = Integer.parseInt(sSquare);
        int price = Integer.parseInt(sPrice);



        PreparedStatement ps = conn.prepareStatement( "SELECT * FROM Flats WHERE  district = ? AND address = ? " +
                " AND rooms= ? AND area = ? AND price =? ");



        try {

            ps.setString(1 ,district );
            ps.setString(2 ,adress);
            ps.setInt(3 , rooms);
            ps.setDouble(4 , area);
            ps.setInt(5,price );
            ResultSet rs = ps.executeQuery();
            try {
                ResultSetMetaData md = rs.getMetaData();
                for (int i =1 ; i <= md.getColumnCount() ; i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs.next()){
                    for (int i=1 ; i <= md.getColumnCount(); i++)
                        System.out.print(rs.getString(i) + "\t\t");
                    System.out.println();
                }

            }finally {
                rs.close();
            }
        }finally {
            ps.close();
        }
    }


    /*

    1 2 3
    -----
    -----
    -----

     */

    private static void viewFlats() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Flats");
        try {
            ResultSet rs = ps.executeQuery();

            try {
                ResultSetMetaData md = rs.getMetaData();

                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }
    private  static void findFlatByRooms(Scanner sc) throws  SQLException{
        System.out.println("Enter number of rooms:");
        String  sNumberOfRooms = sc.nextLine();


        int numberOfRooms = Integer.parseInt(sNumberOfRooms);

        PreparedStatement ps = conn.prepareStatement( "SELECT * FROM  Flats WHERE rooms =?");

        try {
            ps.setInt(1 , numberOfRooms );
            ResultSet rs = ps.executeQuery();
            try {
                ResultSetMetaData
                        md = rs.getMetaData();
                for (int i =1 ; i <= md.getColumnCount() ; i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();

                while (rs.next()){
                    for (int i=1 ; i <= md.getColumnCount(); i++)
                        System.out.print(rs.getString(i) + "\t\t");
                    System.out.println();
                }

            }finally {
                rs.close();
            }
        }finally {
            ps.close();
        }
    }

}
