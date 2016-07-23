package com.zac4j.droidbootstrap.data.remote;

import java.io.Serializable;

/**
 * Base Remote Response Class
 * Created by zac on 16-7-21.
 */

public class ApiResponse implements Serializable {
  private String msg;
  private boolean success;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
