package sistem.model.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//=> Função Keep Alive para evitar o fechamento da conexão por inatividade
public class ConnectionKeepAlive {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void startKeepAlive(Connection connection) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (connection != null && !connection.isClosed()) {
                    try (Statement st = connection.createStatement()) {
                        st.execute("SELECT 1"); // Keep-alive query
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error during keep-alive query: " + e.getMessage());
            }
        }, 0, 5, TimeUnit.MINUTES); // Adjust the interval as needed
    }
}