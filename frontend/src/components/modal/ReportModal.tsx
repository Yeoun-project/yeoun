import Modal from './Modal';

const ReportModal = ({ onSubmit }: { onSubmit: () => void }) => {
  return (
    <Modal>
      <Modal.Header>
        <Modal.Title>해당 답변을 신고하시겠습니까?</Modal.Title>
      </Modal.Header>
      <Modal.Footer>
        <Modal.CancleButton>취소</Modal.CancleButton>
        <Modal.ConfirmButton onConfirm={onSubmit}>신고</Modal.ConfirmButton>
      </Modal.Footer>
    </Modal>
  );
};

export default ReportModal;
