import { FetchNextPageOptions, InfiniteQueryObserverResult } from '@tanstack/react-query';
import { Question } from '../../type/question';

interface ListMoreButtonProps {
  fetchNextPage: (
    options?: FetchNextPageOptions
  ) => Promise<InfiniteQueryObserverResult<Question[], Error>>;
}

const ListMoreButton = ({ fetchNextPage }: ListMoreButtonProps) => {
  return (
    <div>
      <button
        className="font-desc flex w-full cursor-pointer items-center justify-center gap-1"
        onClick={async () => await fetchNextPage()}
      >
        <span className="text-sm">더보기</span>
        <span className="block min-h-4 min-w-4 -rotate-90 bg-[url(/icons/left.svg)] bg-contain bg-center bg-no-repeat" />
      </button>
    </div>
  );
};

export default ListMoreButton;
