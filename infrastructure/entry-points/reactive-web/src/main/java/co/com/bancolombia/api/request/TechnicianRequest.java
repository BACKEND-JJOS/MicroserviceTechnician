package co.com.bancolombia.api.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TechnicianRequest {

    @NotBlank(message = "technician name  is required")
    @Size(max = 30, message = "the name can only have a maximum of 30 characters")
    private String name;
}
