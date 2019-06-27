package datanapps.caching.retrofit.network.users.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseUser {

    @SerializedName("userlist")
    @Expose
    private List<User> userlist = null;
    @SerializedName("totalrecords")
    @Expose
    private Integer totalrecords;
    @SerializedName("message")
    @Expose
    private String message;

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }

    public Integer getTotalrecords() {
        return totalrecords;
    }

    public void setTotalrecords(Integer totalrecords) {
        this.totalrecords = totalrecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
