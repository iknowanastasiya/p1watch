package gatech.cs3300.watchchat;

/**
 * Created by kurt on 6/24/15.
 */
public class User {
    public String userName, userId;

    public User(String name, String userId){
        userName = name;
        this.userId = userId;
    }

    public boolean equals(Object another){
        if(another instanceof User)
            return ((User) another).userId.equals(userId);
        else
            return false;
    }
}
