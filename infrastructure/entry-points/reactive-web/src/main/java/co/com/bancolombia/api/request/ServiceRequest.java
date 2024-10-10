package co.com.bancolombia.api.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ServiceRequest {
    @NotBlank(message = "address is required")
    @Size(max = 30, message = "the address can only have a maximum of 30 characters")
    private String address;

    @NotBlank(message = "description is required")
    @Size(max = 100, message = "the description can only have a maximum of 100 characters")
    private String description;

    @NotNull(message = "start date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "start date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @NotNull(message = "technician id is required")
    @Max(value = 19)
    private Long technicianId;
}
