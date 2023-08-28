package edu.byu.cs.tweeter.model.net.response;

import java.util.Objects;

public class LogoutResponse {
    public LogoutResponse()
    {}
    public LogoutResponse(boolean success, String message)
    {
        this.message = message;
        this.sucess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    private boolean sucess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoutResponse response = (LogoutResponse) o;
        return sucess == response.sucess &&
                Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, sucess);
    }
}
