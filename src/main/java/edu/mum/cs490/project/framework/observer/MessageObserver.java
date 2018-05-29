package edu.mum.cs490.project.framework.observer;

import java.util.Observable;
import java.util.Observer;

public class MessageObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Send a message to the receivers");
    }
}
