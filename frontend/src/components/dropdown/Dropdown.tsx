import { Category } from "../../pages/AddQuestionPage";
import QuestionCategory from "../../type/questionCategory";

import Circle from "../circle/Circle";

interface DropdownProps {
    isOpen : boolean, 
    onClick : () => void,
    handleSelect : (cat: Category) => void,
    categories : Category[], 
    selected : Category,
    categoryId : number,
}

const Dropdown = ({
    isOpen = false, 
    onClick,
    handleSelect,
    categories, 
    selected,
    categoryId,
} : DropdownProps) => {
    return (
        <>
        <div className="relative w-full text-white font-desc">
            <div
              onClick={onClick}
              className={`flex justify-between items-center px-4 py-2 
                bg-white/10 backdrop-blur-md border border-white/20 cursor-pointer 
                transition 
                ${isOpen ? "rounded-t-md rounded-b-none" : "rounded-md"}`}
            >
              <div className="flex items-center gap-2">
              <Circle category={selected.category as QuestionCategory} size={20} animate />
                <span className='pl-1'>{selected.name}</span>
              </div>
              <img src="/icons/dropdownIcon.svg" alt="드롭다운 버튼" className={`w-4 h-4 transform transition-transform duration-200 ${isOpen ? "rotate-180" : ""}`} />
            </div>
              {isOpen && (
                <ul className="absolute z-10 w-full max-h-72 overflow-y-auto bg-white/10 backdrop-blur-md shadow-lg">
                  {categories.map((cat) => (
                    <li
                      key={cat.id}
                      onClick={() => handleSelect(cat)}
                      className={`flex items-center gap-2 px-4 py-2 cursor-pointer text-sm hover:bg-white/20 transition 
                        ${cat.id === selected.id ? "bg-[#96567c7c]" : ""}`}
                    >
                      <Circle category={cat.category as QuestionCategory} size={20} animate />
                      <span className='pl-1'>{cat.name}</span>
                    </li>
                  ))}
                </ul>
              )}
          </div>
          <ul className="font-desc flex flex-col list-none gap-1 text-white">
            <span className="text-[#999999]">(예시)</span>
            {categories[categoryId - 1]?.examples.map((example, index) => (
              <li key={index}>"{example}"</li>
            ))}
          </ul>
        </>
    )
}

export default Dropdown;