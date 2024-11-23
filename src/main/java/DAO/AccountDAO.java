package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    // create a new account
    public Account createAccount(Account account) {
        // Get a connection from the ConnectionUtil
        Connection connection = ConnectionUtil.getConnection(); 
        try{
            // SQL query
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            // Prepared statement
            PreparedStatement ps = connection.prepareStatement(sql);
           // retrieve the generated keys 
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // set parameters
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
           // execute Query
            int affectedRows = ps.executeUpdate();
        // if yes, set account with Account_id
            if (affectedRows > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    int new_account_id = rs.getInt(1);
                    return new Account(new_account_id, account.getUsername(), account.getPassword());
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // check if username is already present 
    public Account checkIfUsernameExists(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            //return rs.next();  
            while (rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Check is account login is valid
    public Account validateLogin(Account user){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        } 
        return null;
    }

    // get all accounts
    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
    
            while(rs.next()){ 
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    // get a specific account by account_id
    public boolean getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
