import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String query = "SELECT r.*, m.* " +
                "FROM registers r " +
                "LEFT JOIN messages m ON m.id = r.code " +
                "ORDER BY r.creation_datetime;";

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://root@localhost/secure_file_system");

            if (conn != null) {
                ResultSet res = conn.createStatement().executeQuery(query);
                List<String> logs = new ArrayList<>();
                while (res.next()) {
                    String msg = res.getString("m.message");
                    String login = res.getString("r.login");
                    String arq = res.getString("r.arq");
                    String date = res.getString("r.creation_datetime");
                    int code = res.getInt("m.id");

                    String log = "Timestamp: " + date + " ----- Message: " + code + " - " + msg.replace("<login_name>", login).replace("<arq_name>", arq);
                    logs.add(log);
                }

                for (String log : logs) {
                    System.out.println(log);
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao conectar com o banco: \n" + e.getMessage());
        }
    }
}
