interface ModalConfirmButtonProps {
  children: React.ReactNode;
  onConfirm: () => void;
}

const ModalConfirmButton = ({ children, onConfirm }: ModalConfirmButtonProps) => {
  return (
    <button
      onClick={onConfirm}
      className="w-full cursor-pointer rounded-[10px] bg-[#FC90D1] text-[#FEFEFE]"
    >
      {children}
    </button>
  );
};

export default ModalConfirmButton;
