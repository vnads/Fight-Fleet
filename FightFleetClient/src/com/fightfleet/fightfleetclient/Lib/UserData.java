package com.fightfleet.fightfleetclient.Lib;

 import java.util.UUID;

import android.os.Parcel;
import android.os.Parcelable;
public class UserData implements Parcelable {
	Integer m_UserID;
	UUID m_UUID;
	String m_UserName;
	String m_Password;
	
	public UserData(String userName, String password, int userID, UUID uuid){
		m_UUID = uuid;
		m_UserName = userName;
		m_UserID = userID;
		m_Password = password;
	}
	
	public String getPassword(){
		return m_Password;
	}
	
	public Integer getUserID() {
		return m_UserID;
	}
	public String getUserName(){
		return m_UserName;
	}
	public UUID getUUID(){
		return m_UUID;
	}

	/**
	 * Per documentation, we can ignore this
	 * @return
	 */
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * This allows us to put this type of object into an intent extra map.
	 * Very useful.
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(m_UserID);
		dest.writeString(m_UserName);
		dest.writeString(m_Password);
		dest.writeSerializable(m_UUID);		
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private UserData(Parcel in) {
        m_UserID = in.readInt();
        m_UserName = in.readString();
        m_Password = in.readString();
        m_UUID = (UUID)in.readSerializable();
    }
}
