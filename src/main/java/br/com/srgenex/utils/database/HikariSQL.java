package br.com.srgenex.utils.database;

import br.com.srgenex.utils.GXUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("unused")
@Getter
public class HikariSQL {

    private DataSource dataSource;
    private ExecutorService executor;
    @Getter
    private static HikariSQL instance;

    @SneakyThrows
    public HikariSQL() {
        instance = this;
        this.executor = Executors.newCachedThreadPool();
    }

    public HikariSQL load(File data) {
        try {
            Class.forName("org.sqlite.JDBC");
            File dbFile = new File(data, "database.db");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:" + dbFile.getPath());
            config.setMaximumPoolSize(1);
            config.setConnectionTestQuery("SELECT 1");
            this.executor = Executors.newSingleThreadExecutor();
            this.dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            GXUtils.getInstance().getLogger().severe("Failed to load the SQLite database.");
        }
        return this;
    }

    public HikariSQL load(ConfigurationSection section) {
        String host = section.getString("host");
        String user = section.getString("user");
        String password = section.getString("password");
        String database = section.getString("database");
        int port = section.getInt("port");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC");
            config.setUsername(user);
            config.setPassword(password);

            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(10000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            config.setConnectionTestQuery("SELECT 1");

            this.dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            GXUtils.getInstance().getLogger().severe("No MySQL driver found.");
        }
        return this;
    }

    @SneakyThrows
    public void update(String sql, Object... vars) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = prepareStatement(connection, sql, vars)) {
            ps.execute();
        }
    }

    public void execute(String sql, Object... vars) {
        executor.execute(() -> update(sql, vars));
    }

    @SneakyThrows
    private PreparedStatement prepareStatement(Connection con, String query, Object... vars) {
        PreparedStatement ps = con.prepareStatement(query);
        for (int i = 0; i < vars.length; i++) {
            ps.setObject(i + 1, vars[i]);
        }
        return ps;
    }

    @SneakyThrows
    public CachedRowSet query(String query, Object... vars) {
        Future<CachedRowSet> future = executor.submit(() -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement ps = prepareStatement(connection, query, vars);
                 ResultSet rs = ps.executeQuery()) {

                CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                crs.populate(rs);

                return crs.next() ? crs : null;
            }
        });
        return future.get();
    }

    public void createTable(String table, String columns) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement stm = connection.createStatement()) {
            stm.execute("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
        }
    }

}
