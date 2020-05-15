package edu.slcc.asdv.bl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface Database {

    /**
     * Returns a Connection to the database or null if no connection is
     * established.
     *
     * @param databaseName
     * @param username
     * @param password
     * @param driver
     * @return
     */
    public Connection getConnection(String databaseName, String username, String password, String driver);

    /**
     * Selects the projected columns of a table where the AND condition of the
     * WHERE clause is true.
     *
     * @param tableName table name
     * @param projectionFields fields in SELECT clause (projected columns)
     * @param whereFieldsNValues fields in WHERE clause, key is the field name (
     * LHS ) and value of the field (RHS), For example snumber='s1', status >
     * "10"
     * @param operators the operators that apply to whereFieldsNValues. They are
     * positioned between the LHS and RHS of the whereFieldsNValues parameter.
     * The operators are always half the size of the whereFieldsNValues. That
     * is, the first operator applies to the first two entries of the map, the
     * second operator to the 3rd and fourth entry of the map and so on.
     * @return the projected columns of a table, after the WHERE clause applies
     * by ANDing all entries of the map (whereFieldsNValues). If an SQL
     * exception is thrown return NULL if an error (exception occurs).
     * 
     * SELECT snumber, sname, city
     * FROM supplier
     * WHERE snumber="s1" AND status > 20;
     */
    public List<String> selectFieldsFromTableWhereFields(String tableName,
            List<String> projectionFields,
            Map<String, String> whereFieldsNValues,
            List<String> operators);

    /**
     * Returns the list of all rows of the table "tableName".
     *
     * @param tableName
     * @return
     */
    public List<String> selectAllFromTable(String tableName);

    /**
     * Returns true if the table exists in the database, false otherwise.
     *
     * @param tableName
     * @return
     */
    boolean isTable(String tableName);

    /**
     * Closes connection to the current database.
     *
     * @param c
     */
    public void closeConnection(Connection c);
}
