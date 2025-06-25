# git 유저 세팅

회사에서 사용하는 깃허브 계정과 내가 사용하던 깃허브 계정이 두 개가 있는 상황이고, 깃허브 데스크톱 앱은 내 개인 계정으로 사용하기 위해 깃 설정을 건드려야 하는 상황이 왔다.

```
# 깃허브 데스크톱 - 내 개인 계정/로컬
# 깃 글로벌 계정 - 회사 계정

$ git config --list 

credential.helper=osxkeychain
filter.lfs.process=git-lfs filter-process
filter.lfs.required=true
filter.lfs.clean=git-lfs clean -- %f
filter.lfs.smudge=git-lfs smudge -- %f
user.name=Jin Ha Jeong
user.email=w******1@gmail.com

```

깃허브 데스크톱을 먼저 설치했고 내 개인 계정에 있는 레포지토리를 클론하였기 때문에 기본 글로벌 설정으로 내 이름과 이메일 주소가 기입되어 있는 것을 확인할 수 있다.

깃에는 글로벌과 로컬 유저가 있는데, 글로벌의 경우 특정한 레포지토리가 아니면 전체적으로 사용될 깃 유저이다.

* 회사에서 쓸 컴퓨터에 설정하는 것이므로 글로벌 깃 유저는 회사에서 사용하는 이름과 회사 이메일

* 그리고 개인적으로 쓸 클론 경로에는 로컬 계정을 적용할 것이다.


## 글로벌 유저 설정


```
# 글로벌 유저 설정
$ git config --global user.name "Jin Jeong"
$ git config --global user.email "jeong.jinha@*****.com"

# 결과 확인
$ git config --list 

credential.helper=osxkeychain
filter.lfs.process=git-lfs filter-process
filter.lfs.required=true
filter.lfs.clean=git-lfs clean -- %f
filter.lfs.smudge=git-lfs smudge -- %f
user.name=Jin Jeong
user.email=jeong.jinha@*****.com
```



## 로컬 유저 설정

```
# 깃허브 클론 경로로 이동
$ cd Documents/GitHub/TIL/

# 로컬 유저 설정
$ git config --local user.name "Jin Ha Jeong"
$ git config --local user.email "w******1@gmail.com"

# 결과 확인
$ git config --list 

credential.helper=osxkeychain
init.defaultbranch=main
filter.lfs.process=git-lfs filter-process
filter.lfs.required=true
filter.lfs.clean=git-lfs clean -- %f
filter.lfs.smudge=git-lfs smudge -- %f
user.name=Jin Jeong                 # 글로벌
user.email=jeong.jinha@*******.com  # 글로벌
core.repositoryformatversion=0
core.filemode=true
core.bare=false
core.logallrefupdates=true
core.ignorecase=true
core.precomposeunicode=true
submodule.active=.
remote.origin.url=https://******.git
remote.origin.fetch=+refs********/*
branch.main.remote=origin
branch.main.merge=refs/heads/main
branch.main.vscode-merge-base=origin/main
branch.main.vscode-merge-base=origin/main
lfs.repositoryformatversion=0
user.name=Jin Ha Jeong                # 로컬
user.email=w*****1@gmail.com          ㄴ# 로컬

```

클론 경로로 가서 git config --list를 해보면 
user.name과 user.email이 두 개인 것을 확인 가능하다.