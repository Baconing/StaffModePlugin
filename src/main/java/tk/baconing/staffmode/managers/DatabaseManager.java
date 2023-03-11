package tk.baconing.staffmode.managers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.baconing.staffmode.StaffMode;
import tk.baconing.staffmode.entities.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static String databaseUrl;
    private static ConnectionSource connectionSource;
    private static Dao<User, Integer> userDao;
    private static Map<Player, User> cache;

    public static void initialize(String databaseUrl) throws SQLException {
        cache = new HashMap<>();
        if (databaseUrl.equalsIgnoreCase("file")) {
            String url = "jdbc:sqlite:" + StaffMode.get().getDataFolder().toPath() + "/database.db";
            DatabaseManager.databaseUrl = url;
            StaffMode.get().getLogger().info(url);
        } else {
            DatabaseManager.databaseUrl = databaseUrl;
        }
        connectionSource = new JdbcPooledConnectionSource(DatabaseManager.databaseUrl);
        userDao = DaoManager.createDao(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
    }

    public static ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public static Dao<User, Integer> getUserDao() {
        return userDao;
    }

    public static String getDatabaseUrl() {
        return databaseUrl;
    }

    public static void setDatabaseUrl(String databaseUrl) {
        DatabaseManager.databaseUrl = databaseUrl;
    }

    public static Map<Player, User> getCache() {
        return cache;
    }

    public static class DatabaseQueries {
        public static void createUser(@NotNull User user) {
            userDao = DatabaseManager.getUserDao();
            try {
                userDao.create(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static void updateUser(@NotNull User user) {
            userDao = DatabaseManager.getUserDao();
            try {
                userDao.update(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @NotNull
        public static User getUser(@NotNull Player p) {
            if (cache.containsKey(p)) return cache.get(p);
            if (getUserByName(p.getName()) == null && getUserByUUID(p.getUniqueId().toString()) == null) {
                // create new user
                User u = new User();
                u.setUsername(p.getName());
                u.setUuid(p.getUniqueId().toString());
                u.setStaffMode(false);
                createUser(u);
                return u;
            }
            User u = getUserByName(p.getName()) != null ? getUserByName(p.getName()) : getUserByUUID(p.getUniqueId().toString());
            assert u != null;
            return u;
        }

        @Nullable
        public static User getUser(@NotNull Integer id) {
            return getUserByID(id);
        }


        @Nullable
        private static User getUserByName(@NotNull String name) {
            userDao = DatabaseManager.getUserDao();
            try {
                QueryBuilder<User, Integer> qb = userDao.queryBuilder();
                qb.where().eq("username", name);
                return userDao.queryForFirst(qb.prepare());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Nullable
        private static User getUserByUUID(String uuid) {
            userDao = DatabaseManager.getUserDao();
            try {
                QueryBuilder<User, Integer> qb = userDao.queryBuilder();
                qb.where().eq("uuid", uuid);
                return userDao.queryForFirst(qb.prepare());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Nullable
        private static User getUserByID(int id) {
            //TODO
            userDao = DatabaseManager.getUserDao();
            try {
                QueryBuilder<User, Integer> qb = userDao.queryBuilder();
                qb.where().eq("id", id);
                return userDao.queryForFirst(qb.prepare());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
