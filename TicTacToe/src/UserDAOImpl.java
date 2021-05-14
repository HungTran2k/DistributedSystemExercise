import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements UserDAO{

    Connection conn;
    PreparedStatement ps;

    @Override
    public int insertUserRegistrationToDB(User user) {
        int status = 0;
        try {
            conn = DBConnection.getConnection();
            ps=conn.prepareStatement("insert into user (username, password) values(?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            status = ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    @Override
    public User getUserFromDB(String username, String password) {
        User user = new User();
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement("Select * from user where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                user.setUsername(rs.getString(1));
                user.setPassword(rs.getString(2));
            }

        } catch(Exception e) {
            System.out.println(e);
        }
        return user;
    }

}
