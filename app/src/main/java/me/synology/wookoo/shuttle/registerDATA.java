package me.synology.wookoo.shuttle;

import com.google.gson.annotations.SerializedName;

public class registerDATA {
    @SerializedName("status")
    private boolean status;

    public boolean getStatus(){
        return status;
    }


}
