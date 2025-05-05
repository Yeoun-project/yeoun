import { changeCommentLike } from '../../services/api/comment/commentLike';

import ReportModal from '../modal/ReportModal';

const AnswerItem = ({
  my,
  report,
  id,
  isLike,
  likeCount,
  reportBtnClick,
  onSubmit,
}: {
  my: boolean;
  report :boolean;
  id: number;
  isLike: boolean;
  likeCount: number;
  reportBtnClick: () => void;
  onSubmit: () => void;
}) => {
  return (
    <div className="flex justify-between min-h-12">
      <div className="w-[calc(100%-100px)] break-all gap-21px">
        {my && <p className="text-[#FC90D1]">댓글</p>}
        {!my && <p className="font-desc text-[14px]">댓글</p>}
      </div>
      <div className="flex gap-4">
        <div className="justify-between">
          <button
            className={`min-h-6 min-w-6 cursor-pointer bg-contain bg-center bg-no-repeat
              ${isLike ? 'bg-[url(/icons/filledHeart.svg)]' : 'bg-[url(/icons/heart.svg)]'}
              `}
            onClick={() => {
              changeCommentLike(id, isLike);
            }}
          />
          <p className="font-desc text-center text-[14px]">{likeCount.toString().padStart(2, '0')}</p>
        </div>
        {my && (
          <div className="justify-between">
            <button
              className="min-h-6 min-w-6 cursor-pointer bg-[url(/icons/bigX.svg)] bg-contain bg-center bg-no-repeat"
              onClick={() => {
                onSubmit();
              }}
            />
            <p className="font-desc text-[14px]">삭제</p>
          </div>
        )}
        {!my && (
          <div className="justify-between">
            <button
              className="min-h-6 min-w-6 cursor-pointer bg-[url(/icons/report.svg)] bg-contain bg-center bg-no-repeat"
              onClick={reportBtnClick}
            />
            {
              report && (<ReportModal onSubmit={onSubmit} />)
            }
            <p className="font-desc text-[14px]">신고</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default AnswerItem;
