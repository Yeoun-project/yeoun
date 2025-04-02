import { useQuery } from '@tanstack/react-query';
import { getTodayQuestion } from '../../services/api/question/todayQuestion';

const useGetTodayQuestion = () => {
  return useQuery({
    queryKey: ['today-question'],
    queryFn: async () => await getTodayQuestion(),
  });
};

export default useGetTodayQuestion;
