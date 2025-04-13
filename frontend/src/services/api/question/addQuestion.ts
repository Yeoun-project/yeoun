import client from "../client";

import UserType from "../../../type/auth/UserType";

const getAddQuestionUrl = (type: UserType) => {
    if(type === 'User') {
        return '/api/question';
    }
    else {
        return '';
    }
}

export const addUserQuestion = async (userType: UserType, content: string, categoryId: number) => {
    const response = await client.post(getAddQuestionUrl(userType), { content, categoryId })
    
    return response;
}