export default interface UserAuthInfo {
  email: string;
  questionCount: number;
  oauthPlatform: 'GOOGLE' | 'KAKAO' | 'NAVER';
}
