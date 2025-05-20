package yeoun.withdrawHistory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reasonCategory;

    @Column
    private String reasonDetail;

    @Column
    private final LocalDateTime createTime = LocalDateTime.now();

    public WithdrawHistory(String reasonCategory, String reasonDetail) {
        this.reasonCategory = reasonCategory;
        this.reasonDetail = reasonDetail;
    }
}
