import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { useInfiniteQuery } from '@tanstack/react-query';
import { useInView } from 'react-intersection-observer';

import AnswerItem from '../../components/answerList/AnswerItem';
import BackArrowButton from '../../components/button/BackArrowButton';
import Circle from '../../components/circle/Circle';
import CheckBox from '../../components/common/CheckBox';
import AvailableModal from '../../components/modal/AvailableModal';
import ReportModal from '../../components/modal/ReportModal';

import useModalStore from '../../store/useModalStore';
import useToastStore from '../../store/useToastStore';

import { getAllComments } from '../../services/api/comment/getComment';

import useCommentGroup from '../../hooks/queries/useCommentGroup';
import useGetQuestionDetail from '../../hooks/queries/useGetQuestionDetail';
import FallBack from '../../components/ui/FallBack';
import Modal from '../../components/modal/Modal';

type sortOrder = 'old' | 'latest' | 'like';

const SORTORDER_CHECKBOXS = [
  {
    label: '오래된순',
    id: 'old',
  },
  {
    label: '최신순',
    id: 'latest',
  },

  {
    label: '여운순',
    id: 'like',
  },
];

const QuestionCommentPage = () => {
  const navigate = useNavigate();

  const modal = useModalStore();
  const toast = useToastStore();

  const [ref, inView] = useInView();

  const params = useParams();
  const questionId = Number(params.id);

  const { data: questionDetail } = useGetQuestionDetail(questionId);
  const createTime = questionDetail?.createTime ? new Date(questionDetail.createTime) : new Date();
  const content = questionDetail?.content;

  const [sortOrder, setSortOrder] = useState<sortOrder>('old');

  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ['comment', questionId, sortOrder],
    queryFn: async () => await getAllComments(questionId, sortOrder),
    initialPageParam: 0,
    getNextPageParam: (lastPage, allPages) => (lastPage.hasNext ? allPages.length : undefined),
  });

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const mycomment = data?.pages[0]?.myComment ?? null;
  const comment = data?.pages.flatMap((page) => page.comments ?? []) || [];

  const { comments } = useCommentGroup(comment, sortOrder);

  const [report, setReport] = useState(false);
  const [register, setRegister] = useState(false);

  const onClickReportBtn = () => {
    setReport(true);
    setRegister(false);
    modal.openModal();
  };

  const onSubmitModal = () => {
    setReport(false);
    setRegister(false);
    modal.closeModal();
    console.log('신고!');
  };

  const handleConfirm = () => {
    setReport(false);
    setRegister(false);
    modal.closeModal();
    navigate(`/question/comment/${questionId}`);
  };

  const handleSelectSortOrder = (sortOrder: sortOrder) => {
    setSortOrder(sortOrder);
  };

  const onClickComment = () => {
    if (questionDetail?.isAuthor) {
      toast.addToast.notification({
        title: '여운 등록 실패',
        message: '본인 답변에는 여운을 남길 수 없어요!',
      });
    } else if (mycomment === null) {
      setReport(false);
      setRegister(true);
      modal.openModal();
    } else {
      modal.openModal();
    }
  };

  return (
    <div className="h-[100svh]">
      <header className="relative flex justify-center p-6">
        <div className="absolute left-6">
          <BackArrowButton />
        </div>
        <h3 className="w-full text-center">{`${createTime.getFullYear()}년 ${createTime.getMonth() + 1}월 ${createTime.getDate()}일`}</h3>
        <div className="absolute right-6">
          <button
            onClick={onClickReportBtn}
            className="block min-h-6 min-w-6 cursor-pointer bg-[url(/icons/report.svg)] bg-no-repeat"
          />
          {report && <ReportModal onSubmit={onSubmitModal} />}
        </div>
      </header>
      <main className="no-scrollbar flex h-[calc(100%-125px)] flex-col overflow-scroll px-6 pb-8">
        <div className="flex h-[380px] items-center justify-center">
          <button onClick={onClickComment} className="cursor-pointer">
            <Circle size={280} animate={true} category={questionDetail?.categoryName}>
              <p className="text-blur text-black-primary px-8 text-xl break-keep">{content}</p>
            </Circle>
          </button>
        </div>

        <p className="py-3">
          이 질문에 대한 답변{' '}
          <span className="font-desc text-[#AAAAAA]">
            {questionDetail?.commentCount != null && questionDetail.commentCount > 0
              ? questionDetail.commentCount > 99
                ? '99+'
                : questionDetail.commentCount.toString().padStart(2, '0')
              : ''}
          </span>
        </p>
        {comments.length === 0 && !mycomment && (
          <FallBack desc="아직 남겨진 여운이 없어요" subDesc="당신의 답변이 첫 여운이 되어주세요" />
        )}
        {comments.length > 0 && !!mycomment && (
          <div className="mb-3 flex items-center justify-start gap-2 py-3">
            {SORTORDER_CHECKBOXS.map((option) => (
              <CheckBox
                key={option.id}
                isChecked={sortOrder === option.id}
                id="sortOrder"
                name={option.id}
                label={option.label}
                value={option.id}
                onChange={(e) => handleSelectSortOrder(e.target.value as sortOrder)}
              />
            ))}
          </div>
        )}
        {!!mycomment && (
          <div className="border-b border-[#FFFFFF80] py-4">
            <AnswerItem
              my={true}
              report={report}
              id={mycomment.id}
              isLike={mycomment.isLike}
              likeCount={mycomment.likeCount}
              content={mycomment.content}
              reportBtnClick={onClickReportBtn}
              onSubmit={() => console.log('삭제')}
              questionId={questionId}
              sortOrder={sortOrder}
            />
          </div>
        )}
        {comments.length > 0 &&
          comment.map((comment) => (
            <div className="border-b border-[#FFFFFF80] py-4">
              <AnswerItem
                my={false}
                report={report}
                id={comment.id}
                isLike={comment.isLike}
                likeCount={comment.likeCount}
                content={comment.content}
                reportBtnClick={onClickReportBtn}
                onSubmit={onSubmitModal}
                questionId={questionId}
                sortOrder={sortOrder}
              />
            </div>
          ))}
        {/* 무한 스크롤 */}
        <div ref={ref} style={{ display: 'none' }}>
          로딩
        </div>
      </main>
      {register && (
        <AvailableModal title="질문 당 답변" subTitle="답변" handleConfirm={handleConfirm} />
      )}
      <div className="absolute bottom-0 w-full p-6">
        <button
          form="add-question"
          className="font-desc h-[60px] w-full cursor-pointer rounded-xl bg-white py-4 font-bold text-black"
          onClick={onClickComment}
        >
          답변 작성하기
        </button>
      </div>
      {!!mycomment && (
        <Modal>
          <Modal.Header>
            <Modal.Title>질문당 답변은 1번만 가능합니다.</Modal.Title>
            <Modal.SubTitle>
              <span className="text-[#FF2020]">이 질문에 이미 답변을 작성하셨습니다.</span>
            </Modal.SubTitle>
          </Modal.Header>
          <Modal.Footer>
            <Modal.CancleButton>확인</Modal.CancleButton>
          </Modal.Footer>
        </Modal>
      )}
    </div>
  );
};

export default QuestionCommentPage;
