package com.revature.foundation.daos;

import com.revature.foundation.models.UserRole;
import com.revature.foundation.models.Users;
import com.revature.foundation.util.connectionFactory;
import com.revature.foundation.util.exceptions.DataSourceException;
import com.revature.foundation.util.exceptions.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    // TODO attempt to centralize exception handling in service layer
    public class UserDAO implements CrudDAO<Users> {

        private final String rootSelect = "SELECT " +
                "au.id, au.first_name, au.last_name, au.email, au.username, au.password, au.role, ur.role_name " +
                "FROM app_users au " +
                "JOIN user_roles ur " +
                "ON au.role = ur.id ";

        public Users findUserByUsername(String username) {

            Users user = null;

            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE username = ?");
                pstmt.setString(1, username);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    user = new Users();
                    user.setUserId(rs.getString("id"));
                    user.setGivenName(rs.getString("first_name"));
                    user.setSurname(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(new UserRole(rs.getString("role"), rs.getString("role_name")));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return user;
        }

        public Users findUserByEmail(String email) {

            Users user = null;

            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE email = ?");
                pstmt.setString(1, email);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    user = new Users();
                    user.setUserId(rs.getString("id"));
                    user.setGivenName(rs.getString("first_name"));
                    user.setSurname(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(new UserRole(rs.getString("role"), rs.getString("role_name")));
                }

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }

            return user;

        }

        public Users findUserByUsernameAndPassword(String username, String password) {

            Users authUser = null;

            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE username = ? AND password = ?");
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    authUser = new Users();
                    authUser.setUserId(rs.getString("id"));
                    authUser.setGivenName(rs.getString("first_name"));
                    authUser.setSurname(rs.getString("last_name"));
                    authUser.setEmail(rs.getString("email"));
                    authUser.setUsername(rs.getString("username"));
                    authUser.setPassword(rs.getString("password"));
                    authUser.setRole(new UserRole(rs.getString("role"), rs.getString("role_name")));
                }

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }

            return authUser;
        }

        @Override
        public void save(Users newUser) {

            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO app_users VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, newUser.getUserId());
                pstmt.setString(2, newUser.getGivenName());
                pstmt.setString(3, newUser.getSurname());
                pstmt.setString(4, newUser.getEmail());
                pstmt.setString(5, newUser.getUsername());
                pstmt.setString(6, newUser.getPassword());
                pstmt.setString(7, newUser.getRole().getId());

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted != 1) {
                    conn.rollback();
                    throw new ResourcePersistenceException("Failed to persist user to data source");
                }

                conn.commit();

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }
        }

        @Override
        public Users getById(String id) {

            Users user = null;

            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE id = ?");
                pstmt.setString(1, id);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    user = new Users();
                    user.setUserId(rs.getString("id"));
                    user.setGivenName(rs.getString("first_name"));
                    user.setSurname(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(new UserRole(rs.getString("role"), rs.getString("role_name")));
                }

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }

            return user;

        }

        @Override
        public List<Users> getAll() {

            List<Users> users = new ArrayList<>();

            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                ResultSet rs = conn.createStatement().executeQuery(rootSelect);
                while (rs.next()) {
                    Users user = new Users();
                    user.setUserId(rs.getString("id"));
                    user.setGivenName(rs.getString("first_name"));
                    user.setSurname(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(new UserRole(rs.getString("role"), rs.getString("role_name")));
                    users.add(user);
                }
            } catch (SQLException e) {
                throw new DataSourceException(e);
            }

            return users;
        }

        @Override
        public void update(Users updatedUser) {
            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement("UPDATE app_users " +
                        "SET first_name = ?, " +
                        "last_name = ?, " +
                        "email = ?, " +
                        "username = ?, " +
                        "password = ? " +
                        "WHERE id = ?");
                pstmt.setString(1, updatedUser.getGivenName());
                pstmt.setString(2, updatedUser.getSurname());
                pstmt.setString(3, updatedUser.getEmail());
                pstmt.setString(4, updatedUser.getUsername());
                pstmt.setString(5, updatedUser.getPassword());
                pstmt.setString(6, updatedUser.getUserId());

                // TODO allow role to be updated as well

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted != 1) {
                    throw new ResourcePersistenceException("Failed to update user data within datasource.");
                }

                conn.commit();

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }
        }

        @Override
        public void deleteById(String id) {
            try (Connection conn = connectionFactory.getInstance().getConnection()) {

                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM app_users WHERE id = ?");
                pstmt.setString(1, id);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted != 1) {
                    conn.rollback();
                    throw new ResourcePersistenceException("Failed to delete user from data source");
                }

                conn.commit();

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }
        }
    }


}
