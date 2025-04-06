package com.ian.rent_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private List<T> dataList;
    private boolean success;
    private String message;

    public static <E> ApiResponse<E> ofList(List<E> list){
        return ApiResponse.<E>builder()
                .dataList(list)
                .success(true)
                .message("OK")
                .build();
    }

    public static <E> ApiResponse<E> ofSingle(E data){
        return ApiResponse.<E>builder()
                .data(data)
                .success(true)
                .message("OK")
                .build();
    }

    public static <E> ApiResponse<E> ofInformation(String message){
        return ApiResponse.<E>builder()
                .success(true)
                .message(message)
                .build();
    }
}
