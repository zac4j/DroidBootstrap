package com.zac4j.droidbootstrap.util;

import android.text.TextUtils;
import com.zac4j.droidbootstrap.data.remote.ApiResponse;
import com.zac4j.droidbootstrap.ui.base.MvpView;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * RxJava Helper Utility Class
 * Created by zac on 16-7-21.
 */

public class RxUtils {

  public static <T> Observable.Transformer<T, T> applySchedulers() {
    return new Observable.Transformer<T, T>() {
      @Override public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public static <T extends ApiResponse> Observable.Transformer<Response<T>, Response<T>> preHandle(
      final MvpView view) {
    return new Observable.Transformer<Response<T>, Response<T>>() {
      @Override public Observable<Response<T>> call(Observable<Response<T>> observable) {
        return observable.filter(new Func1<Response<T>, Boolean>() {
          @Override public Boolean call(Response<T> tResponse) {
            boolean isSuccess = tResponse.isSuccessful();
            int code = tResponse.code();
            T body = tResponse.body();
            if (!isSuccess) {
              view.showNotice("网络无法连接,请求失败:" + code);
            } else if (!body.isSuccess() && !TextUtils.isEmpty(body.getMsg())) {
              view.showNotice(body.getMsg());
            }
            Timber.d(body.getMsg());
            return isSuccess;
          }
        }).doOnError(new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
            String message = throwable.getMessage();
            Timber.e(throwable, message);
            if (!TextUtils.isEmpty(message)) {
              view.showNotice("网络请求失败.请重试!");
            }
          }
        });
      }
    };
  }
}
