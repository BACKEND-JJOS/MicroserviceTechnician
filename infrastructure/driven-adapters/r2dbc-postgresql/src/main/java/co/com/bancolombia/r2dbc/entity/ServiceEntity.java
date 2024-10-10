package co.com.bancolombia.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table("service")
public class ServiceEntity {
    @Id
    private Long id;
    private String address;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long technicianId;
}
