import useModalStore from '../../store/useModalStore';

const ModalCancelButton = ({
  children,
  onCancel,
}: {
  children: React.ReactNode;
  onCancel?: () => void;
}) => {
  const { closeModal } = useModalStore();

  return (
    <button
      type="button"
      onClick={() => {
        closeModal();
        if (onCancel) onCancel();
      }}
      className="w-full cursor-pointer rounded-[10px] bg-[#AAAAAA] py-2 text-[#FEFEFE]"
    >
      {children}
    </button>
  );
};

export default ModalCancelButton;
