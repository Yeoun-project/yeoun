import { useNavigate } from 'react-router-dom';

import { useMutation } from '@tanstack/react-query';

// import useToastStore from '../../store/useToastStore';

import { deleteTodayQuestionComment } from '../../services/api/question/todayQuestion';
import { queryClient } from '../../utils/queryClient';

const useDeleteCommentMutation = () => {
  //   const { addToast } = useToastStore();
  const navigate = useNavigate();

  return useMutation({
    mutationFn: async (questionId: number) => deleteTodayQuestionComment(questionId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['my', 'today-question', 'answers'],
      });
      //   addToast.notification({
      //     title: '여운 수정 완료',
      //     message: '오늘의 질문에 당신의 여운이 다시 머물렀어요.',
      //   });

      navigate('/today-question/answers');
    },
  });
};

export default useDeleteCommentMutation;
