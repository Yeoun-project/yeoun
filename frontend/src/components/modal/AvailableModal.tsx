import Modal from './Modal';

interface availableProps {
  title: string;
  subTitle?: string;
  handleConfirm: () => void;
}

const AvailableModal = ({ title, subTitle = title, handleConfirm }: availableProps) => {
  return (
    <Modal>
      <Modal.Header>
        <Modal.Title>
          {title} 작성은 하루에 1번만 가능합니다. {subTitle}을 작성하시겠습니까?
        </Modal.Title>
        <Modal.SubTitle>
          작성 가능 횟수가 <span className="text-[#398DFA]">1번 남았습니다.</span>
        </Modal.SubTitle>
      </Modal.Header>
      <Modal.Footer>
        <Modal.CancleButton>아니요</Modal.CancleButton>
        <Modal.ConfirmButton onConfirm={handleConfirm}>네</Modal.ConfirmButton>
      </Modal.Footer>
    </Modal>
  );
};

export default AvailableModal;
