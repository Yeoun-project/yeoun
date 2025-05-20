import { useMutation, useQueryClient } from '@tanstack/react-query';
import { changeCommentLike } from '../../services/api/comment/commentLike';

import ReportModal from '../modal/ReportModal';
import useToastStore from '../../store/useToastStore';

const AnswerItem = ({
  my,
  report,
  id,
  isLike,
  likeCount,
  content,
  reportBtnClick,
  onSubmit,
  questionId,
  sortOrder,
}: {
  my: boolean;
  report: boolean;
  id: number;
  isLike: boolean;
  likeCount: number;
  content: string;
  reportBtnClick: () => void;
  onSubmit: () => void;
  questionId: number;
  sortOrder: 'old' | 'latest' | 'like';
}) => {
  const toast = useToastStore();
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: ({ id, isLike }: { id: number; isLike: boolean }) => {
      return changeCommentLike(id, isLike);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['comment', questionId, sortOrder] });
    },
  });

  return (
    <div className="flex min-h-12 justify-between">
      <div className="gap-21px w-[calc(100%-100px)] break-all">
        {my && <p className="text-[#FC90D1]">{content}</p>}
        {!my && <p className="font-desc text-[14px]">{content}</p>}
      </div>
      <div className="flex gap-4">
        <div className="justify-between">
          <button
            className={`min-h-6 min-w-6 cursor-pointer bg-contain bg-center bg-no-repeat ${
              isLike
                ? 'bg-[url(/icons/filledHeart.svg)]'
                : my
                  ? 'bg-[url(/icons/myHeart.svg)]'
                  : 'bg-[url(/icons/heart.svg)]'
            }`}
            onClick={() => {
              if (!my) {
                mutation.mutate({ id, isLike });
              } else {
                toast.addToast.notification({
                  title: '좋아요 등록 실패',
                  message: '본인 답글에는 좋아요를 남길 수 없어요!',
                });
              }
            }}
          />
          <p className="font-desc text-center text-[14px]">
            {likeCount.toString().padStart(2, '0')}
          </p>
        </div>
        {!my && (
          <div className="justify-between">
            <button
              className="min-h-6 min-w-6 cursor-pointer bg-[url(/icons/report.svg)] bg-contain bg-center bg-no-repeat"
              onClick={reportBtnClick}
            />
            {report && <ReportModal onSubmit={onSubmit} value="답변" />}
            <p className="font-desc text-[14px]">신고</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default AnswerItem;
