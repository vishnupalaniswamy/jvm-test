package com.vishnu;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DBAccess {

    public static void main(String[] args) throws SQLException {
        DBAccess.connectToAndQueryDatabase(args[0], args[1], args[2]);
    }

    private static void connectToAndQueryDatabase(String username, String password, String url) throws SQLException {

        System.out.println("Connecting " + DBAccess.getDateTime());

        Connection con = DriverManager.getConnection(url, username, password);

        System.out.println("Connected " + DBAccess.getDateTime());

        execute("SELECT * FROM SELR_USR_PRFL", con);
        execute("SELECT j.id, j.name FROM APPL_CONFIG A, JSON_TABLE( A.CONFIG_JSON, '$.*.config[*]'COLUMNS(name PATH '$.Name' , id PATH '$.ID')) j WHERE CONFIG_KEY <> 'DC'", con);

    }

    private static void execute(String query, Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("Executed query " + DBAccess.getDateTime());

        int count = 0;
        while (rs.next()) {
            count++;
        }

        System.out.println("read rows " + count + " " + DBAccess.getDateTime());
    }

    private static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS z");
        Date date = new Date(System.currentTimeMillis());
        return (formatter.format(date));
    }
}
