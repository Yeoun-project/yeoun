import client from "../client";

const getAddQuestionUrl = () => {
    return '/api/question';
}

export const addUserQuestion = async (content: string, categoryId: number) => {
    const response = await client.post(getAddQuestionUrl(), { content, categoryId })
    
    return response;
}