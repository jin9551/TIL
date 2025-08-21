# git 커밋 확인


### 커밋 해쉬 

``` bash
# 전체 커밋 로그 확인
git log 
# -n은 최근 몇개까지만 보여줄지 정하는 입력값
git log -n 

# 결과 예시
# commit 옆의 해쉬값이 커밋 해쉬다.
# commit ebce5a644efc6be619a616a7b77b5c68a34fa04e (HEAD -> user_session)
# Author: Jin Jeong <jeong.jinha@abc.com>
# Date:   Thu Aug 21 15:41:39 2025 +0900
```

### 커밋 될 파일 목록 (파일 이름만)

``` bash
git show --name-only [커밋 해쉬]
```

### 커밋 될 파일들의 변화 확인

``` bash
git show [커밋 해쉬]
``` 

