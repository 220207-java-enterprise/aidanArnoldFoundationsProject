package com.revature.foundation.daos;

        import com.revature.foundation.models.Users;
        import com.revature.foundation.util.exceptions.ResourcePersistenceException;

        import java.io.*;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;

public class UsersDAO implements CrudDAO<Users> {

    private Connection conn;

    public UsersDAO(Connection conn) {
        this.conn = conn;
    }

    public Users findUserByUsername(String username) {
        return null;
    }

    public Users findUserByEmail(String email) {
        return null;
    }

    public Users findUserByUsernameAndPassword(String username, String password) {


        return null;

    }

    @Override
    public void save(Users newUser) {
        try {
            File usersDataFile = new File("data/users.txt");
            FileWriter dataWriter = new FileWriter(usersDataFile, true);
            dataWriter.write(newUser.toFileString() + "\n");
            dataWriter.close();
        } catch (IOException e) {
            throw new ResourcePersistenceException("An error occurred when accessing the data file.");
        }
    }

    @Override
    public Users getById(String id) {
        String query = "select * from ers_users where user_id = ?";
        Users user = new Users();

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                user.setUserId(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGivenName(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIsActive(rs.getString("is_active"));
                user.setRoleId(rs.getString("role_id"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    List<Users> total = new ArrayList<>();
    @Override
    public List<Users> getAll() {
        String queryAll = "select * from ers_users;";
        Users user = new Users();


        try {
            PreparedStatement ps = conn.prepareStatement(queryAll);
//            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

//            while()
            while(rs.next()){
                user.setUserId(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGivenName(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIsActive(rs.getString("is_active"));
                user.setRoleId(rs.getString("role_id"));


                total.add(user);
//                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    public void update(Users updatedObject) {
        int i = Integer.parseInt(updatedObject.getUserId());
        String queryUpdate = "UPDATE ers_users\n" +
                "SET username = 'jakesnake2', email = 'abcd@gmail.com', password = 'pass123', given_name = 'Jake',\n" +
                "surname = 'Snake', is_active = true, role_id = 1\n" +
                "WHERE user_id = '" + i + "';";
        try {
            PreparedStatement ps = conn.prepareStatement(queryUpdate);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(String id) {
        String queryDelete = "delete from ers_users where user_id = ?;";


        try {
            PreparedStatement ps = conn.prepareStatement(queryDelete);
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
