# git 머징하는 방법

로컬 `feature` 브랜치에서 작업 중인데 원격 저장소에 큰 변경 사항이 생겼을 때, 이를 `feature` 브랜치에 업데이트하는 방법은 크게 두 가지가 있습니다. 각각의 장단점을 고려하여 상황에 맞는 방법을 선택하시면 됩니다.

## **1. `git merge`를 사용하는 방법 (가장 일반적이고 권장되는 방법)**

이 방법은 원격 저장소의 변경사항을 `feature` 브랜치에 병합하여 통합하는 방식입니다.

  * **단계:**

    1.  **현재 작업 내용을 커밋하거나 스태시(stash)합니다.**
        ```bash
        git status # 변경사항 확인
        git add .
        git commit -m "WIP: Feature 작업 중 중간 커밋"
        # 또는
        git stash save "Feature 작업 중 임시 저장"
        ```
    2.  **`main` (또는 `master`) 브랜치로 전환합니다.**
        ```bash
        git checkout main
        ```
    3.  **`main` 브랜치를 최신 원격 상태로 업데이트합니다.**
        ```bash
        git pull origin main
        # 또는
        # git fetch origin
        # git merge origin/main
        ```
    4.  **다시 `feature` 브랜치로 전환합니다.**
        ```bash
        git checkout feature
        ```
    5.  **`main` 브랜치의 변경사항을 `feature` 브랜치로 병합합니다.**
        ```bash
        git merge main
        ```
    6.  **충돌이 발생하면 해결합니다.**
        충돌이 발생하면 Git이 알려주며, 파일을 열어 수동으로 충돌을 해결해야 합니다. 충돌 해결 후에는 `git add .`로 변경사항을 스테이징하고 `git commit`으로 병합 커밋을 생성합니다.
    7.  **스태시한 내용이 있다면 다시 적용합니다.**
        ```bash
        git stash pop # 스태시한 내용을 다시 가져옵니다.
        ```

  * **장점:**

      * 가장 직관적이고 일반적인 방법입니다.
      * 기존 커밋 이력을 그대로 유지합니다.
      * 병합 커밋이 생성되어 언제 병합이 이루어졌는지 명확하게 알 수 있습니다.

  * **단점:**

      * 병합 커밋이 생성되어 커밋 이력이 다소 복잡해질 수 있습니다.
      * 충돌 해결이 필요할 수 있습니다.

---

## **2. `git rebase`를 사용하는 방법 (깔끔한 커밋 이력을 선호할 때)**

이 방법은 `feature` 브랜치의 커밋들을 `main` 브랜치의 최신 커밋 위로 "재배치"하는 방식입니다. `feature` 브랜치의 커밋 이력이 `main` 브랜치의 최신 커밋 이후에 시작되는 것처럼 보여서 커밋 이력이 선형적으로 깔끔해집니다.

  * **단계:**

    1.  **현재 작업 내용을 커밋하거나 스태시(stash)합니다.** (`git merge`와 동일)
        ```bash
        git status
        git add .
        git commit -m "WIP: Feature 작업 중 중간 커밋"
        # 또는
        git stash save "Feature 작업 중 임시 저장"
        ```
    2.  **`main` (또는 `master`) 브랜치로 전환합니다.** (`git merge`와 동일)
        ```bash
        git checkout main
        ```
    3.  **`main` 브랜치를 최신 원격 상태로 업데이트합니다.** (`git merge`와 동일)
        ```bash
        git pull origin main
        # 또는
        # git fetch origin
        # git merge origin/main
        ```
    4.  **다시 `feature` 브랜치로 전환합니다.** (`git merge`와 동일)
        ```bash
        git checkout feature
        ```
    5.  **`feature` 브랜치를 `main` 브랜치 위로 리베이스합니다.**
        ```bash
        git rebase main
        ```
    6.  **충돌이 발생하면 해결합니다.**
        리베이스 중 충돌이 발생하면 Git이 알려줍니다. 충돌을 해결한 후에는 `git add .`로 변경사항을 스테이징하고 **`git rebase --continue`** 명령어를 사용하여 리베이스를 계속 진행합니다. (절대 `git commit`을 사용하지 마세요\!)
        만약 리베이스를 중단하고 싶다면 `git rebase --abort`를 사용합니다.
    7.  **스태시한 내용이 있다면 다시 적용합니다.** (`git merge`와 동일)
        ```bash
        git stash pop
        ```

  * **장점:**

      * 커밋 이력이 선형적으로 깔끔하게 유지됩니다. 불필요한 병합 커밋이 생기지 않습니다.
      * 나중에 `feature` 브랜치를 `main` 브랜치로 병합할 때 "Fast-forward" 병합이 가능하여 더욱 깔끔합니다.

  * **단점:**

      * 리베이스는 기존 커밋의 해시(ID)를 변경하므로, **이미 원격 저장소에 푸시된 브랜치에는 절대 리베이스를 사용해서는 안 됩니다.** (다른 사람과의 협업에 혼란을 줄 수 있습니다.)
      * 충돌 해결 과정이 `merge`보다 다소 복잡하게 느껴질 수 있습니다. 각 커밋마다 충돌을 해결해야 할 수도 있습니다.

**어떤 방법을 선택해야 할까요?**

  * **일반적으로 `git merge`를 사용하는 것이 안전하고 권장됩니다.** 특히 협업 환경에서 이미 원격에 푸시된 브랜치라면 `merge`가 유일한 옵션입니다.
  * **개인적으로 작업하는 브랜치이거나, 아직 원격에 푸시하지 않은 브랜치이고 깔끔한 커밋 이력을 선호한다면 `git rebase`를 고려해볼 수 있습니다.**

두 방법 모두 장단점이 있으니, 상황과 팀의 워크플로우에 맞춰 선택하시면 됩니다.