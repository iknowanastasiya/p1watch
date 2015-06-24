package gatech.cs3300.watchchat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kurt on 6/24/15.
 */
public class User implements Parcelable{
    public String userName, userId;

    public User(String name, String userId){
        userName = name;
        this.userId = userId;
    }

    protected User(Parcel in) {
        userName = in.readString();
        userId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public boolean equals(Object another){
        if(another instanceof User)
            return ((User) another).userId.equals(userId);
        else
            return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userId);
    }
}
