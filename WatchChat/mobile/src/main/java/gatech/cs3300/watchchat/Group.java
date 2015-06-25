package gatech.cs3300.watchchat;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kurt on 6/24/15.
 */
public class Group implements Comparable<Group>, Parcelable {

    private String groupName, groupId;
    private ArrayList<User> groupMembers;
    private ArrayList<Message> messages;
    private boolean hasUnreadMessages;

    public Group(JSONObject groupInfo){
        try{
            groupId = groupInfo.getString("groupId");
            groupName = groupInfo.getString("groupName");
        } catch (Exception e){
            e.printStackTrace();
        }

        groupMembers = new ArrayList<>();
        messages = new ArrayList<>(300);

        fetchGroupMembersFromAPI();
        fetchMessagesFromAPI();
        hasUnreadMessages = true;
    }

    public Group(String groupId, String groupName){
        this.groupId = groupId;
        this.groupName = groupName;

        groupMembers = new ArrayList<>();
        messages = new ArrayList<>(300);

        fetchGroupMembersFromAPI();
        fetchMessagesFromAPI();
        hasUnreadMessages = true;
    }

    protected Group(Parcel in) {
        groupMembers = new ArrayList<>();
        messages = new ArrayList<>(300);

        groupName = in.readString();
        groupId = in.readString();
        in.readTypedList(groupMembers, User.CREATOR);
        in.readTypedList(messages, Message.CREATOR);
        hasUnreadMessages = in.readByte() != 0;
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public Message getMostRecentMessage(){
        if(messages != null && !messages.isEmpty())
            return messages.get(0);
        return null;
    }

    public ArrayList<Message> getMessages(){return messages;}

    public String getGroupName(){return groupName;}

    public String getGroupId(){return groupId;}

    public ArrayList<User> getGroupMembers(){return groupMembers;}

    public void addMessage(Message message){
        messages.add(0,message);
    }

    public void viewedUnreadMessages(){hasUnreadMessages = false;}

    public void setHasUnreadMessages(){hasUnreadMessages = true;}

    public boolean areThereUnreadMessages(){return hasUnreadMessages;}

    public void fetchMessagesFromAPI(){
        AsyncHttpClient client = new AsyncHttpClient();
        try{
            URL url = new URL(new String("http://cs3300.elasticbeanstalk.com" + "/groups/" + groupId + "/messages"));
            client.get(url.toString(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        parseMessages(new JSONArray(new String(responseBody)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                      Throwable error) {
                    Log.e("Get messages", error.getLocalizedMessage());
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void fetchGroupMembersFromAPI(){
        AsyncHttpClient client = new AsyncHttpClient();
        try{
            URL url = new URL(new String("http://cs3300.elasticbeanstalk.com" + "/groups/" + groupId + "/users"));
            client.get(url.toString(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        parseMembers(new JSONArray(new String(responseBody)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                      Throwable error) {
                    Log.e("Get users", error.getLocalizedMessage());
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Group another) {
        if(equals(another))
            return 0;
        else if(getMostRecentMessage() != null && another.getMostRecentMessage() != null)
            return another.getMostRecentMessage().compareTo(getMostRecentMessage());
        else
            return 1;
    }

    public boolean equals(Object another){
        if(another instanceof Group) {
            return ((Group)another).groupId.equals(groupId);
        } else {
            return false;
        }
    }

    private void parseMessages(JSONArray response){
        for(int i = 0; i < response.length(); i++) {
            try {
                Message message = new Message(response.getJSONObject(i), this);
                if (!messages.contains(message))
                    messages.add(0, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseMembers(JSONArray response){
        for(int i = 0; i < response.length(); i++) {
            try {
                User user = new User(response.getJSONObject(i).getString("username"),
                        response.getJSONObject(i).getString("userId"));
                if (!groupMembers.contains(user))
                    groupMembers.add(0, user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeString(groupId);
        dest.writeTypedList(groupMembers);
        dest.writeTypedList(messages);
        dest.writeByte((byte) (hasUnreadMessages ? 1 : 0));

    }
}
