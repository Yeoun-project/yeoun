import { useNavigate } from 'react-router-dom';

import { useMutation } from '@tanstack/react-query';

import useToastStore from '../../store/useToastStore';

import { updateTodayQuestionComment } from '../../services/api/question/todayQuestion';
import { queryClient } from '../../utils/queryClient';

const useUpdateCommentMutation = (questionId: number) => {
  const { addToast } = useToastStore();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: async (comment: string) => updateTodayQuestionComment({ comment, questionId }),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['today-question', 'comment', questionId.toString()],
      });
      addToast.notification({
        title: '여운 수정 완료',
        message: '오늘의 질문에 당신의 여운이 다시 머물렀어요.',
      });

      navigate('/today-question/answers');
    },
  });
};

export default useUpdateCommentMutation;
