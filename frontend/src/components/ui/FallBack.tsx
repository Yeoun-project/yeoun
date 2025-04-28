interface FallBackProps {
  desc: string;
  subDesc?: string;
}
const FallBack = ({ desc, subDesc }: FallBackProps) => {
  return (
    <div className="font-desc flex h-full flex-col items-center justify-center gap-1 text-[#AAAAAA]">
      <p>{desc}</p>
      <p className="font-bold">{subDesc}</p>
    </div>
  );
};

export default FallBack;
