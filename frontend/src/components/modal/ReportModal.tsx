import Modal from './Modal';

const ReportModal = ({
  value,
  onSubmit,
  onCancel,
}: {
  value: string;
  onSubmit: () => void;
  onCancel: () => void;
}) => {
  return (
    <Modal>
      <Modal.Header>
        <Modal.Title>해당 {value}을 신고하시겠습니까?</Modal.Title>
      </Modal.Header>
      <Modal.Footer>
        <Modal.CancleButton onCancel={onCancel}>취소</Modal.CancleButton>
        <Modal.ConfirmButton onConfirm={onSubmit}>신고</Modal.ConfirmButton>
      </Modal.Footer>
    </Modal>
  );
};

export default ReportModal;
