export interface WebPageTest {
    id: number,
    apiKey: string;
    url: string;
    cronExpression: string;
    location : string;
    userAgent: string;
}
