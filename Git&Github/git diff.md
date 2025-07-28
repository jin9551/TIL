# git diff 명령어

> 참고 : https://kotlinworld.com/278

`git diff`는 Git에서 **파일 간의 변경 사항**을 확인할 수 있는 명령어야. 구체적으로 말하면, 두 버전 간의 **차이(diff)** 를 보여주지.

---

## ✅ 기본 개념

`git diff`는 Git 저장소에서 **작업 디렉토리**, **스테이징 영역(index)**, **커밋(commit)** 사이의 변경 사항을 비교할 수 있어.

---

## 🧱 주요 사용 예시와 설명

### 1. **작업 디렉토리 vs 스테이징 영역**

```bash
git diff
```

* 아직 `git add` 하지 않은 **수정사항**을 보여줌.
* → 로컬에서 파일은 수정했지만 Git이 추적 중인 영역(index)에 아직 반영 안 된 상태.

### 2. **스테이징 영역 vs 마지막 커밋**

```bash
git diff --cached
```

또는

```bash
git diff --staged
```

* `git add`로 staging된 내용과 현재 커밋 사이의 차이를 보여줌.

### 3. **작업 디렉토리 vs 마지막 커밋**

```bash
git diff HEAD
```

* 마지막 커밋 기준으로 현재 작업 디렉토리의 변경 사항 전체를 확인.
* staging된 것과 staging되지 않은 것을 모두 포함.

---

## 🧠 특정 파일 비교

```bash
git diff <파일명>
```

* 해당 파일의 변경 사항만 보여줌.

```bash
git diff HEAD <파일명>
```

* 커밋 기준으로 특정 파일의 전체 변경 사항을 확인.

---

## 🕘 커밋 간 차이 비교

```bash
git diff <commit1> <commit2>
```

---

## 🕘 브랜치 간 차이 비교

```bash
git diff <branch1> <branch2>  # git diff develop create-new-endpoints
```

---

## 📜 출력 형식 예시

```diff
diff --git a/test.txt b/test.txt
index e69de29..4b825dc 100644
--- a/test.txt
+++ b/test.txt
@@ -0,0 +1,2 @@
+Hello world!
+This is a new line.
```

* `+`로 시작하면 추가된 줄
* `-`로 시작하면 삭제된 줄
* `@@`는 변경된 위치 정보를 의미

---

## 🛠 옵션 몇 가지

| 옵션            | 설명                 |
| ------------- | ------------------ |
| `--name-only` | 변경된 파일 이름만 출력      |
| `--stat`      | 파일별로 변경된 라인 수 요약   |
| `--color`     | 색상 강조 출력           |
| `--word-diff` | 라인 대신 단어 단위로 변경 표시 |

---

## 💡 예시 정리

```bash
git diff --name-only                # 어떤 파일이 변경됐는지만 보기
git diff --cached main             # main 브랜치와 staging 영역 비교
git diff HEAD^ HEAD                # 이전 커밋과 현재 커밋 비교
```

---
