package server.model.domain;

public class UserRequest
{
  private String request;

  public UserRequest(String request)
  {
    this.request = request;
  }

  public String getRequest()
  {
    return request;
  }
}