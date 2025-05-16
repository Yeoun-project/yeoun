import Modal from './Modal';

const ConfirmModal = ({ value, onSubmit }: { value: string; onSubmit: () => void }) => {
  return (
    <Modal>
      <Modal.Header>
        <Modal.Title>작성하신 {value}을 등록하시겠습니까?</Modal.Title>
        <Modal.SubTitle>
          등록 후 {value}
          <span className="text-[#FF2020]"> 수정이나 삭제는 불가능합니다.</span>
        </Modal.SubTitle>
      </Modal.Header>
      <Modal.Footer>
        <Modal.CancleButton>아니오</Modal.CancleButton>
        <Modal.ConfirmButton onConfirm={onSubmit}>네</Modal.ConfirmButton>
      </Modal.Footer>
    </Modal>
  );
};

export default ConfirmModal;
