# CloudFormation 템플릿 구조


CloudFormation Template은 AWS 리소스를 정의하는 **JSON 또는 YAML 파일**로, **"AWS 인프라 설계도"** 역할을 합니다.
이 템플릿에는 리소스를 생성, 구성, 연결하는 모든 정보가 들어 있으며, 전체 구조는 다음과 같은 주요 섹션으로 구성됩니다.

---

## ✅ CloudFormation Template의 기본 구조

```yaml
AWSTemplateFormatVersion: '2010-09-09'    # (선택) 템플릿 버전
Description: 템플릿 설명                 # (선택) 설명

Metadata:                                 # (선택) 템플릿에 대한 메타정보
  Version: 1.0
  Author: jin-ha

Parameters:                               # (선택) 외부에서 입력받을 변수
  InstanceTypeParam:
    Type: String
    Default: t2.micro
    AllowedValues:
      - t2.micro
      - t3.micro

Mappings:                                 # (선택) 지역(region) 또는 조건에 따른 값 맵핑
  RegionMap:
    ap-northeast-2:
      AMI: ami-12345678
    us-east-1:
      AMI: ami-87654321

Conditions:                               # (선택) 특정 조건이 맞을 때만 리소스를 생성
  IsKoreaRegion: !Equals [ !Ref "AWS::Region", "ap-northeast-2" ]

Resources:                                # (필수) 생성할 실제 AWS 리소스 정의
  MyEC2Instance:
    Type: AWS::EC2::Instance
    Properties:
      InstanceType: !Ref InstanceTypeParam
      ImageId: !FindInMap [ RegionMap, !Ref "AWS::Region", AMI ]

Outputs:                                  # (선택) 생성 후 사용자에게 출력할 값
  InstanceId:
    Description: EC2 인스턴스 ID
    Value: !Ref MyEC2Instance
```

---

## 🧱 주요 섹션 상세 설명

| 섹션                         | 필수 여부 | 설명                                          |
| -------------------------- | ----- | ------------------------------------------- |
| `AWSTemplateFormatVersion` | ❌     | 템플릿 형식 버전 (항상 '2010-09-09' 사용)              |
| `Description`              | ❌     | 템플릿에 대한 설명                                  |
| `Metadata`                 | ❌     | 템플릿 자체에 대한 정보 (작성자, 버전 등)                   |
| `Parameters`               | ❌     | 템플릿 외부에서 값 입력받기 위한 변수 정의 (ex. 인스턴스 타입, 리전)  |
| `Mappings`                 | ❌     | 리전별 값 매핑 (ex. 리전별 AMI ID)                   |
| `Conditions`               | ❌     | 특정 조건일 때만 리소스를 생성할 수 있도록 정의                 |
| `Resources`                | ✅     | 실제로 생성될 리소스를 정의하는 가장 핵심 섹션                  |
| `Outputs`                  | ❌     | Stack 생성 후 외부에 전달하고 싶은 값 (ex. URL, IP 주소 등) |

---

## 🔧 대표적인 리소스 정의 예시 (`Resources` 섹션)

```yaml
Resources:
  MyBucket:
    Type: AWS::S3::Bucket
    Properties:
      BucketName: my-sample-bucket-1234

  MyLambda:
    Type: AWS::Lambda::Function
    Properties:
      FunctionName: my-function
      Handler: index.handler
      Role: arn:aws:iam::123456789012:role/lambda-role
      Runtime: nodejs18.x
      Code:
        S3Bucket: my-lambda-code-bucket
        S3Key: lambda-code.zip
```

---

## 📤 Outputs 예시

```yaml
Outputs:
  BucketName:
    Description: S3 버킷 이름
    Value: !Ref MyBucket

  LambdaARN:
    Description: 람다 함수의 ARN
    Value: !GetAtt MyLambda.Arn
```

---

## 📌 정리: 최소 구성 예제 (YAML)

```yaml
AWSTemplateFormatVersion: '2010-09-09'
Resources:
  MyBucket:
    Type: AWS::S3::Bucket
```

이게 최소 구성 템플릿이며, 실제로는 보통 `Parameters`, `Mappings`, `Outputs`를 함께 사용해서 유연하게 만듭니다.

---

## ➕ 참고: 자주 사용하는 함수들

| 함수                               | 설명                    |
| -------------------------------- | --------------------- |
| `!Ref`                           | 파라미터, 리소스 등을 참조       |
| `!GetAtt`                        | 리소스의 속성값(예: ARN) 가져오기 |
| `!Sub`                           | 문자열 내 변수 치환           |
| `!FindInMap`                     | Mappings에서 값 가져오기     |
| `!Join`, `!Split`                | 문자열 처리 함수             |
| `!If`, `!Equals`, `!And`, `!Not` | 조건 로직 처리              |

---

필요하다면 CloudFormation 템플릿으로 VPC, EC2, ALB, RDS 등 실무에서 자주 쓰는 예제를 만들어 줄 수도 있어. 원하면 어떤 리소스를 만들고 싶은지 알려줘.
