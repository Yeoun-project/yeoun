const Item = ({
  children,
  currentItem,
  idx,
}: {
  children: React.ReactNode;
  currentItem: number;
  idx: number;
}) => {
  const getScaleAndTranslate = () => {
    if (idx === currentItem - 1) {
      return `translate(8.5rem) scale(0.8)`;
    }
    if (idx === currentItem + 1) {
      return `translate(-8.5rem) scale(0.8)`;
    }

    return `translate(0) scale(1)`;
  };

  return (
    <div
      className="flex w-full shrink-0 touch-none justify-center will-change-transform select-none"
      style={{
        transform: getScaleAndTranslate(),
        transitionDuration: '200ms',
        transitionTimingFunction: 'ease-out',
      }}
    >
      {children}
    </div>
  );
};

export default Item;
