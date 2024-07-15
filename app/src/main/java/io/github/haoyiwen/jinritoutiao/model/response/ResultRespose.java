package io.github.haoyiwen.jinritoutiao.model.response;

public class ResultRespose<T> {
    public String has_more;

    public String message;

    public String success;

    public T data;

    public ResultRespose(String has_more, String message, T data) {
        this.has_more = has_more;
        this.message = message;
        this.data = data;
    }
}
