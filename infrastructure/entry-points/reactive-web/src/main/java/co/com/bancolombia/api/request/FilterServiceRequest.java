package co.com.bancolombia.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FilterServiceRequest {
    @NotNull(message = "size is required")
    private Integer technicianId;

    @NotNull(message = "page is required")
    private String startDate;

    @NotNull(message = "page is required")
    private String endDate;
}
