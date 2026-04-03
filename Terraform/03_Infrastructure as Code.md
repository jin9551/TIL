## Infrastructure as Code

#### Resources

* Terraform uses providers to access to resources; for AWS, provider gives access to AWS-specific resources.

* Resources define the actural infrastructure elements, like an S3 bucket, using a consistent syntax.

* You can name resources differently in Terraform code and in AWS; this helps manage unique naming requirements, such as for S3 buckets.

* Parameters inside resources specify details like bucket name and privacy settings, allowing customization of the infrastructure.

##### Simple Examples

``` hcl
# Provider Definition
# A provider isn't a resource. It's what gives you access to resources.
# You need to have a provider defined in your code so that Terraform knows where the
# resources to go.
provider "aws" {
    profile     = "default"
    region      = "us-west-2"
}

# AWS S3 Bucket
# Keyword | Resource Type | Name terraform 
resource "aws_s3_bucket" "tf-course" {
    bucket      = "jinjeong-terraform-2026-04-03"   # Parameter naming the S3 bucket
    acl         = "private"                         # Parameter defining the bucket scope
}
```

1. Name Terrform: 테라폼 코드 내부에서만 유효한 논리 식별자. 테라폼 코드 안에서 이 리소스를 참조할 때 사용(e.g. aws_s3_bucket.tf-course.arn)
2. Parameter naming(Physical Name/Bucket Name): 실제 AWS 인프라에 생성될 물리적인 리소스의 이름.

|구분|tf-course|jinjeong-terraform-2026-04-03|
|---|--------|---------|
|공식 명칭|Resource Name (Local Name)|Argument (Physical Name)|
비유|"우리 집 거실에서 부르는 ""둘째"""|"주민등록상의 본명 ""홍길동"""|
|누가 사용하나?|테라폼 (Terraform)|클라우드 제공자 (AWS)
|코드 내 참조|aws_s3_bucket.tf-course.id|(참조용으로 쓰이지 않음)|



#### Basic Resource Types

* Terraform defines AWS infrastructure using resources like S3 buckets, VPCs, security groups, EC2 instances, and elastic IPs, each with specific parameters.

* Separating configurations (like S3 website config or security group rules) from resources helps manage changes without affecting stored data or other settings.

* Dynamic variables in Terraform allow flexible and reusable code, such as dynamically selecting the latest Ubuntu AMI for EC2 instances.

* Terraform resources can reference each other dynamically, enabling coordinated management of related infrastructure components.

``` hcl
# S3 buckets
resource "aws_s3_bucket" "example" {
    bucket      = "learning-terraform.example.com"
    acl         = "public-read"
    policy      = file("policy.json")
}

resource "aws_s3_bucket_website_configuration" "example" {
    bucket      = "aws_s3_bucket.example.bucket"
    index_document {
        suffix = "index.html"
    }
}

# VPC (Virtual Private Cloud)
resource "aws_vpc" "QA" {
    cidr_block = "10.0.0.0/16"
}

resource "aws_vpc" "Staging" {
    cidr_block = "10.1.0.0/16"
}

resource "aws_vpc" "Prod" {
    cidr_block = "10.2.0.0/16"
}

# AWS Security Group
resource "aws_security_group" "allow_tls" {
    ingress { # inbound traffic
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["1.2.3.4/32"]
    }
    egress { # outbound traffic
        # 0 and -1 means that this group allows any protocol on any port to go out
        from_port   = 0
        to_port     = 0
        protocol    ="-1"
    }
}

# You can separate this blocks
resource "aws_security_group" "allow_tls" { }

resource "aws_security_group" "https_inbound" {
    type        = "ingress"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["1.2.3.4/32"]
    security_group_id = aws_security_group.allow_tls.id
}


# EC2
resource "aws_instance" "blog" {
    ami             = data.aws_ami.ubuntu.id
    instance_type   = "t3.nano"
}

# Dynamic Syntax
# e.g. ip address
resource "aws_eip" "blog" { # resource의 terraform name이 같아도 resource type이 다르면 괜찮다.
    instance = aws_instance.blog.id
    vpc      = true
}
```


#### Terraform Style

* Use **two spaces for indentation** instead of tabs to keep your code consistent and readable.

* **Meta arguments** control how Terraform interprets your code, such as deploying multiple instances or managing resource dependencies, and should be placed at the end of resource blocks.

* Use blank lines to logically separate groups of arguments and blocks, improving code clarity.

* Align equal signs within sections to enhance readability, making your code easier to understand and maintain.


#### Security Group
> [Video](https://www.linkedin.com/learning/learning-terraform-15575129/security-group?autoSkip=true&resume=false&u=2239242)

> [Code](https://github.com/LinkedInLearning/learning-terraform-3087701/blob/03_04/main.tf)

* Use a data block to fetch the default VPC details, which is necessary for associating the security group with the correct network.

* Define the security group resource with a clear name and description to manage it easily in AWS.

* Add inbound (ingress) and outbound (egress) rules as separate resources for clarity, specifying protocols, ports, and allowed IP ranges.

* Attach the security group to your EC2 instance using the vpc_security_group_ids parameter to enforce the rules.

* Apply your Terraform changes to create and update the security group and instance configuration.