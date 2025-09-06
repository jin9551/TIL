# git 원격 브랜치로 푸시

### 원격 브랜치와 로컬 브랜치의 **이름이 다를** 때

```
# 로컬 my-new-feature를 원격 origin의 new-feature로 푸시
git push origin my-new-feature:new-feature
```

### 원격 브랜치와 로컬 브랜치의 **이름이 같을** 때

```
# 로컬 mfa-auth-flow를 원격 origin의 mfa-auth-flow로 푸시
git push origin mfa-auth-flow
```

### 명령어의 의미

  * **`git push`**: 로컬 저장소의 변경사항을 원격 저장소로 보내는 명령어입니다.
  * **`origin`**: 원격 저장소의 이름입니다. 일반적으로 GitHub, GitLab, Bitbucket 등 원격 저장소를 `origin`이라고 부릅니다.
  * **`mfa-auth-flow`**: 푸시할 로컬 브랜치의 이름입니다.

이 명령어를 실행하면 Git은 로컬 `mfa-auth-flow` 브랜치에만 존재하는 모든 커밋들을 원격 `origin` 저장소의 `mfa-auth-flow` 브랜치로 전송합니다.

### 추가 팁: `-u` 옵션

첫 번째 푸시를 할 때는 **`-u`** (또는 `--set-upstream`) 옵션을 함께 사용하는 것이 편리합니다.

```bash
git push -u origin mfa-auth-flow
```

이 옵션은 로컬 `mfa-auth-flow` 브랜치를 원격 `origin/mfa-auth-flow` 브랜치와 연결(tracking)해 줍니다. 이렇게 해두면 다음부터는 단순히 **`git push`** 명령어만 입력해도 Git이 자동으로 올바른 원격 브랜치로 푸시해 줍니다.

-----

### 명령어 오해 바로잡기

이전의 `git push origin/mfa-auth-flow mfa-auth-flow` 명령어는 Git이 \*\*'origin/mfa-auth-flow'\*\*를 별도의 원격 저장소 이름으로 인식하여 오류가 발생했던 것입니다. `origin`이 원격 저장소 이름이고, 그 뒤에 오는 `mfa-auth-flow`가 푸시할 브랜치 이름이라는 것을 기억하면 혼동 없이 명령어를 사용할 수 있습니다.