package gatech.cs3300.watchchat;

import java.util.Date;

public class GroupMessage {
    public String author;
    public Date date;
    public String content;
    public Boolean received;

    public static GroupMessage received(String content) {
        GroupMessage message = new GroupMessage();
        message.content = content;
        message.received = true;

        return message;
    }

    public static GroupMessage sent(String content) {
        GroupMessage message = new GroupMessage();
        message.content = content;
        message.received = false;

        return message;
    }
}
