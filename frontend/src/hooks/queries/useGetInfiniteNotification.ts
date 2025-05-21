import { useInfiniteQuery } from '@tanstack/react-query';
import { AlarmList } from '../../type/auth/notification';

interface useGetInfiniteNotificationProps<T> {
  queryKey: T[];
  getAlarm: ({ page }: { page?: number }) => Promise<AlarmList>;
}

const useGetInfiniteNotification = <T>({
  queryKey,
  getAlarm,
}: useGetInfiniteNotificationProps<T>) => {
  return useInfiniteQuery({
    queryKey: queryKey,
    queryFn: async ({ pageParam }) =>
      await getAlarm({
        page: pageParam as number,
      }),
    initialPageParam: 0,
    getNextPageParam: (lastPage, allPages) => (lastPage.hasNext ? allPages.length : undefined),
    select: (data) => {
      // 새로 불러온 데이터들이 있다면 기존 데이터들과 매핑 후 반환
      return data.pages.flatMap((page) => page.details || []);
    },
  });
};

export default useGetInfiniteNotification;
