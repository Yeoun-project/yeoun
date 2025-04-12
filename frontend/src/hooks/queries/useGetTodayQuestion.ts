import { useQuery } from '@tanstack/react-query';
import { getTodayQuestion } from '../../services/api/question/todayQuestion';
import UserType from '../../type/auth/UserType';

const useGetTodayQuestion = (userType: UserType) => {
  return useQuery({
    queryKey: [userType, 'today-question'],
    queryFn: async () => await getTodayQuestion(userType),
  });
};

export default useGetTodayQuestion;
