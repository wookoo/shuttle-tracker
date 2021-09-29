package me.synology.wookoo.shuttle;

import com.google.gson.annotations.SerializedName;

public class registerDATA {
    @SerializedName(value = "status")
    private boolean status;

    public boolean getStatus(){
        return this.status;
    }


}
