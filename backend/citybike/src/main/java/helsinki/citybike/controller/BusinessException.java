package helsinki.citybike.controller;

import org.springframework.validation.BindingResult;

public class BusinessException extends RuntimeException {

    private BindingResult bindingResult;
    public BusinessException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
