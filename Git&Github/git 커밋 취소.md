# git 커밋 취소


| 명령어 | 설명                               |
|-------|-----------------------------------|
|  **git reset 커밋**  | 대상 커밋 이후에 생긴 모든 커밋을 취소한다. 하지만 커밋과 함께 변경된 내역은 로컬 저장소에 남겨둔다.          |
|  **git reset --hard 커밋**  | 커밋을 취소하고 파일들을 unstaged 상태로 워킹디렉토리에서 삭제한다.     |
|  **git reset --soft 커밋**  | 커밋을 취소하고 파일들을 staged 상태로 워킹디렉토리에 보존한다.       |
|  **git reset --mixed 커밋**  | (기본,default) 커밋을 취소하고 파일들을 unstaged 상태로 워킹디렉토리에 보존한다.       |
|  **git revert  커밋ID** | 레포지토리에 반영된 내용을 취소해야하는 경우 사용됨         |

> 여기서 '커밋'은 **HEAD**(가장 최근 커밋)이 들어가거나 다른 **commit id**를 넣는다.
>> HEAD의 예시로는 **HEAD^**(가장 최근거만)
>> **HEAD~2**(마지막 2개의 commit 취소)


> 참고 1 (reset revert 개요 및 사용법): https://velog.io/@njs04210/Git-reset%EA%B3%BC-revert-%EC%95%8C%EA%B3%A0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0

> 참고 2 : https://gmlwjd9405.github.io/2018/05/25/git-add-cancle.html