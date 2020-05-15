package edu.slcc.asdv.bl;

import java.sql.*;
import java.util.*;

public class Queries implements Database {

    String databaseName = "supplierpartsclass";
    String userName = "root";
    String password = "";
    String driver = "com.mysql.jdbc.Driver";

    @Override
    public Connection getConnection(String databaseName, String userName, String password, String driver) {

        //String URL2 = "com.mysql.cj.jdbc.Driver";
        Connection con = null;
        try {// Load Sun's jdbc driver
            Class.forName(driver).newInstance();
            System.out.println("JDBC Driver loaded!");
        } catch (Exception e) // driver not found
        {
            System.err.println("Unable to load database driver");
            System.err.println("Details : " + e);
            return null;
        }
        String ip = "localhost"; //internet connection
        String url = "jdbc:mysql://" + ip + ":3306/" + databaseName;
        try {
            con = DriverManager.getConnection(url, userName, password);
            con.setReadOnly(false);
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
        System.out.println("connection successfull");
        return con;
    }

    @Override
    public List<String> selectAllFromTable(String tableName) {

        Connection con = getConnection(this.databaseName, this.userName, this.password, this.driver);
        if (con == null) {
            System.out.println("cannot connect to database");
            return null;
        }
        List<String> table = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlStr = "SELECT  *  FROM " + tableName;
        try {
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            //execute
            rs = ps.executeQuery();
            int numberOfCollumns = rs.getMetaData().getColumnCount();
            System.out.println("before entering while");

            while (rs.next()) {
                int parmIndex = 1;
                String oneRaw = "";
                while (parmIndex < numberOfCollumns) {
                    oneRaw += rs.getObject(parmIndex++).toString() + "  ";
                }
                table.add(oneRaw);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                closeConnection(con);
                // close the resources 
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return table;
    }

    @Override
    public boolean isTable(String tableName) {
        Connection con = getConnection(this.databaseName, this.userName, this.password, this.driver);

        try {
            DatabaseMetaData metaData = con.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = metaData.getTables(con.getCatalog(), null, "%", types);

            while (resultSet.next()) {
                if (tableName.compareToIgnoreCase(resultSet.getString("TABLE_NAME")) == 0) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                closeConnection(con);
                // close the resources 
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void closeConnection(Connection c) {

        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    
    @Override
    public List<String> selectFieldsFromTableWhereFields(String tableName, List<String> projectionFields, Map<String, String> whereFieldsNValues, List<String> operators) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
