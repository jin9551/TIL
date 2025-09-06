# 깃 원격 브랜치 설정하기

`git branch --set-upstream-to` 명령어는 로컬 브랜치(혹은 feature branch) 간의 추적 관계를 설정하거나 변경할 때 사용된다 이 명령어는 특히 로컬 브랜치가 원격 브랜치와 동기화되어야 할 때 중요하다.

#### 추적 브랜치 설정의 중요성

추적 브랜치를 올바르게 설정하는 것은 원격 저장소와의 작업을 원활하게 하고, 혼란을 방지하는 데 중요하다. 특히 팀 환경에서는 이러한 설정이 협업을 더욱 효율적으로 만들어준다.

### 추적 브랜치 설정하기

로컬 브랜치를 원격 브랜치와 연결하기 위해 `--set-upstream-to` 옵션을 사용한다. 이렇게 설정하면, 추후 `git pull`이나 `git push` 명령을 사용할 때 원격 브랜치를 명시할 필요가 없어진다.

```
# origin/remote-branch를 로컬 브랜치의 추적 브랜치로 설정
git branch --set-upstream-to=origin/remote-branch local-branch

# 혹은 간단하게
git branch -u origin/remote-branch local-branch
```

---

### 추적 관계 확인하기

로컬 브랜치와 원격 브랜치 간의 현재 추적 관계는 `git branch -vv` 명령어로 확인할 수 있다.

``` 
# 모든 브랜치의 추적 관계 확인
git branch -vv
```

---

### 기본 브랜치의 추적 변경하기

프로젝트에서 기본 브랜치의 이름이 변경되었거나 다른 원격 브랜치를 추적하고자 할 때 `--set-upstream-to`를 사용한다.

```
# 기본 브랜치의 추적 브랜치 변경
git branch --set-upstream-to=origin/new-default-branch
```

---

### 추적 관계 해제하기

특정 브랜치의 추적 관계를 해제하려면 `--unset-upstream` 옵션을 사용한다.

```
# 추적 관계 해제
git branch --unset-upstream local-branch
```

---

> 참고 : https://www.codingmax.net/courses/git-commands/section02/lec0004