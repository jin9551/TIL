# git stash에 대하여

솔직히 말해서 **stash**라는 명령어는 잘 쓸 일이 없을거라 생각했지만 큰 오산이었다.

예를 들어 `my-project`라는 프로젝트에 2개의 브랜치 `new-branch`와 `old-branch`가 있다고 가정했을 때,
`new-branch`에서 `old-branch`로 체크아웃을 하려고 하면 커밋되지 않은 `new-branch`의 변경 사항은 인텔리제이나 깃의 체크아웃 기능에 의하여 `old-branch`의 내용으로 덮어씌워지거나 사라진다.

이럴 때 `new-branch`의 변경사항을 저장해 두고 싶을 때 사용하는 것이 **stash**이다.

---

## stash 간단 명령어

### 저장

```
# 특정 파일만 stash 영역에 저장하고 싶을 때 :
# git stash push [경로명/파일명]
git stash push src/main/resources/log4j2.xml -m "stashing log files"

# 커밋되지 않은 모든 변경 사항을 stash 영역에 저장하고 싶을 때 :
git stash -m "all changes before switching branch"

# Untracked files까지 저장하고 싶을 때 :
git stash -u -m "all changes including untracked files"
```

### 불러오기

```
# 스태시를 적용하고 삭제할 때
git statsh pop

# 스태시를 적용하고 삭제하지 않을 때 (stash 영역어 보관)
git stash apply
```

### 조회

``` 
# 스태시 목록 확인
git stash list
```

---

### Stash 영역의 저장 기간?

Stash 영역에 저장된 파일은 **명령어로 직접 삭제하거나, 스태시된 변경 사항을 적용한 뒤 `git stash pop`을 사용해 제거하기 전까지는 계속 남아있습니다.**

---

### Stash의 영속성

Git Stash는 임시 저장 공간이지만, 일반적인 임시 파일처럼 사라지는 것이 아닙니다. Git 내부의 스택(stack) 구조에 스냅샷 형태로 저장되며, 이는 마치 커밋(commit)과 비슷한 방식으로 관리됩니다.

* `git stash` 명령어를 사용할 때마다 새로운 스태시가 스택의 맨 위에 추가됩니다.
* `git stash list` 명령어를 사용하면 저장된 스태시 목록과 그 순서를 확인할 수 있습니다.
* `git stash pop`을 사용해 변경 사항을 적용하면 해당 스태시는 목록에서 제거됩니다.
* `git stash drop` 또는 `git stash clear` 명령어를 사용하면 수동으로 스태시를 삭제할 수 있습니다.

### Stash 관리의 중요성

Stash는 편리하지만, 불필요한 스태시를 계속 쌓아두면 목록이 복잡해져서 관리가 어려워질 수 있습니다. 따라서 작업이 완료되면 `git stash pop`을 사용해 스태시를 적용하고 제거하는 것이 좋은 습관입니다.

Stash에 저장된 내용은 Git 레포지토리의 일부이므로, 레포지토리를 삭제하거나 손상시키지 않는 한 그대로 유지됩니다. 하지만 장기 보관을 위한 용도보다는, 브랜치 전환과 같은 단기적인 목적에 맞게 사용하는 것이 좋습니다.
