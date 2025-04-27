import BackArrowButton from '../button/BackArrowButton';

interface SubPageHeader {
  pageTitle: string;
  backButtonPath?: string;
}

const SubPageHeader = ({ pageTitle, backButtonPath }: SubPageHeader) => {
  return (
    <header className="relative flex justify-center p-6">
      <div className="absolute left-6">
        <BackArrowButton path={backButtonPath} />
      </div>
      <h3 className="w-full text-center">{pageTitle}</h3>
    </header>
  );
};

export default SubPageHeader;
