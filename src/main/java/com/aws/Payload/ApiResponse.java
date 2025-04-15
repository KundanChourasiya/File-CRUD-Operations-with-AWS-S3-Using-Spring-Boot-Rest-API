package com.aws.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean status;
    private String message;
    private T data;

    public ApiResponse(Boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
