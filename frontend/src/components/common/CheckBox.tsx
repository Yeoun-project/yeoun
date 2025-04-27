interface CheckBoxProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  id: string;
  isChecked: boolean;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const CheckBox = ({ label, id, isChecked, value, onChange, ...rest }: CheckBoxProps) => {
  const CheckLabelStyle =
    ' has-checked:border-[#FC90D1]/50 has-checked:bg-gradient-to-b has-checked:from-[#FC90D1]/20 has-checked:to-[#96567C]/20 has-checked:text-white';
  const DefaultLabelStyle =
    'font-desc cursor-pointer rounded-full border border-[#AAAAAA] px-3 py-1 text-[#AAAAAA] transition-colors';
  return (
    <label className={`${DefaultLabelStyle} ${CheckLabelStyle}`}>
      {label}
      <input
        className="hidden"
        type="radio"
        id="sortOrder"
        name={id}
        value={value}
        checked={isChecked}
        onChange={onChange}
        {...rest}
      />
    </label>
  );
};

export default CheckBox;
