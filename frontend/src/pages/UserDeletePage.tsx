import { useEffect, useRef, useState } from 'react';
import SubPageHeader from '../components/ui/SubPageHeader';
import { useNavigate } from 'react-router-dom';
import { userDelete } from '../services/api/auth/userDelete';
import Modal from '../components/modal/Modal';
import useModalStore from '../store/useModalStore';

const reasons = [
  {
    id: 1,
    name: '기대한 서비스 분위기랑 달라요',
  },
  {
    id: 2,
    name: '사용하기가 어려워요',
  },
  {
    id: 3,
    name: '하고 싶은 말을 다 못 적어요',
  },
  {
    id: 4,
    name: '수정/삭제가 안돼요',
  },
  {
    id: 5,
    name: '질문과 답변을 더 남기고 싶어요',
  },
  {
    id: 6,
    name: '질문과 답변이 너무 없어요',
  },
  {
    id: 7,
    name: '비매너 답변이 달렸어요',
  },
  {
    id: 8,
    name: '기타',
  },
];

const UserDeletePage = () => {
  const dropdownRef = useRef<HTMLDivElement>(null);
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = useState(false);
  const [reasonNum, setReasonNum] = useState(0);
  const [checked, setChecked] = useState(true);

  const { modal, openModal, closeModal } = useModalStore();

  useEffect(() => {
    const clickOutside = (e: MouseEvent) => {
      if (isOpen && dropdownRef.current && !dropdownRef.current.contains(e.target as Node)) {
        setIsOpen(!isOpen);
      }
    };

    document.addEventListener('mousedown', clickOutside);
    return () => {
      document.removeEventListener('mousedown', clickOutside);
    };
  }, [isOpen, setIsOpen]);

  const handleSelect = (id: number) => {
    setReasonNum(id);
    setIsOpen(false);
  };

  const onClickLeave = () => {
    openModal();
  };

  const onClickDelete = async (checked: boolean) => {
    try {
      const response = await userDelete(checked);
      if (response.status === 200) {
        closeModal();
        navigate('/');
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className="flex h-[100svh] flex-col">
      <SubPageHeader pageTitle={'탈퇴하기'} />
      <div className="p-6">
        <p className="pb-4 text-[20px]">여운을 남긴 채, 떠나시겠어요?</p>

        <p className="font-desc gap-1 leading-6.5 text-[#AAAAAA]">
          {`당신이 머물렀던 흔적은 여기 남아 누군가에게 조용한 울림이 될 거예요. `}
          <span className="text-[#FF2020]">탈퇴하면 모든 기록이 사라지고 되돌릴 수 없습니다.</span>
          {` 그래도 괜찮다면 조심스레 보내드릴게요.`}
        </p>
      </div>
      <div className="p-6">
        <p className="font-desc pb-3">혹시 떠나시는 이유를 알려주실 수 있을까요?</p>
        <div ref={dropdownRef} className={'font-desc relative mb-4 w-full text-white'}>
          <div
            onClick={() => setIsOpen(!isOpen)}
            className={`flex cursor-pointer items-center justify-between border border-white/20 bg-[#ffffff0D] px-4 py-2 backdrop-blur-md transition ${isOpen ? 'rounded-t-md rounded-b-none' : 'rounded-md'}`}
          >
            <div className="flex items-center gap-2">
              {reasonNum === 0
                ? '이유를 선택해주세요'
                : reasons.find((reason) => reason.id === reasonNum)?.name}
            </div>
            <img
              src="/icons/dropdownIcon.svg"
              alt="드롭다운 버튼"
              className={`h-4 w-4 transform transition-transform duration-200 ${isOpen ? 'rotate-180' : ''}`}
            />
          </div>
          {isOpen && (
            <ul className="absolute z-10 w-full overflow-y-auto border-r border-l border-white/20 bg-white/10 shadow-lg backdrop-blur-md">
              {reasons.map((reason) => (
                <li
                  key={reason.id}
                  onClick={() => handleSelect(reason.id)}
                  className={`flex cursor-pointer items-center border-b border-white/20 px-4 py-2 transition hover:bg-white/20 ${reason.id === reasonNum ? 'bg-[#96567c4d]' : ''}`}
                >
                  {reason.name}
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      <div className="absolute bottom-0 w-full p-6">
        <div className="flex flex-col items-center p-6">
          <label className="flex cursor-pointer items-center gap-2">
            <input onClick={() => setChecked(!checked)} type="checkbox" className="peer hidden" />
            <div className="peer-checked: h-6 w-6 border border-white/40 peer-checked:bg-white"></div>
            <span className="font-desc">작성한 질문과 답변까지 모두 삭제할게요.</span>
          </label>
        </div>
        <div className="flex gap-3">
          <button
            onClick={onClickLeave}
            className="font-desc h-[60px] w-[50%] cursor-pointer rounded-xl bg-[#AAAAAA] py-4 font-bold text-white"
          >
            떠나기
          </button>
          <button
            onClick={() => navigate(-1)}
            className="font-desc h-[60px] w-[50%] cursor-pointer rounded-xl bg-[#FC90D1] py-4 font-bold text-white"
          >
            계속 함께하기
          </button>
        </div>
      </div>
      {modal && (
        <Modal>
          <Modal.Header>
            <Modal.Title>등록하신 답변을 삭제하시겠습니까?</Modal.Title>
            <Modal.SubTitle>
              삭제 후 답변 <span className="text-[#FF2020]">복구는 불가능합니다.</span>
            </Modal.SubTitle>
          </Modal.Header>
          <Modal.Footer>
            <Modal.CancleButton>취소</Modal.CancleButton>
            <Modal.ConfirmButton
              onConfirm={() => {
                onClickDelete(checked);
              }}
            >
              삭제
            </Modal.ConfirmButton>
          </Modal.Footer>
        </Modal>
      )}
    </div>
  );
};

export default UserDeletePage;
