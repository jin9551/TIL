# Redis 기본 명령어 익히기

가장 많이 쓰는 7가지 명령어만 알아보도록 하겠다.

## 1. 데이터(Key, Value) 저장하기

``` bash
# 데이터 저장
# set [key 이름] [value]
$ set jinha:name "jinha jeong" // 띄워쓰기를 쓰려면 쌍따음표가 필요
$ set jinha:hobby game


# 데이터 조회
$ get jinha:name // 아까 저장한 jinha:name의 value를 가져온다.
jinha jeong

```

## 2. 모든 데이터 조회

``` bash
$ keys *
1) "jinha:name"
2) "jinha:hobby"
```


## 3. 데이터 삭제 (Key로 삭제)

``` bash
# del [key]
$ del jinha:hobby
(integer) 1 // 삭제가 되었다는 뜻

$ get jinha:hobby
(nil)       // 없는 데이터 조회 시 nil이라고 뜸
$ keys *
1) "jinha:name"
```



## 4. 데이터 만료 시간 설정(TTL, Time To Live)

RDBMS와 다르게 데이터 저장 시에 만료 시간을 정할 수 있다. (일정 시간이 지나면 삭제)

레디스의 특성상 메모리 공간이 한정되어 있기 떄문에 모든 데이터를 레디스에 저장할 수 없다. 따라서 만료시간을 활용해 자주 사용하는 데이터만 레디스에 저장해놓고 쓰는 식으로 활용한다.

``` bash

# set [key] [value] ex [만료 시간(초)]
$ set jinha:pet dog ex 30

```

### 만료 시간 확인 

``` bash

# ttl [key]
$ ttl jinha:pet
(integer) 15 // 15초 남음
$ ttl jinha:pet
(integer) 1
$ ttl jinha:pet
(integer) 0
$ ttl jinha:pet
(integer) -2   // 만료 시간이 지나서 삭제가 되면 -2를 반환한다.
               // 혹은 key가 존재하지 않아도 -2를 반환한다.


$ ttl jinha:name
(integer) -1   // ttl를 설정하지 않았다면 -1를 반환한다.

```


## 5. 모든 키값 삭제

``` bash
$ flushall //모든 데이터 삭제

$ keys *
(empty array)  // 저장소가 비었다는 뜻.

```



* * *

이로써 redis의 기본 명령어 중 7가지(set, get, keys, del, ttl, ex, flushall)
