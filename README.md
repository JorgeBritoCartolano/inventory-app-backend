# Inventory Management Application

A comprehensive application designed for small businesses to manage their inventory and maintain transaction records efficiently.

## Architecture

![arquitecture.png](images%2Farquitecture.png)

## Features

### 1. Product Management
- **Create Product**: Add new products to the inventory, with attributes such as name, description, price, stock quantity, and category.
- **Update Product**: Modify details of existing products, such as price, stock quantity, or description.
- **Delete Product**: Remove a product from the inventory.
- **List Products**: Display a list of all available products in the inventory, with filtering options by product ID.

### 2. Inventory Management
- **Register Product Entry**: Record the entry of new products into the inventory, increasing stock quantity.
- **Register Product Exit**: Log the exit of products (e.g., sales or returns), reducing stock quantity.
- **View Transaction History**: Display a history of all product entries and exits in the inventory.

### 3. Basic Reports
- **Inventory Report**: Generate a simple report showing the current status of the inventory, including products and quantities.
- **Transaction History**: Display a history of all product entries and exits, including dates and details of the operations.

## Technologies Used

- **Backend**: Java with Spring Framework
- **API Documentation**: Swagger/OpenAPI
- **Database**: AWS RDS MySQL
- **Secrets Management**: AWS Secrets Manager
- **CI/CD Pipeline**: AWS CodePipeline using:
    - GitHub for version control
    - AWS CodeBuild for building the application
    - AWS CodeDeploy for deployment to an EC2 instance
- **Storage**: Amazon S3 for storing inventory reports
- **IAM**: AWS IAM roles for permissions

## Deployment Instructions

### 1. Database Setup
- Create an RDS MySQL instance.
- Store credentials securely using AWS Secrets Manager.
- Use a database management tool such as DBeaver to connect to the instance.
- Create the database 'inventory' using the SQL commands provided in the `utilities/database_setup` file.

### 2. EC2 Instance Setup
- Create an EC2 role with the following permissions:
    - `AmazonEC2RoleforAWSCodeDeploy`
    - `AmazonS3FullAccess`
    - `SecretsManagerReadWrite`
- Launch an EC2 instance, assign the created role, and connect via the console.
- Install Java and the CodeDeploy agent on the EC2 instance. Use the commands provided in `utilities/instance_dependencies`.
- Add an inbound rule to allow traffic on port `8080` from any IP, and another rule to allow HTTP connections from any IP.

### 3. CI/CD Pipeline Setup
- Create an AWS CodePipeline:
    - Set up GitHub as the version control system.
    - For CodeBuild, select the option to "Use a buildspec file" to define the build commands.
    - For CodeDeploy, create a role with the `AWSCodeDeployRole` policy.
    - In the deployment group setup (CodeDeploy), choose `never` for "Install CodeDeploy Agent" since it has already been installed on the instance.

### 4. Access the Application
Once deployed, the application can be accessed using the public IP of your EC2 instance. For example: http://`<public-ip>`:8080/products. Replace `<public-ip>` with the actual public IP of your EC2 instance.

## API Endpoints

### Product Management
- `GET /products`: List all products.
- `GET /products/{id}`: Get a product by ID.
- `POST /products`: Create a new product.
- `PUT /products/{id}`: Update an existing product.
- `DELETE /products/{id}`: Delete a product by ID.

### Inventory Movements
- `POST /inventory-movements/entry`: Register product entry.
- `POST /inventory-movements/exit`: Register product exit.
- `GET /inventory-movements`: View transaction history.
- `GET /inventory-movements/{id}`: View a transaction by ID.

### Reports
- `GET /reports/inventory`: Generate an inventory report.
- `GET /reports/transactions`: View transaction history report.

## AWS Services Setup

1. **RDS MySQL**: Set up your MySQL instance and store credentials securely using AWS Secrets Manager.
2. **CodePipeline**: Automate the CI/CD pipeline with AWS CodePipeline, integrating GitHub for version control, AWS CodeBuild for building the application, and AWS CodeDeploy for deployment.
3. **IAM Roles**: Ensure proper IAM roles are configured for EC2, S3, and Secrets Manager access.

## Requirements

- Java 17
- MySQL
- AWS CLI and SDK
- AWS IAM, EC2, S3, RDS, Secrets Manager

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.