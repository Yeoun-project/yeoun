import useModalStore from '../../store/useModalStore';

const ModalCancelButton = ({ children }: { children: React.ReactNode }) => {
  const { closeModal } = useModalStore();

  return (
    <button
      type="button"
      onClick={() => closeModal()}
      className="w-full cursor-pointer rounded-[10px] bg-[#AAAAAA] py-2 text-[#FEFEFE]"
    >
      {children}
    </button>
  );
};

export default ModalCancelButton;
