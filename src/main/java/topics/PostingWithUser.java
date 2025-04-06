package topics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostingWithUser {
    String id;
    LocalDate creaetTime;
    String usuario;
    Integer numberOfPosting;
    String userName;
    LocalDate create;
    LocalDate update;
}
