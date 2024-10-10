package co.com.bancolombia.api.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse <T>{
    private T data;
    private int status;
    private String message;
}
