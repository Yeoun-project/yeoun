import { ReactNode, useEffect, useRef } from 'react';
import { createPortal } from 'react-dom';
import useModalStore from '../../store/useModalStore';

import ModalTitle from './ModalTitle';
import ModalSubTitle from './ModalSubTitle';
import ModalCancelButton from './ModalCancelButton';
import ModalConfirmButton from './ModalConfirmButton';

interface ModalProps {
  children: ReactNode;
}

const Modal = ({ children }: ModalProps) => {
  const modalRef = useRef<HTMLDialogElement>(null);
  const { closeModal, modal } = useModalStore();
  useEffect(() => {
    if (modal) {
      modalRef.current?.showModal();
    } else {
      modalRef.current?.close();
    }
  }, [closeModal, modal]);

  return createPortal(
    <dialog
      className="mx-auto flex min-h-[100svh] w-full max-w-[430px] items-center justify-center bg-transparent px-6 backdrop:bg-black/40"
      ref={modalRef}
      onClose={() => closeModal()}
    >
      <div className="w-full rounded-[20px] bg-white px-12 py-6">{children}</div>
    </dialog>,
    document.getElementById('modal') as HTMLDivElement
  );
};

const ModalHeader = ({ children }: ModalProps) => {
  return <header className="mb-3">{children}</header>;
};

const ModalContent = ({ children }: ModalProps) => {
  return <main className="mb-3">{children}</main>;
};
const ModalFooter = ({ children }: ModalProps) => {
  return <div className="flex gap-3">{children}</div>;
};
export default Modal;

Modal.Header = ModalHeader;
Modal.Footer = ModalFooter;
Modal.Content = ModalContent;

Modal.Title = ModalTitle;
Modal.SubTitle = ModalSubTitle;
Modal.CancleButton = ModalCancelButton;
Modal.ConfirmButton = ModalConfirmButton;
