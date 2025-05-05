import { useQuery } from '@tanstack/react-query';
import { getQuestionDetail } from '../../services/api/question/getQuestionDetail';

const useGetQuestionDetail = (questionId: number) => {
  return useQuery({
    queryKey: ['questionDetail', questionId],
    queryFn: async () => await getQuestionDetail(questionId),
  });
};

export default useGetQuestionDetail;
