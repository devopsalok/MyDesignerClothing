package com.mydesignerclothing.mobile.android.commons.core;


import java.util.Vector;

public class ThreadQueueEngine {

  /* Initialize-on-Demand, Holder Class Idiom */
  private static class InstanceHolder {
    static ThreadQueueEngine instance = new ThreadQueueEngine();
  }
  // Maximum threads to spawn to do the work.
  public static final int MAX_CONNECTIONS = 5;
  // Constants of the possible states of this network engine.
  public static final int STATE_RUNNING = 100;
  public static final int STATE_COMPLETE = 101;
  public static final int STATE_NOT_RUNNING = 102;

  // Runnable data containers
  private Vector<Runnable> active = new Vector<Runnable>();
  private Vector<Runnable> queue = new Vector<Runnable>();
  // Variable to store the state.
  private int state;
  // Variable used internally to update progress of engine.
  private int counter = 0;
  // Variable to hold the current loading progress of the engine.
  private volatile int progress = 0;

  public void execute(Runnable runnable) {
    setState(STATE_NOT_RUNNING);
    queue.add(runnable);
    if (active.size() <= MAX_CONNECTIONS) {
      startNext();
    }
  }

  private void startNext() {
    if (!queue.isEmpty()) {
      for (Runnable runnable : queue) {
        if (!active.contains(runnable)) {
          queue.remove(runnable);
          active.add(runnable);
          setState(STATE_RUNNING);
          setProgress(10);
          Thread thread = new Thread(runnable);
          thread.start();
          break;
        }
      }
    }
  }

  public void didComplete(Runnable runnable) {
    active.remove(runnable);
    if (active.isEmpty()) {
      setStateComplete();
    } else {
      setState(STATE_RUNNING);
      // Progress is based upon total active connections.
      if (active.size() == MAX_CONNECTIONS) {
        if (counter == 0) {
          setProgress(20);
        } else {
          setProgress(counter * 10);
        }
        counter++;
      } else {
        if (counter != 0) {
          setProgress(counter * 10);
        }
        counter++;
      }
      if (getProgress() >= 90) {
        setProgress(90);
      }
    }
    startNext();
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public int getProgress() {
    return this.progress;
  }

  public void setState(int state) {
    this.state = state;
  }

  public int getState() {
    return this.state;
  }

  public int getActiveCount() {
    return this.active.size();
  }

  public int getQueueCount() {
    return this.queue.size();
  }

  public static ThreadQueueEngine getInstance() {
    return InstanceHolder.instance;
  }

  private void setStateComplete() {
    setState(STATE_COMPLETE);
    setProgress(100);
    counter = 0;
    queue.removeAllElements();
    active.removeAllElements();
  }

  /**
   * This is to reset the state of the thread queue engine to complete.<br />
   * <b>This should only be called from the onCreate of a
   * Super BaseActivity that is within the My DesignerClothing Application and nowhere else!!!!!</b>
   */
  public void setToCompleteState() {
    if (!active.isEmpty() || !queue.isEmpty()) {
      setStateComplete();
    }
  }
}
