package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message insertNewMessage(int postedBy, String messageText, long timePostedEpoch){
        Message new_Message = new Message(postedBy, messageText, timePostedEpoch);
        return messageDAO.createMessage(new_Message);
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

    public boolean updateAMessage(int messageId, String newMessageText) {
        Message existingMessage = messageDAO.getMessageById(messageId);
        if (existingMessage != null) {
            Message updatedMessage = new Message(messageId, existingMessage.getPosted_by(), newMessageText, existingMessage.getTime_posted_epoch());
            messageDAO.updateMessage(messageId, updatedMessage); 
            return true; 
        }
        return false; 
    }

    public boolean deleteAMessage(int messageId){
        Message existingMessage = messageDAO.getMessageById(messageId);
        if (existingMessage != null) {
            messageDAO.deleteMessage(messageId); 
            return true; 
        }
        return false; 
    }
}
