package vn.com.jackycore.architech;

import android.content.Context;

/**
 * Created by Jacky Hua on 9/21/2017.
 */

public abstract class BaseUseCase {

    protected Context context;


    public BaseUseCase(Context context) {
        this.context = context;
    }
}
