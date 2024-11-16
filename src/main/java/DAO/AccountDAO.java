package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    /* 
    // Create a new account
    public Account createAccount(Account account) {
        // Get a connection from the ConnectionUtil
        Connection connection = ConnectionUtil.getConnection(); 
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        // getter methods
        try{
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
        // check if new account has been created?
            ps.executeUpdate();
        // if yes, set account with Account_id
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                    //account.setAccount_id(rs.getInt(1));
                int account_num = (int)rs.getLong(1);
                return new Account(account_num, account.getUsername(), account.getPassword()) ; // Return account with the right ID
        
            }
        } catch(SQLException e){ // check for error in creating a new account
            System.out.println("Error during creating a new account: " + e.getMessage());
        } 
        return null;
    }

*/

    public Account createAccount(Account account) {
        // Get a connection from the ConnectionUtil
        Connection connection = ConnectionUtil.getConnection(); 
        // getter methods
        try{
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
           
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
            System.out.println("createAccount Error: " + e.getMessage());
        }
        return null;
    }

    // check if username is already present
    public boolean checkIfUsernameExists(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); //return true, if username already exists
            // similar above if statment TODO; Refer flight tracker
        }catch (SQLException e){
            System.out.println("Username Already Exists error : " + e.getMessage());
        }
        return false;
    }

    // Check is account login is valid
    public boolean validateLogin(String username, String password) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login Validation Error: " + e.getMessage());
        } 
        return false;
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
            System.out.println("Getting ALL accounts error: " + e.getMessage());
        }
        return accounts;
    }

    // get a specific account by account_id
    public Account getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println("getAccountById Error: " + e.getMessage());
        }
        return null;
    }

    // Update accounts if needed
    public void updateAccount(int account_id, Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Account SET username = ?, password = ? WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt(3, account_id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Updating Account Error: " + e.getMessage());
        }
    }

    // delete any account if needed
    public void deleteAccount(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Deleting Account Error: " + e.getMessage());
        }
    }
}
