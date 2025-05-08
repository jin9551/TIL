# git 작업 영역 구분(구조)

Git은 세 가지 작업 영역으로 관리한다.

* Working Directory(작업 폴더) : 저장소가 추적 중인 파일들이 위치하는 영역
* Staging Area(스테이지) : 커밋할 준비가 된(staged) 파일들이 위치하는 영역
* Repository(저장소) : 커밋되어 버전을 관리하는 파일들이 위치하는 영역. 이 영역의 파일이 수정되면 Working Directory 영역으로 이동한다.

```
// git 구조
         +-------------------+  
작업 공간 | Working Directory |
         +-------------------+ 
               |         ^ 
   git add     |         | git reset files
               v         |
         +-------------------+       +-------------------+  
커밋 준비 |   Staging Area    |   === |   git stash/pop   |  임시공간(Stash)
         +-------------------+       +-------------------+
               |         ^
git commit     |         | git reset
               v         |  
         +-------------------+  
         | Repository        | Index Area
         +-------------------+   

```


## Stash란?

Git stash는 변경사항을 일시적으로 저장하는 기능으로, 아직 커밋하기에 이른 경우나 다른 브랜치로 체크아웃할 때 변경사항을 유지하고 싶을 때 사용한다.


### stash를 사용할 수 있는 상황 예시 


A 브랜치에서 작업을 하다가 변경사항들을 commit 하지 않고 B 브랜치로 checkout 하고 싶을 때.

만약 이런 상황에서 B 브랜치로 checkout 하면 아마도 아래와 같은 에러 메시지를 볼 수 있다.

```
error: Your local changes to the following files would be overwritten by checkout:
        application/controllers/Login.php
Please commit your changes or stash them before you switch branches.
Aborting
```

아직 commit 하고 싶지 않거나 준비되지 않았을 때, git stash를 사용해볼 수 있습니다.


### stash 명령어

| 명령어 | 설명                               |
|-------|-----------------------------------|
|  **git stash**  | Staged 상태에 있는 커밋되지 않는 변경 내역을 stash라는 임시 공간에 저장한다.          |
|  **git stash pop**  | stash에 마지막으로 저장된 변경 내역을 현재 브랜치에 적용한다.         |
|  **git stash list**  | stash에 저장된 변경 내역의 목록을 출력한다.         |
|  **git stash drop**  | 마지막으로 저장된 변경 내역을 삭제한다.          |