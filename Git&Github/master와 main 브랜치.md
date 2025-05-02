# Git master와 main 브랜치

Git에서 오래전에 사용되던 기본 브랜치를 master라고 한다.

현재는 main 브랜치를 기본으로 권장하고 있는데, master에서 main으로 바뀐 이유가 조금 머시기하다.

master의 어감때문인지 인종차별, 주종관계가 연상되는 어휘를 사용하지 말자는 운동이 일어나 바뀌게 된것이다. 

아무튼 Git의 master는 main과 이름만 다를 뿐이지 기본 브랜치이며, Github의 main도 마찬가지로 기본 브랜치이다.

## master를 main으로 변경

master를 main으로 변경하는 방법은 다음과 같다.


### 기존 브랜치 이름 변경
``` 
# 현재 master 브랜치를 main으로 이름 변경
git branch -m master main
```

### 로컬 브랜치의 변경 사항을 원격 저장소에 반영
```
# 새로 이름을 변경한 main 브랜치를 원격 저장소에 업로드
git push -u origin main 
# 기존의 master 브랜치를 원격 저장소에서 삭제
git push origin --delete master
```

## Git 기본 브랜치 이름 변경

```
# 전역으로 기본 브랜치를 메인으로 수정
git config --global init.defaultBranch main
```

## 바뀐 Git 설정 확인
```
git config --list
# 혹은
git config -l
```