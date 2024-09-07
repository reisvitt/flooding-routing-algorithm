package model;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Store {
  private static Store instance;
  private BooleanProperty running = new SimpleBooleanProperty(false);
  private AtomicInteger messageCounterAtomic;
  private IntegerProperty messageCounter;
  private AtomicInteger runningThreads;
  private IntegerProperty velocity;

  public static Store getInstance() {
    if (instance == null) {
      instance = new Store();
    }
    return instance;
  }

  public Store() {
    this.velocity = new SimpleIntegerProperty(5);
  }

  public void start() {
    this.running.set(true);
    this.runningThreads = new AtomicInteger(0);
    this.messageCounterAtomic = new AtomicInteger(0);
    this.messageCounter = new SimpleIntegerProperty(0);
  }

  public int increment() {
    int qtd = runningThreads.incrementAndGet();
    if (qtd > 0) {
      this.running.set(true);
    }
    return qtd;
  }

  public void decrement() {
    int qtd = runningThreads.decrementAndGet();
    if (qtd <= 0) {
      this.running.set(false);
    }
  }

  public int getThreadsCount() {
    return this.runningThreads.get();
  }

  public void incrementMessageCounter() {
    int count = this.messageCounterAtomic.incrementAndGet();
    messageCounter.set(count);
  }

  public int getMessageCounter() {
    return this.messageCounterAtomic.get();
  }

  public IntegerProperty getMessageCounterProperty() {
    return this.messageCounter;
  }

  public BooleanProperty getRunningProperty() {
    return running;
  }

  public void setVelocity(int vel) {
    this.velocity.setValue(vel);
  }

  public IntegerProperty getVelocity() {
    return this.velocity;
  }
}
