package gatech.cs3300.watchchat;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kurt on 6/24/15.
 */
public class Message implements Comparable<Message>, Parcelable{
    public String messageId, senderId, groupId;
    public Date date;
    public String content;
    public Boolean received;

    public Message(String content, String senderId, Date date, String groupId){
        this.content = content;
        this.senderId = senderId;
        this.date = date;
        this.groupId = groupId;
    }

    public Message(JSONObject message, String groupId){
        try{
            messageId = message.getString("messageId");
            senderId = message.getString("senderId");
            date = new Date(message.getLong("dateCreated"));
            content = message.getString("content");
            groupId = message.getString("groupId");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected Message(Parcel in) {
        messageId = in.readString();
        senderId = in.readString();
        content = in.readString();
        date = new Date(in.readLong());
        groupId = in.readString();
        received = in.readByte() != 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageId);
        dest.writeString(senderId);
        dest.writeString(content);
        dest.writeLong(date.getTime());
        dest.writeString(groupId);
        dest.writeByte((byte) (received ? 1 : 0));
    }
}
