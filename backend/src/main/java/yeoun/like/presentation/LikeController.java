package yeoun.like.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yeoun.auth.service.JwtService;
import yeoun.common.SuccessResponse;
import yeoun.like.dto.request.LikeRequest;
import yeoun.like.service.LikeService;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/comment/{commentId}/like")
    public ResponseEntity<?> updateCommentLikeStatus(
            @PathVariable final Long commentId,
            @RequestBody @Valid final LikeRequest likeRequest
    ) {
        Long userId = JwtService.getUserIdFromAuthentication();
        likeService.update(userId, commentId, likeRequest);
        return ResponseEntity.ok(new SuccessResponse("좋아요 상태 변경에 성공했습니다.", null));
    }

}
