import Response from '../../../type/response';
import client from '../client';

interface CheckQuestionToday {
  hasWritten: boolean;
}

const checkQuestionToday = async (): Promise<boolean> => {
  const response = await client.get<Response<CheckQuestionToday>>('/api/question/written-today');
  return response.data.data.hasWritten;
};

export default checkQuestionToday;
