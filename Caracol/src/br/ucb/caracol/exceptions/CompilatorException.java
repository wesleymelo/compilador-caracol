
package br.ucb.caracol.exceptions;

@SuppressWarnings("serial")
public class CompilatorException extends RuntimeException{
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public CompilatorException(String message) {
        setMsg(message);
    }

    @Override
    public String getMessage() {
        return getMsg();
    }
    
    
    
}
