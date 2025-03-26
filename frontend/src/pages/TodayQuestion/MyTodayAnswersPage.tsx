import BackArrow from '../../components/common/BackArrow';
import QuestionList from '../../components/question/QuestionList';
import QuestionCategory from '../../type/questionCategory';

const questions = [
  {
    id: 1,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 5,
    commentCount: 3,
    categoryName: 'valuesAndBeliefs' as QuestionCategory,
    createTime: '2025-12-25 17:35:23',
  },
  {
    id: 2,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 4,
    commentCount: 2,
    categoryName: 'relationships' as QuestionCategory,
    createTime: '2025-11-05 17:50:50',
  },
  {
    id: 3,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 4,
    commentCount: 2,
    categoryName: 'dreamsAndGoals' as QuestionCategory,
    createTime: '2025-02-13 17:50:50',
  },
  {
    id: 4,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 4,
    commentCount: 2,
    categoryName: 'selfReflection' as QuestionCategory,
    createTime: '2025-10-08 17:50:50',
  },
  {
    id: 5,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 4,
    commentCount: 2,
    categoryName: 'mindAndEmotions' as QuestionCategory,
    createTime: '2025-04-17 17:50:50',
  },
  {
    id: 6,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 4,
    commentCount: 2,
    categoryName: 'challengesAndCourage' as QuestionCategory,
    createTime: '2025-05-19 17:50:50',
  },
  {
    id: 7,
    content: '다시 태어난다면, 당신은 어떻게 살고 싶나요?',
    heart: 4,
    commentCount: 2,
    categoryName: 'memories' as QuestionCategory,
    createTime: '2025-01-21 17:50:50',
  },
];

const MyTodayAnswersPage = () => {
  return (
    <main className="flex min-h-[100svh] flex-col gap-4">
      {/* Header */}
      <header className="relative flex justify-center px-6 py-3">
        <div className="absolute top-3 left-6">
          <BackArrow />
        </div>
        <h3 className="w-full text-center">답변목록</h3>
      </header>

      {/* Content-Section */}
      <QuestionList questions={questions} />
    </main>
  );
};

export default MyTodayAnswersPage;
