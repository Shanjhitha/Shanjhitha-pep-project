package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    // default constructor
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // insert a new message
    public Message insertNewMessage(Message message){
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && accountDAO.getAccountById(message.getPosted_by())){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    // search messagge by its id number
    public Message searchMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    // look up all messages
    public List<Message> searchAllMessages(){
        return messageDAO.getAllMessages();
    }

    // retrieve a specific message by posted_by 
    public List<Message> searchSpecificMessage(int posted_by){
        return messageDAO.getSpecificMessage(posted_by);
    }

    // update a message
    public Message updateAMessage(int messageId, Message newMessageText) {
        Message existingMessage = messageDAO.getMessageById(messageId);
        // API rules followed here
        if (existingMessage != null && !newMessageText.getMessage_text().isBlank() && newMessageText.getMessage_text().length() < 255 ) {
            messageDAO.updateMessage(messageId, newMessageText);
            return messageDAO.getMessageById(messageId);
        }
        return null; 
    }

    // delete a message
    public Message deleteAMessage(int messageId){
        Message existingMessage = messageDAO.getMessageById(messageId);
        if (existingMessage != null) {
            return messageDAO.deleteMessage(messageId); 
        }
        return null; 
    }
}
