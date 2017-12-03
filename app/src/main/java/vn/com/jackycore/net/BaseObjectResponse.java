package vn.com.jackycore.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sgvn213 on 9/21/2017.
 */
@Getter
@Setter
public class BaseObjectResponse<D> {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("data")
    @Expose
    private D data;

    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public D getData() {
        return data;
    }

    public String getMessage() {
        return message == null? "" : message;
    }
}
