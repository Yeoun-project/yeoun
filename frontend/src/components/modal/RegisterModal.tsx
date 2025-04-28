import Modal from "./Modal";

const RegisterModal = ({content, onSubmit}: {content: string, onSubmit: ()=>void}) => {
    return (
        <Modal>
            <Modal.Header>
              <Modal.Title>이대로 등록하시겠습니까?</Modal.Title>
              <Modal.SubTitle>
                작성한 질문은 한 번 등록하면{' '}
                <span className="text-[#FF2020]">수정이나 삭제가 불가능합니다!</span>
              </Modal.SubTitle>
            </Modal.Header>
            <Modal.Content>
              <div className="font-desc h-[200px] rounded-[8px] border border-[#919191] px-5 py-3 text-[#1A1A1A]">
                {content}
              </div>
              <div className="font-desc mt-2 text-right text-sm text-[#6D6D6D]">
                {content.length} / 30
              </div>
            </Modal.Content>
            <Modal.Footer>
              <Modal.CancleButton>수정</Modal.CancleButton>
              <Modal.ConfirmButton
                onConfirm={onSubmit}
              >
                등록
              </Modal.ConfirmButton>
            </Modal.Footer>
          </Modal>
    )
}

export default RegisterModal;