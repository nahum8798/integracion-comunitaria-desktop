package connection;

/**
 * Representa el resultado de una operación en la base de datos.
 */
public class ResultDataBase {
    private Boolean success;
    private String message;
    private Object object;

    public ResultDataBase() {}

    public ResultDataBase(Boolean success, String message, Object object) {
        this.success = success;
        this.message = message;
        this.object = object;
    }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getObject() { return object; }
    public void setObject(Object object) { this.object = object; }
}
