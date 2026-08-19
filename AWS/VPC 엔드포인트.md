## VPC 엔드포인트(VPC Endpoint)

VPC 엔드포인트는 VPC 내 리소스들이 VPC 외부의 서비스 (S3, DynamoDB, CloudWatch) 등에 접근할 때 internet gateway, NAT gateway 등의 **외부 인터넷 전송 서비스를 타지 않고 내부 네트워크를 통해 접근**할 수 있도록 지원하는 서비스이다.

AWS VPC는 사설 네트워크로 이루어진 사용자 정의 네트워크이다.

VPC 내 EC2, RDS, ELB 등을 탑재하고 ENI(Elastic Network Interface)에 사설 IP 혹은 공인 IP를 부여하여 사용한다.

그럼 다른 AWS 서비스 S3, Cloudwatch, cloudfront, DDB, API Gateway 등은 어떨까?
이들은 내가 설정한 AWS 리전 내에 존재하지만 VPC 내부에 설치하는 서비스들이 아니다.
즉, 따로 공인 IP를 가지고 외부에서 접근하는 서비스들이다.

만일 VPC 내의 Private subnet에 위치한 EC2 인스턴스에서 S3에 대한 api를 부르게 되면 어떻게 될까?
이는 S3에 접속해서 S3의 정보를 얻어와야 되기 때문에 외부에서의 접근이 필요하게 된다.

EC2(10.0.3.20, Private Subnet) → NAT Gateway → Router → Internet Gateway → 외부 인터넷 → S3

이런식으로 이동하게 된다.

보기엔 통신에 전혀 문제없어 보이지만, 이는 결국 VPC 내부 Resource와 기타 AWS Service Endpoint(EC2 API 호출, S3 접속 등)와 통신 시 외부 인터넷에 공개적으로 연결되며 트래픽이 노출됨을 의미하게 된다.
만일 내부적으로 은밀하게 처리해야 될 api 호출이라면 보안상으로 썩 좋지 않은 방법인 셈이다.
거기다 VPC 밖에서 들어오는 트래픽에는 과금이 되기 때문에 비용이 늘어난다.

따라서 이를 해결하기 위한 것이 VPC Endpoint인 것이다.

앞서 말했듯이 S3는 외부에서 접근해야 하는 서비스지만,

만일 내부 사용자가 사용하는 형태라면, 보안을 위해 외부로 나가지 말고 내부 네트워크로 접근해서 사용하자는 취지인 것이다.

![img](/AWS/images/VPC_endpoint.png)

위의 그림을 보면 AWS Cloud 내에 VPC 엔드포인트가 있고 바로 S3로 연결되어있다.

말그대로 VPC 내부에 엔드포인트를 형성한 뒤, 이 엔드포인트를 통해 AWS 외부 서비스에 도달할 수 있도록 하는 서비스인 것이다.

정리하자면, VPC 엔드포인트는 AWS 여러 서비스들과 VPC를 연결시켜주는 중간 매게체로서, AWS VPC바깥으로 트래픽이 나가지 않고 AWS의 여러 서비스들을 사용할 수 있게 만들어주는 서비스라 할 수 있다.


### VPC 엔드 포인트 종류

VPC 엔드포인트는 Interface Endpoint와 라우팅 테이블 기반의 Gateway Endpoint 두가지 종류로 나뉜다.
이 2개 유형의 다른점은 Access 방식이 부분이다. 
"Interface Endpoint"가 ENI(Elastic Network Interface)를 이용하여 IP가 할당되고 해당 IP로 Access를 하는 방식이라면, 
"Gateway Endpoint"는 Route Table을 이용하여 Endpoint에 Access한다는 것이 다른 점이다.
AWS 서비스마다 사용할 수 있는 VPC Endpoint 유형이 정해져 있으므로 확인 후 선택해야 한다.

* Interface Endpoint : Private IP를 만들어서 서비스로 연결해줌 (SQS, SNS, Kinesis, Sagemaker 등 많은 서비스를 지원)
* Gateway Endpoint : 라우팅 테이블에서 경로의 대상으로 지정하여 사용(S3, DynamoDB 일부만 지원)

![img2](/AWS/images/interface_endpoint.png)
![img3](/AWS/images/gateway_endpoint.png)


### 엔드포인트 설정 방법 예시 
https://inpa.tistory.com/entry/AWS-%F0%9F%93%9A-VPC-End-Point-%EA%B0%9C%EB%85%90-%EC%9B%90%EB%A6%AC-%EA%B5%AC%EC%B6%95-%EC%84%B8%ED%8C%85