package myapp.org.userapp.utils;

public class Result<T> {
    private T data;
    private String error;
    private boolean success;

    public Result() {
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.data = data;
        result.success = true;
        return result;
    }

    public static <T> Result<T> error(String error) {
        Result<T> result = new Result<>();
        result.error = error;
        result.success = false;
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
