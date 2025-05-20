package yeoun.withdrawHistory.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yeoun.withdrawHistory.domain.repository.WithdrawHistoryRepository;

@Component
@RequiredArgsConstructor
public class WithdrawHistoryWriter {

    private final WithdrawHistoryRepository withdrawHistoryRepository;

    void write(final WithdrawHistory withdrawHistory) {
        withdrawHistoryRepository.save(withdrawHistory);
    }

}
