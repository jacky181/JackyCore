package vn.com.jackycore.net;

import java.util.Map;

import io.reactivex.Flowable;

/**
 * Created by sgvn213 on 9/22/2017.
 */

public interface PostHashMapWithIdRequestUseCase<T> {
    Flowable<T> request(String id, Map<String, String> param);
}
