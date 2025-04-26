import { JSX } from 'react';

import QuestionCategory from '../../type/questionCategory';

import CATEGORY, { CategoryList } from './Category';

interface ExtendsData {
  icon?: (size?: number) => JSX.Element;
  color?: string;
  examples?: string[];
  test?: string;
}

type ExtendsDataList = {
  [category in QuestionCategory]: ExtendsData;
};

export type ExtendedCategory = CategoryList & ExtendsData;

const extendsCategoryData = (extendsDataList: ExtendsDataList): ExtendedCategory[] => {
  return CATEGORY.filter((category) => extendsDataList[category.category]).map((category) => ({
    ...category,
    ...extendsDataList[category.category],
  }));
};

export default extendsCategoryData;
