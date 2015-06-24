package gatech.cs3300.watchchat;

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
public class Group implements Comparable<Group> {

    private String groupName, groupId;
    private ArrayList<User> groupMembers;
    private ArrayList<Message> messages;

    public Group(JSONObject groupInfo){
        try{
            groupName = groupInfo.getString("groupname");
            groupId = groupInfo.getString("groupId");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Group(String groupId, String groupName){
        this.groupId = groupId;
        this.groupName = groupName;

        groupMembers = new ArrayList<>();
        messages = new ArrayList<>(300);

        fetchGroupMembersFromAPI();
        fetchMessagesFromAPI();
    }

    public Message getMostRecentMessage(){
        return messages.get(0);
    }

    public ArrayList<Message> getMessages(){return messages;}

    public String getGroupName(){return groupName;}

    public String getGroupId(){return groupId;}

    public ArrayList<User> getGroupMembers(){return groupMembers;}

    public void addMessage(Message message){
        messages.add(0,message);
    }

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
        else
            return another.getMostRecentMessage().compareTo(getMostRecentMessage());
    }

    public boolean equals(Object another){
        if(another instanceof Group) {
            return ((Group)another).groupId.equals(groupName);
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
}
