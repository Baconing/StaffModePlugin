package tk.baconing.staffmode.managers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.baconing.staffmode.entities.User;

import javax.annotation.Nullable;
import java.sql.SQLException;

public class DatabaseManager {
    private static String databaseUrl;
    private static ConnectionSource connectionSource;
    private static Dao<User, Integer> userDao;

    public static void initialize(String databaseUrl) throws SQLException {
        DatabaseManager.databaseUrl = databaseUrl;
        connectionSource = new JdbcConnectionSource(databaseUrl);
        userDao = DaoManager.createDao(connectionSource, User.class);
        // TODO: setup custom table name
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

    public static class DatabaseQueries {

        public static void enableStaffMode(@NotNull User user, @NotNull Player player, @NotNull String serializedData) {
            user.setStaffMode(true);
            user.setSerializedData(serializedData);
        }

        public static String disableStaffMode(@NotNull User user) {
            user.setStaffMode(false);
            return user.getSerializedData();
        }

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
