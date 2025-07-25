# git fetch와 pull의 차이

`git pull`과 `git fetch`는 모두 원격 저장소의 변경사항을 로컬 저장소로 가져오는 명령어지만, 핵심적인 차이가 있습니다. 간단히 요약하면 다음과 같습니다:

  * **`git fetch`**: 원격 저장소의 최신 변경사항(커밋 이력 등)만 로컬 저장소로 가져옵니다. **작업 중인 로컬 브랜치나 워킹 디렉토리는 업데이트하지 않습니다.** 가져온 변경사항은 로컬의 "원격 추적 브랜치(remote tracking branch)"에 반영됩니다. 개발자가 직접 변경사항을 확인하고 수동으로 병합(merge) 또는 리베이스(rebase)를 수행할 수 있게 합니다.

  * **`git pull`**: `git fetch`와 `git merge`를 합쳐놓은 명령어입니다. 원격 저장소의 최신 변경사항을 가져온 다음, **자동으로 현재 작업 중인 로컬 브랜치에 병합(merge)합니다.** 이 과정에서 충돌이 발생하면 해결해야 합니다.

**자세한 차이점:**

| 특징          | `git fetch`                                                                                                                                                           | `git pull`                                                                                                                                                                            |
| :------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **하는 일** | 원격 저장소의 최신 커밋 이력 및 변경사항만 로컬로 가져옵니다.                                                                                                         | 원격 저장소의 최신 커밋 이력 및 변경사항을 가져온 후, 현재 로컬 브랜치에 자동으로 병합(merge)합니다. (내부적으로 `git fetch` + `git merge`와 같음)                                |
| **로컬 브랜치 영향** | 직접적인 영향을 주지 않습니다. 워킹 디렉토리의 파일도 변경되지 않습니다. 가져온 내용은 `origin/main`과 같은 원격 추적 브랜치에 반영됩니다.                           | 현재 로컬 브랜치를 원격 저장소의 최신 상태로 업데이트합니다. 이 과정에서 로컬 파일이 변경될 수 있으며, 충돌이 발생할 수 있습니다.                                                        |
| **병합/통합** | **자동으로 병합하지 않습니다.** 개발자가 `git merge` 또는 `git rebase` 명령어를 사용하여 수동으로 병합해야 합니다.                                                        | **자동으로 병합합니다.** |
| **언제 사용?** |
|               | \* 다른 사람이 작업한 내용을 확인하고 싶지만, 내 작업 내용에 즉시 영향을 주고 싶지 않을 때.\<br/\>\* 병합 전에 변경사항을 꼼꼼히 검토하고 충돌을 미리 예상하고 싶을 때.\<br/\>\* 여러 브랜치의 변경사항을 가져와서 비교하고 싶을 때.                                                                                     | \* 원격 저장소의 최신 내용을 내 로컬 브랜치에 빠르게 적용하고 싶을 때.\<br/\>\* 다른 사람과의 협업이 활발하고, 최신 변경사항을 자주 반영해야 할 때.\<br/\>\* 대부분의 경우 `git pull`을 사용하면 편리합니다. |
| **안전성** | 변경사항을 수동으로 통합하므로 더 안전합니다. 변경사항을 검토할 시간을 가질 수 있습니다.                                                                                    | 자동으로 병합하기 때문에 때로는 예상치 못한 충돌이나 변경이 발생할 수 있습니다.                                                                                                     |

**예시:**

1.  **`git fetch` 사용:**

    ```bash
    git fetch origin
    # 이제 origin/main (원격 추적 브랜치)에 원격 저장소의 최신 변경사항이 반영됩니다.
    # 현재 로컬 브랜치 (예: main)는 변경되지 않습니다.
    git log origin/main # 원격 저장소의 최신 이력을 확인할 수 있습니다.
    git diff main origin/main # 현재 로컬 main 브랜치와 원격 main 브랜치 간의 차이를 확인할 수 있습니다.
    git merge origin/main # 필요한 경우 수동으로 병합합니다.
    ```

2.  **`git pull` 사용:**

    ```bash
    git pull origin main
    # 원격 origin 저장소의 main 브랜치에서 변경사항을 가져와서
    # 현재 로컬 main 브랜치에 자동으로 병합합니다.
    ```

**결론적으로,**

  * **`git fetch`는 "원격 저장소의 상태를 가져오기만 하는 것"**
  * **`git pull`은 "`git fetch` 후 `git merge`를 자동으로 수행하는 것"**

라고 이해하시면 됩니다. 더 세밀하게 제어하고 싶거나, 병합 전에 변경사항을 검토할 필요가 있을 때는 `git fetch`를 사용하고, 단순히 최신 상태로 업데이트하고 싶을 때는 `git pull`을 사용합니다.