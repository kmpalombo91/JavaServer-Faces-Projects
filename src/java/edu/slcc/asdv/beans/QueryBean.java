package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Queries;
import java.util.*;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "queryBean")
@RequestScoped
public class QueryBean {

    private Queries queries;
    List<String> table;
    String tableName;

    public QueryBean() {
        queries = new Queries();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getTable() {
        return table;
    }

    public void queries() {
        if (!tableName.isEmpty()) {
            if (queries.isTable(tableName)) {
                table = queries.selectAllFromTable(tableName);
            } else {
                table = new ArrayList();
                table.add("Table " + tableName + " doesn't exist in the database");
            }
        } else {
            table = new ArrayList();
            table.add("No name for table?");
        }
    }
}
