package com.zac4j.droidbootstrap.util;

import android.text.TextUtils;
import com.zac4j.droidbootstrap.data.remote.ApiResponse;
import com.zac4j.droidbootstrap.ui.base.MvpView;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
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

  /**
   * 解绑 subscription
   * @param subscription 订阅者对象
   */
  public static void unsubscribe(Subscription subscription) {
    if (subscription == null || subscription.isUnsubscribed()) {
      return;
    }
    subscription.unsubscribe();
  }

  /**
   * 配置订阅对象工作线程
   * @param <T> Observable 对象
   * @return 已配置好线程的 Observable对象
   */
  public static <T> Observable.Transformer<T, T> applySchedulers() {
    return new Observable.Transformer<T, T>() {
      @Override public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * 预处理服务端返回的请求数据
   * @param view mvp view对象
   * @param <T> 服务端返回的Observable 对象
   * @return 预处理后的 Observable 对象
   */
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
              view.showError("网络无法连接,请求失败:" + code);
            } else if (!body.isSuccess() && !TextUtils.isEmpty(body.getMsg())) {
              view.showError(body.getMsg());
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
              view.showError("网络请求失败.请重试!");
            }
          }
        });
      }
    };
  }
}
