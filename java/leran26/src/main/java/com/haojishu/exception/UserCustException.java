package com.haojishu.exception;

public class UserCustException extends RuntimeException {
  /** * 错误信息是不是应该包含错误路径，错误信息呢？看代码 */
  private String url;

  private String msg;

  public UserCustException(String url, String msg) {
    super("msg");
    this.url = url;
    this.msg = msg;
  }

  public String getUrl() {
    return url;
  }

  public String getMsg() {
    return msg;
  }
}
