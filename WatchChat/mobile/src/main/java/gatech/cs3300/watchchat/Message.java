package gatech.cs3300.watchchat;
import org.json.JSONObject;
import java.util.Date;

/**
 * Created by kurt on 6/24/15.
 */
public class Message implements Comparable<Message>{
    public String messageId, senderId;
    public String author;
    public Date date;
    public String content;
    public Boolean received;
    public Group group;

    public Message(String content, String senderId, Date date, Group group){
        this.content = content;
        this.senderId = senderId;
        this.date = date;
        this.group = group;
        author = getAuthorFromGroupMembers();
    }

    public Message(JSONObject message, Group group){
        try{
            messageId = message.getString("messageId");
            senderId = message.getString("senderId");
            date = new Date(message.getLong("dateCreated"));
            content = message.getString("content");
            this.group = group;
            author = getAuthorFromGroupMembers();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getAuthorFromGroupMembers(){
        int index = group.getGroupMembers().indexOf(new User("", senderId));
        if(index != -1)
            return group.getGroupMembers().get(index).userName;
        else
            return "";
    }

    @Override
    public int compareTo(Message another) {
        if(date.after(another.date))
            return 1;
        else if(date.before(date))
            return -1;
        else if(messageId.equals(another.messageId))
            return 0;
        else
            return 1;
    }
}
