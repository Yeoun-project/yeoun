import Squre from '../../assets/Squre';

interface CommentFormProps {
  formId: string;
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  question: string;
  maxValue?: number;
  commentValue: string;
  onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const CommentForm = ({
  formId,
  onSubmit,
  question,
  maxValue = 500,
  commentValue,
  onChange,
}: CommentFormProps) => {
  return (
    <form className="z-10 w-full" id={formId} onSubmit={(e) => onSubmit(e)}>
      <label
        htmlFor="comment"
        className="text-gradient mb-6 block bg-clip-text text-center text-2xl/10 break-keep"
      >
        {question}
      </label>

      <div className="relative z-10">
        <textarea
          value={commentValue}
          onChange={(e) => onChange(e)}
          name="comment"
          id="comment"
          maxLength={maxValue}
          className="font-desc z-30 h-[270px] w-full resize-none rounded-sm border border-white/50 bg-white/10 p-4 backdrop-blur-xs outline-none placeholder:text-white"
          placeholder="답변을 작성해주세요"
        />
        <div className="font-desc w-full text-right text-sm">{`${commentValue.length || 0} / 500`}</div>

        {/* Background Squre */}
        <div aria-hidden className="animate-spin-second absolute top-[-15%] right-[-5%] -z-10">
          <Squre size={60} />
        </div>
        <div aria-hidden className="animate-spin-third absolute bottom-[-5%] left-[-5%] -z-10">
          <Squre size={80} />
        </div>
      </div>
    </form>
  );
};

export default CommentForm;
