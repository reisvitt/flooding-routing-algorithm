package service;

import model.Package;
import model.Router;

public interface IRouter {
  public void send(Package packge);

  public void receive(Router from, Package packge);
}
