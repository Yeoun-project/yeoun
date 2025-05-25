interface QuestionListYearSectionProps {
  children: React.ReactNode;
  year: string;
}

const QuestionListYearSection = ({ children, year }: QuestionListYearSectionProps) => {
  const afterStyle =
    'after:absolute after:top-1/2 after:-translate-y-1/2 after:right-6 after:w-[33%] after:border-b-1';
  const beforeStyle =
    'before:left-6 before:w-[33%] before:border-b-1 before:absolute before:top-1/2 before:-translate-y-1/2';
  return (
    <div className="">
      <p
        className={`relative mb-2 text-center text-sm ${afterStyle} ${beforeStyle}`}
      >{`${year}년`}</p>
      {children}
    </div>
  );
};

export default QuestionListYearSection;
