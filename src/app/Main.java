package app;


import config.ConfigDb;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = ConfigDb.openConnection();
    }
}