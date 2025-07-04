# sdkman

sdkman은 software development kit manager의 약자로 Java 개발자를 포함한 여러 언어 개발자들이 JDK 및 **기타 개발 도구(SDK)**를 쉽게 설치하고, 버전 간 전환을 할 수 있도록 도와주는 CLI 도구이다.

---

### ✅ 주요 특징

* 다양한 JDK 배포판 (Adoptium, Oracle, Amazon Corretto 등) 설치 및 전환
* Gradle, Maven, Scala, Kotlin 등 여러 SDK 도구도 지원
* 로컬에 여러 버전을 설치해놓고 쉽게 `use`, `default` 명령으로 전환 가능
* 커맨드라인 기반이라 자동화, 스크립트 작성에 적합

---

### 📌 예시 명령어

#### 설치 가능한 목록 보기

```bash
sdk list java
```

#### 특정 JDK 버전 설치

```bash
sdk install java 17.0.9-tem
```

#### 해당 버전 사용하기

```bash
sdk use java 17.0.9-tem
```

#### 기본 JDK 설정

```bash
sdk default java 17.0.9-tem
```

---

### 🛠 지원 도구 예시

| 종류       | 예시                                   |
| -------- | ------------------------------------ |
| Java SDK | OpenJDK, Oracle, Corretto, GraalVM 등 |
| 빌드 도구    | Maven, Gradle                        |
| 언어       | Kotlin, Scala, Ceylon                |
| 프레임워크    | Spring Boot, Vert.x 등                |

---

### 🧩 설치 방법 (macOS/Linux)

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

설치 후 `sdk version` 명령어로 정상 설치 확인 가능.

---

## 맥에서 java directory 확인하기

``` 
# java 패키지 설치 위치
$ cd $HOME/.sdkman/candidates/java

```

해당 위치에 자바가 설치되어 있다.

.sdkman은 숨김 파일이라 소프트 링크 파일을 생성해야 보인다.

```
ln -s ~/.sdkman ~/sdkman
```

