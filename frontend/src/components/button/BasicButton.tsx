interface BasicButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

const BasicButton = ({ children, ...rest }: BasicButtonProps) => {
  return (
    <button
      className="font-desc w-full cursor-pointer rounded-xl bg-white py-4 text-sm font-bold text-black"
      {...rest}
    >
      {children}
    </button>
  );
};

export default BasicButton;
