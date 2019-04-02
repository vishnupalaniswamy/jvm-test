package com.vishnu

import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.SQLException
import java.text.SimpleDateFormat

object DBAccess {

    private val dateTime: String
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS z")
            val date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }

    @Throws(SQLException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        DBAccess.connectToAndQueryDatabase(args[0], args[1], args[2])
    }

    @Throws(SQLException::class)
    private fun connectToAndQueryDatabase(username: String, password: String, url: String) {

        println("Connecting ${DBAccess.dateTime}")
        val con = DriverManager.getConnection(url, username, password)
        println("Connected ${DBAccess.dateTime}")

        execute(
            query = "SELECT * FROM SELR_USR_PRFL",
            con = con
        )
        execute(
            query = "SELECT j.id, j.name FROM APPL_CONFIG A, JSON_TABLE( A.CONFIG_JSON, '$.*.config[*]'COLUMNS(name PATH '$.Name' , id PATH '$.ID')) j WHERE CONFIG_KEY <> 'DC'",
            con = con
        )

    }

    @Throws(SQLException::class)
    private fun execute(query: String, con: Connection) {
        val stmt = con.createStatement()
        println("Current fetchSize ${stmt.fetchSize}")
        stmt.fetchSize = 100
        println("New fetchSize ${stmt.fetchSize}")
        val rs = stmt.executeQuery(query)

        println("Executed query ${DBAccess.dateTime}")

        var count = 0
        while (rs.next()) {
            count++
        }

        println("read rows $count ${DBAccess.dateTime}")
    }
}
