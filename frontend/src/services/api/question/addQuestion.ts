import client from "../client";

export const addUserQuestion = async (content: string, categoryId: number) => {
    const response = await client.post('/api/question', { 
        content : content, 
        categoryId : categoryId,
    });
    
    return response;
}