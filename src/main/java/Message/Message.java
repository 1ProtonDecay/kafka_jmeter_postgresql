package Message;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "test_table", schema = "test_db")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "msg_id")
    private String msgId;

    @Column(name = "head")
    private String head;

    @CreatedDate
    @Column(name = "time_rq")
    private long timeRq;
}
