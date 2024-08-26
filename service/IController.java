package service;

import model.Package;
import model.Router;

public interface IController {
  public void send(Router from, Router router, Package packge);
}
