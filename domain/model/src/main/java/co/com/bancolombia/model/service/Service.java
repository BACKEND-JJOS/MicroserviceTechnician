package co.com.bancolombia.model.service;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Service {
    private Integer id;
    private String address;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long technicianId;
}
