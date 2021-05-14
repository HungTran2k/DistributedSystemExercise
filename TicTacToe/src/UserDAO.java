public interface UserDAO {
    public int insertUserRegistrationToDB(User user);
    public User getUserFromDB(String username, String password);
}
