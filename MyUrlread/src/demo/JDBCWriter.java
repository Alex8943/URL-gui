package demo;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class JDBCWriter {

    private Connection connection = null;

    public boolean setconnection(){ // Denne metode tjekker om der er connection til vores database
        final String url = "jdbc:mysql://localhost:3306/urlread?serverTimezone=UTC";
        boolean bres = false;
        try {
            connection = DriverManager.getConnection(url, "Yrsa", "Y");
            bres = true;

        } catch (SQLException iorr){
            System.out.println("Vi fik IKKE connection " + iorr.getMessage());
        }
            return bres;
    }


    public int deleteRow(String aUrl, String aWord){
        String deStr = "DELETE FROM urlreads where url like ? and line like ?";
        PreparedStatement preparedStatement;
        int res = +1;
        try {
            preparedStatement = connection.prepareStatement(deStr);
            preparedStatement.setString(1,"%" + aUrl + "%");
            preparedStatement.setString(2,"%" + aWord + "%");
            res = preparedStatement.executeUpdate();
            System.out.println("Line deleted = " + res);

        } catch (SQLException sqlErr){
            System.out.println("Error in delete = " + sqlErr.getMessage());
        }
        return res;
    }

    public Vector<String> getLines(String aUrl, String aWord){
        String searchStr = "SELECT left(line, 50) as line FROM urlreads where url like ? and line like ? LIMIT 28";
        PreparedStatement preparedStatement;
        Vector<String> v1 = new Vector<>();
        try{
            preparedStatement = connection.prepareStatement(searchStr);
            preparedStatement.setString(1,"%" + aUrl + "%");
            preparedStatement.setString(2,"%" + aWord + "%");
            System.out.println(searchStr);
            ResultSet reset = preparedStatement.executeQuery();
            String str1;
           while (reset.next()){
               str1 = "" + reset.getObject("Line");
               v1.add(str1);
           }

        }catch (SQLException sqlerr){
            System.out.println("Error in select " + sqlerr.getMessage());
        }
        return v1;
    }

    public int searchDB(String aUrl, String aWord) {
        String searchStr = "SELECT count(*) FROM urlreads where url like ? and line like ?";
        PreparedStatement preparedStatement;
        int res = -1;
        try{
            preparedStatement = connection.prepareStatement(searchStr);
            preparedStatement.setString(1,"%" + aUrl + "%");
            preparedStatement.setString(2,"%" + aWord + "%");
            System.out.println(searchStr);
            ResultSet reset = preparedStatement.executeQuery();
            if (reset.next()) {
                String str = "" + reset.getObject(1);
                res = Integer.parseInt(str);
                System.out.println("Fundet antal = " + res);
            }
        }catch (SQLException sqlErr){
            System.out.println("Fejl i search " + sqlErr.getMessage());
        }
        return res;
    }

    public int writeLines(String aUlr, ArrayList<String> aLst) {
        String insstr = "INSERT INTO urlreads(url, line, linelen) values (?, ?, ?)";
        PreparedStatement preparedStatement;
        int res = 0;
        for (String line : aLst) {
            try {
                preparedStatement = connection.prepareStatement(insstr);
                preparedStatement.setString(1, aUlr);
                preparedStatement.setString(2, line);
                preparedStatement.setString(3, "" + line.length());
                int rowcount = preparedStatement.executeUpdate();
                //System.out.println("Indsat række=" + rowcount);
                res = res + rowcount;

            } catch (SQLException sqlerr) {
                System.out.println("Fejl i INSERT " + sqlerr.getMessage());
            }
        }
        return res;
    }




}
