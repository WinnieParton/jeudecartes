package esgi.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeckDomain {

    private Long id;

    private List<HeroDomain> heros;

    private Date createdAt;

    private Date updatedAt;

}
