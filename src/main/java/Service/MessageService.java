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

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message insertNewMessage(Message message){
       // Message new_Message = new Message();
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && accountDAO.getAccountById(message.getPosted_by())){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public Message searchMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public List<Message> searchAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> searchSpecificMessage(int posted_by){
        return messageDAO.getSpecificMessage(posted_by);
    }

    public Message updateAMessage(int messageId, Message newMessageText) {
        Message existingMessage = messageDAO.getMessageById(messageId);
        if (existingMessage != null && !newMessageText.getMessage_text().isBlank() && newMessageText.getMessage_text().length() < 255 ) {
            //Message updatedMessage = new Message(messageId, existingMessage.getPosted_by(), newMessageText, existingMessage.getTime_posted_epoch());
            messageDAO.updateMessage(messageId, newMessageText);
            return messageDAO.getMessageById(messageId);
        }
        return null; 
    }

    public Message deleteAMessage(int messageId){
        Message existingMessage = messageDAO.getMessageById(messageId);
        if (existingMessage != null) {
            return messageDAO.deleteMessage(messageId); 
        }
        return null; 
    }
}
