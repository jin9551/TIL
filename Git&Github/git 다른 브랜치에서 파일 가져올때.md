# 다른 브랜치에서 새로운 브랜치로 파일 가져올 떄

### 단계별 방법

1.  **새로운 브랜치 생성 및 전환**
    먼저, 원격 master 브랜치의 최신 상태를 로컬로 가져와 업데이트합니다.

    ```bash
    git checkout master
    git pull origin master 
    ```

    이제 깨끗한 `master` 브랜치에서 새로운 브랜치(`new-branch` 등)를 생성하고 전환합니다.

    ```bash
    git checkout -b new-branch 
    ```

2.  **원하는 파일만 옮기기**
    이제 문제가 있는 `old-branch` 브랜치에 있는 파일들 중 필요한 파일만 가져옵니다. `git checkout` 명령어를 사용하면 다른 브랜치의 특정 파일을 현재 브랜치로 가져올 수 있습니다.

    ```bash
    git checkout old-branch -- <파일경로1> <파일경로2>
    ```

    **예시:**
    만약 `src/main/java/com/ge/wca/accountsbrilliongea/auth/LoginActionsSignature.java` 파일이 필요하다면:

    ```bash
    git checkout old-branch -- src/main/java/com/ge/wca/accountsbrilliongea/auth/LoginActionsSignature.java
    ```

    이 명령어는 **해당 파일의 최종 상태**만 가져오기 때문에, 여러 커밋에 걸쳐 변경된 히스토리를 신경 쓸 필요 없이 깔끔하게 파일을 복사해 올 수 있습니다.

3.  **변경 내용 커밋 및 푸시**
    원하는 모든 파일을 가져왔다면, 변경 사항을 커밋하고 새로운 브랜치를 원격 저장소에 푸시합니다.

    ```bash
    git add .
    git commit -m "feat: adding authenticator flow files"
    git push -u origin new-branch
    ```

    이제 `new-branch` 브랜치는 필요한 파일들만 포함한 깨끗한 상태가 됩니다.
