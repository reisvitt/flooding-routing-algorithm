package service;

import model.Package;
import model.Router;

public class Router1 implements IRouter {
  private Router router;

  public Router1(Router router) {
    this.router = router;
  }

  @Override
  public void send(Package packge) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'send'");
  }

  @Override
  public void receive(model.Router from, Package packge) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'receive'");
  }

}
