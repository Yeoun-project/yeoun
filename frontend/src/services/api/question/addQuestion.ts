import client from "../client";

export const verifiedQuestion = async (content: string, categoryId: number) => {
    const response = await client.post('/api/question/forbidden-words', {
        content: content,
        categoryId: categoryId,
    })

    return response;
}

export const addUserQuestion = async (content: string, categoryId: number) => {
    const response = await client.post('/api/question', { 
        content : content, 
        categoryId : categoryId,
    });
    
    return response;
}