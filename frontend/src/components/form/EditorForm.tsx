import { useEffect, useRef } from 'react';

interface EditorProps {
  formId: string;
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  value: string;
  onChange: (e: React.FormEvent<HTMLDivElement>) => void;
  hasError: boolean;
  maxLength: number;
  placeholder: string;
  forbidden: string[];
}

const highlightForbiddenWords = (text: string, forbiddenWords: string[]) => {
  let highlighted = text;
  forbiddenWords.forEach((word) => {
    const regex = new RegExp(`(${word})`, 'gi');
    highlighted = highlighted.replace(regex, `<span style="color: #FF2020;">$1</span>`);
  });
  return highlighted;
};

const EditorForm = ({
  formId,
  onSubmit,
  value,
  onChange,
  hasError,
  maxLength,
  placeholder,
  forbidden,
}: EditorProps) => {
  const valueRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (valueRef.current) {
      if (hasError) {
        if (value.length !== 0)
          valueRef.current.innerHTML = highlightForbiddenWords(value, forbidden);
      } else {
        valueRef.current.innerText = value;
      }
    }
  }, [forbidden]);

  return (
    <form id={formId} onSubmit={onSubmit} className="h-[192px]">
      <div
        ref={valueRef}
        contentEditable
        suppressContentEditableWarning={true}
        onBeforeInput={(e) => {
          if(value.length >= maxLength) {
            if(valueRef.current) {
              valueRef.current.contentEditable = 'false';
              setTimeout(() => {
                if(valueRef.current)
                  valueRef.current.contentEditable = 'true';
              }, 0);
            }
            e.preventDefault();
          }
        }}
        onInput={onChange}
        data-placeholder={placeholder}
        className={`!h-[160px] w-full resize-none rounded-[4px] border p-5 placeholder:text-white before:opacity-50 empty:before:content-[attr(data-placeholder)] focus:outline-none focus:before:content-[''] ${
          hasError
            ? 'border-[#FF202080] bg-[#FF20200D] focus:border-[#FF202080]'
            : 'border-[#99999999] bg-[#ffffff1a]'
        }`}
      >
        {/* 여기에 입력 들어감 */}
      </div>
      <div className="p-2 text-right text-sm text-white">
        {value.length} / {maxLength}
      </div>
    </form>
  );
};

export default EditorForm;
