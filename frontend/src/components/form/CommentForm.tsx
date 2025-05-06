interface CommentFormProps {
  formId?: string;
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  maxValue?: number;
  commentValue: string;
  onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  ref: React.RefObject<HTMLTextAreaElement | null>;
}

const CommentForm = ({
  formId,
  onSubmit,
  maxValue = 500,
  commentValue,
  onChange,
  ref,
}: CommentFormProps) => {
  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    // maxLength 한글 인식 이슈
    if (e.target.value.length > maxValue) {
      e.target.value = e.target.value.slice(0, maxValue);
    }

    onChange(e);
  };

  return (
    <form className="z-10 w-full" id={formId} onSubmit={(e) => onSubmit(e)}>
      <div className="relative z-10">
        <textarea
          ref={ref}
          value={commentValue}
          onChange={handleChange}
          name="comment"
          id="comment"
          maxLength={maxValue}
          className="font-desc z-30 h-[250px] w-full resize-none rounded-sm border border-white/50 bg-white/5 px-5 py-4 text-base/relaxed backdrop-blur-xs outline-none placeholder:text-white"
          placeholder="답변을 작성해주세요"
        />
        <div className="font-desc w-full text-right text-sm">{`${commentValue.length || 0} / 500`}</div>
      </div>
    </form>
  );
};

export default CommentForm;
