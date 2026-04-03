## What is Terraform?

* Terraform is a tool and language for managing cloud infrastructure as code, allowing you to provision and maintain resources like servers, networking, and storage in a centralized way.

* If focuses on infrastructure step (like creating servers and load balancers) but doesn't manage software running on those servers; for that, tools like Puppet are used.

* Terraform supports the conecpt of immutable infrastructure, where servers are replaced rather than modified, enhancing stability and reliability. 
    * 불변 인프라(immutable infrastructure)는 서버나 인프라 디스크의 권한의 read-only로 설정이 되어있어 업데이트나 변경을 위해서는 기존의 것을 파기하고 새로 만들어 교체하는 운영 방식을 말한다.


## Set up your Terraform environment

#### Fork Github repo

Exercise files on Github:
https://github.com/jin9551/learning-terraform-3087701

#### Set up AWS for Terraform

Terraform official documents give a detailed instruction on how to set up an account for Terraform: https://docs.aws.amazon.com/ko_kr/prescriptive-guidance/latest/terraform-aws-provider-best-practices/terraform-aws-provider-best-practices.pdf

* You need to create an AWS IAM user specifically for Terraform with programmatic access and administrator permissions (though in practice, more restricted permissions are recommended).
    * 1. Go to IAM
    * 2. Create a user which Terraform can use. For this practice, give this user an administrator access(AdministratorAccess)

* Generate access keys for this user; these keys allow Terraform to interact with your AWS environment.
    * 1. On Users menu, click on the account that you've created. 
    * 2. Go to Security Credentials tab

* Keep these credentials secure, as they provide full access to your AWS account and misuse could lead to serious consequences.

This setup is essential for managing cloud infrastructure with Terraform effectively and securely.

#### Terraform Cloud

* Start by creating a free Terraform Cloud account and set up an organization and workspace using the version control workflow.

* Connect Terraform Cloud to your GitHub repository to manage your Terraform code.

* Configure sensitive environment variables in the workspace for AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY to allow Terraform to authenticate and manage your AWS infrastructure securely.

This setup is crucial for managing cloud infrastructure efficiently using Terraform in a user-friendly, cloud-based environment.