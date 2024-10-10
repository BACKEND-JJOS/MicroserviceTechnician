package co.com.bancolombia.model.technician;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Technician {
    private Integer id;
    private String name;
}
