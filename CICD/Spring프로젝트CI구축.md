# GitHub Actions로 Spring 프로젝트 CI 구축하기
* 내 블로그 포스트 : https://hobbyatelier.tistory.com

이번 글에서는 개인 프로젝트 진행하면서 CI 구축을 해보고 그 방법과 경험을 작성하려고 한다.

우선 AWS 서버가 준비가 된 게 없어서 CD는 나중에 구축할 예정이다.
 

 
## GitHub Actions란?

GitHub Actions는 github에서 제공하는 서비스로 빌드, 테스트, 배포까지 CI/CD 자동화를 편리하게 해 준다.
 
 

 
## GitHub Actions로 CI 구축하기

우선 내 프로젝트가 이미 깃헙 레포지토리에 올라가 있다는 가정하에 작성하겠다.
 
### 1. 워크플로우(workflow) 작성

워크플로우란 레포지토리에 사용자가 지정한 이벤트(푸시, 풀, 등...)가 레포지토리에서 발생할 시 트리거되어 동작하는 자동화 프로세스이다.

워크플로우는 깃헙에서 제공하는 템플릿이 많으니 굳이 머리 아프게 경로,파일 생성하고 할 필요가 없다.

레포지토리에서 상단 메뉴를 보면 'Actions'라는 메뉴가 보일 거다.
 
Actions 탭을 눌러보면 여러 가지 템플릿을 보여주는데. 레포지토리에 올라온 프로젝트의 소스를 보고 추천 템플릿을 제공한다.

나는 프로젝트를 Maven을 이용하여 빌드했기 때문에 'Java with Maven'을 선택해 주었다.
 
 ![img1 daumcdn](https://github.com/user-attachments/assets/761f882c-6834-47a4-8463-a0b35287e618)
 
추천 탬플릿들

![img1 daumcdn](https://github.com/user-attachments/assets/1a6939e0-8d8f-4ee9-b509-a378fbabfb25)

내가 선택한 템플릿

``` java
name: Java CI with Maven

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
    - name: Build with Maven
      run: mvn -B package --file pom.xml

    # Optional: Uploads the full dependency graph to GitHub to improve the quality of Dependabot alerts this repository can receive
    - name: Update dependency graph
      uses: advanced-security/maven-dependency-submission-action@571e99aab1055c2e71a1e2309b9691de18d6b7d6
```
 
기본으로 생성된 템플릿(maven.yml)은 이렇다.
 

 
위에서부터 하나하나 설명을 하겠다. 
 
## 1. 이벤트 트리거 설정
on은 무슨 이벤트가 발생했을 때 워크플로우가 트리거 되어 동작할 건지 지정하는 것이다.

나는 master 브랜치에 내 프로젝트를 올려놨으니 master 브랜치에 push나 pull 이벤트가 발생할 경우 동작할 거라 명시했다.

``` java
on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]
```
 
 
## 2. 작업 단계 설정

jobs는 여러 스태프로 구성되고 사용자가 지정한 순서대로 동작한다. 

runs-on은 jobs가 내 워크플로우가 깃헙 Actions에서 제공하는 VM 중 어떤 운영체제를 사용하여 실행되는지 명시하는 것이다. 

빌드 환경이 중요한 것이 아니라면 ubuntu-latest를 써도 상관없다.
 
steps는 앞으로 실행될 단계들을 나타낸다. 

``` java
jobs:
  build:
    runs-on: ubuntu-latest <br>
    steps:
```
 
 
## 3.  자바 설치 및 Maven 캐싱
 - uses: actions/checkout@v4 <br>

* @v4는 깃헙 Actions 4 버전 사용한다는 의미이고 
* actions/checkout은 현재 저장소의 코드를 클론 해오는 역할이다.

 
 uses : actions/setup-java 액션을 통해 Java 11 환경 구성
 
 with:
 
 java-version : '11' 자바 버전은 11을 쓸 것이고
 
 distribution : 'temurin' 테무린은 이클립스 재단이 관리하는 오픈 소스 Java SE 빌드이다.  테무린 외에 다른 Java 빌드는 zulu, corretto 등이 있는데 테무린으로 해도 문제는 없다. 
 
 cache: maven으로 .m2/repository 캐시 적용 (다음 빌드 속도 향상용)
 

``` java
    - uses: actions/checkout@v4
    - name: Set up JDK 11
      uses: actions/setup-java@v4
      with:
        java-version: '11'
        distribution: 'temurin'
        cache: maven
```
 
## 4. 테스트 설정
빌드 전에 간단한 테스트도 진행할 것이다.

run : mvn test  JUnit 등의 단위 테스트를 실행

실패 시 workflow 전체가 실패 처리된다.

굳이 테스트가 필요 없다면 제외하면 된다.

 ``` java
    - name: Run tests
      run: mvn test
```
 
 
## 5. Maven 빌드 수행
run : mvn -B: batch 모드 (CI에 적합, 불필요한 입력 제거)

package: 프로젝트를 .war 또는 .jar로 패키징함 - 추후 설정 예정

기본적으로 target/ 폴더에 결과물이 생성된다.

``` java
    - name: Build with Maven
      run: mvn -B package --file pom.xml
```
 
## 6. 빌드 결과물 업로드

target/ 디렉터리에 있는 .war 파일을 깃헙 Actions Artifcat로 업로드

``` java
    - name: Upload WAR artifact
      uses: actions/upload-artifact@v4
      with:
        name: my-artifact
        path: target/*.war
```
 
 
## 최종 결과물 

``` java
name: myProject - CI with Maven

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 11
      uses: actions/setup-java@v4
      with:
        java-version: '11'
        distribution: 'temurin'
        cache: maven
        
    # Testing    
    - name: Run tests
      run: mvn test
      
    # Building
    - name: Build with Maven
      run: mvn -B package --file pom.xml

    # Upload war file
    - name: Upload WAR artifact
      uses: actions/upload-artifact@v4
      with:
        name: my-artifact
        path: target/*.war
```
 
워크플로우 작성이 끝났다면 내 레포지토리에 test.text 파일과 같은 임시 파일을 만들어서 push 해보면 잘 동작하는지 확인할 수 있다. 
 
빌드 성공할 경우 V 초록색 아이콘이 보일 것이고 실패할 경우 X 빨간색 아이콘이 보일 것이다.

 ![img1 daumcdn](https://github.com/user-attachments/assets/07edf8d6-ee03-4e5e-8983-90be2eb306a9)


실패한 빌드를 클릭하여 들어가 보면 왜 실패했는지 오류 로그가 싹 나오니 오류 내역을 보고 하나하나 해결하면 결국 성공할 것이다.
 
성공한 빌드를 클릭하면 Artifacts라는 항목이 아래에 보일 것이다.

Artifact 위치

![img1 daumcdn](https://github.com/user-attachments/assets/cdcbc193-75bd-416f-bf73-c9b12b9e0e9e)


 
아티팩트 옆에 다운로드 아이콘을 누르면 빌드된 war 파일을 받을 수 있다.
 
 

 
 
이것으로 GitHub Actions를 이용한 CI 구축 방법에 대해 적어봤다. 나중에 시간이 나면 AWS EC2에 CD까지 구축하는 방법에 대해 작성해 보겠다.

출처: https://hobbyatelier.tistory.com/111 [취미공방:티스토리]
