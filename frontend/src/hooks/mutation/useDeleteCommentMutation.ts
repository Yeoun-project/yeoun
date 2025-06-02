import { useNavigate } from 'react-router-dom';

import { useMutation } from '@tanstack/react-query';

import useToastStore from '../../store/useToastStore';

import { deleteTodayQuestionComment } from '../../services/api/question/todayQuestion';
import { queryClient } from '../../utils/queryClient';
import useAuthStore from '../../store/useAuthStore';

const useDeleteCommentMutation = () => {
  const { userType } = useAuthStore();
  const { addToast } = useToastStore();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: async (questionId: number) => deleteTodayQuestionComment(questionId),
    onSuccess: async (data, varibles) => {
      console.log(varibles);
      await queryClient.invalidateQueries({
        queryKey: ['my', 'today-question', 'answers'],
      });

      await queryClient.invalidateQueries({
        queryKey: [userType, 'today-question'],
      });

      await queryClient.invalidateQueries({
        queryKey: ['today-question', 'comment', varibles.toString()],
      });

      addToast.notification({
        title: '여운 삭제 완료',
        message: '이 날의 여운은 더 이상 머물지 않아요.',
        hasBottomTab: false,
      });

      navigate('/today-question/answers?sort=latest', { state: { from: 'today-question-delete' } });
    },
  });
};

export default useDeleteCommentMutation;
