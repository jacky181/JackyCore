package vn.com.jackycore.net;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sgvn213 on 9/21/2017.
 */
@Getter
@Setter
public class BaseListObjectResponse<D> {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<D> list;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public List<D> getData() {
        return list;
    }
}
