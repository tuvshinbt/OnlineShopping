package edu.mum.cs490.project.model;

/**
 * Created by Erdenebayar on 4/24/2018
 */
public class Message {

    public enum Type {
        SUCCESS, ERROR, INFO
    }

    public static Message successfullySaved = new Message(Type.SUCCESS, "successfully.saved");
    public static Message errorOccurred = new Message(Type.ERROR, "error.occurred");

    public Message(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    private Type type;
    private String message;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (type != message1.type) return false;
        return message != null ? message.equals(message1.message) : message1.message == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
