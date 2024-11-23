package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // default constructor
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    // insert a new account
    public Account insertNewAccount(Account account){
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            return null;
        }
        List<Account> a = accountDAO.getAllAccounts();
        for (Account b: a){
            if(b.getAccount_id() == account.getAccount_id()){
                return null;
            }
        }
        return accountDAO.createAccount(account);
    }

    //is user login valid?
    public Account realUser(Account account){
        Account user_account = accountDAO.validateLogin(account);
        if(user_account != null){
            return user_account;
        }
        return null;
    }
}
