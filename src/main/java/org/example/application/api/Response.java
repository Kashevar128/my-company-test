package org.example.application.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(value = NON_NULL)
public class Response<T> {

    private Integer count;
    private T data;
    private Boolean success;
}
