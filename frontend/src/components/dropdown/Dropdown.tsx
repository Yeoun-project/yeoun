import { useEffect, useRef } from 'react';
import { ExtendedCategory } from '../../constant/category/extendsCategoryData';
import QuestionCategory from '../../type/questionCategory';

import Circle from '../circle/Circle';

interface DropdownProps {
  id: number;
  isOpen: boolean;
  all: boolean;
  onClick: () => void;
  handleSelect: (id: number) => void;
  categories: ExtendedCategory[];
  selected: ExtendedCategory;
  location?: string;
}

const Dropdown = ({
  id,
  isOpen = false,
  all,
  onClick,
  handleSelect,
  categories,
  selected,
  location,
}: DropdownProps) => {
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const clickOutside = (e: MouseEvent) => {
      if (isOpen && dropdownRef.current && !dropdownRef.current.contains(e.target as Node)) {
        onClick();
      }
    };

    document.addEventListener('mousedown', clickOutside);
    return () => {
      document.removeEventListener('mousedown', clickOutside);
    };
  }, [isOpen, onClick]);

  return (
    <>
      <div ref={dropdownRef} className={`${location} relative`}>
        <div
          onClick={onClick}
          className={`flex cursor-pointer items-center justify-between border border-white/20 bg-white/10 px-4 py-2 backdrop-blur-md transition ${isOpen ? 'rounded-t-md rounded-b-none' : 'rounded-md'}`}
        >
          <div className="flex items-center gap-2">
            {id !== 0 && <Circle category={selected.category as QuestionCategory} size={20} />}
            {id !== 0 && <span className="pl-1">{selected.name}</span>}
            {id === 0 && <span className="pl-1">전체 카테고리</span>}
          </div>
          <img
            src="/icons/dropdownIcon.svg"
            alt="드롭다운 버튼"
            className={`h-4 w-4 transform transition-transform duration-200 ${isOpen ? 'rotate-180' : ''}`}
          />
        </div>
        {isOpen && (
          <ul className="absolute z-10 max-h-72 w-full overflow-y-auto bg-white/10 shadow-lg backdrop-blur-md">
            {all && (
              <li
                key={0}
                onClick={() => handleSelect(0)}
                className={`flex cursor-pointer items-center gap-2 px-4 py-2 text-sm transition hover:bg-white/20 ${0 === id ? 'bg-[#96567c7c]' : ''}`}
              >
                전체 카테고리
              </li>
            )}
            {categories.map((cat) => (
              <li
                key={cat.id}
                onClick={() => handleSelect(cat.id)}
                className={`flex cursor-pointer items-center gap-2 px-4 py-2 text-sm transition hover:bg-white/20 ${cat.id === id ? 'bg-[#96567c7c]' : ''}`}
              >
                <Circle category={cat.category as QuestionCategory} size={20} animate />
                <span className="pl-1">{cat.name}</span>
              </li>
            ))}
          </ul>
        )}
      </div>
    </>
  );
};

export default Dropdown;
