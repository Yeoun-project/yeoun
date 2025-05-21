package yeoun.withdrawHistory.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.user.dto.request.UserWithdrawRequest;

@Service
@RequiredArgsConstructor
public class WithdrawHistoryService {

    private final WithdrawHistoryWriter withdrawHistoryWriter;

    public void save(final UserWithdrawRequest userWithdrawRequest) {
        WithdrawHistory withdrawHistory = new WithdrawHistory(
                userWithdrawRequest.getReasonCategory(),
                userWithdrawRequest.getReasonDetail()
        );
        withdrawHistoryWriter.write(withdrawHistory);
    }

}
