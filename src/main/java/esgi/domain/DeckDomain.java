package esgi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeckDomain {

    private Long id;

    private List<HeroDomain> heros;

    private Date createdAt;

    private Date updatedAt;

}
