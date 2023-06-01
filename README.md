# About this project
The system should maintain user enrollment, a product and its category. Each user has a name, email, phone number, date of birth and password. The product data are: name, description, price and image. The system should display
a product catalog, which can be filtered by the product name. From this catalog, the user can select a product to see its details and choose whether to add it to the purchase cart. The user can include and remove itens from the
purchase cart, also alter the quantity of each item. Once the user has chosen to finish the order, this order should be saved in the system with the status "waiting payment". The order data are: Instant in which it was saved
and an item list, where each item refers to one product and its quantity in the order. The order status can be: waiting payment, paid, shipped, delivered or canceled. When a user pays for an order, the payment instant may be
registered. Users can be clients or administrators, while every user registered in the system is by default a client. Users who are not identified can register themselves on the system, navigate through the product catalog
and navigate to their purchase cart. Clients can update their data, register orders and view their own orders. Administrators have access to the administrative area, where it is possible to access user, product and categories enrollments.


## Back End
- Java
- Spring Boot
- Spring Data JPA
- Spring Security 
- OAuth2/JWT
- Bean validation
- H2
- Postgresql


## Database overview
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/9843ad91-b440-4e71-aba6-e0c3e2845d25)

## Use cases
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/08f6e272-765e-4a22-8081-bcbc3ff7544b)


# Layout web
### Product listing on the initial page
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/42436f28-801b-4c42-a23a-839a9a0746d4)

### Further information is available when the product is selected
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/d38c8ab7-8f94-4784-92d0-756bd7d40aff)

### Product added to the cart
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/0818cf71-50b7-4055-931e-fe5c4fdcf0d2)

### Log in form
***If the user tries to make the purchase without being logged in, the user is redirected to log in to the system.***
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/9c6570d6-0929-42d5-b50b-116ee2a74cdd)

### Ended order
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/a1a49e66-b2a1-4d5b-b601-0d3a75f51059)

# Administrative area
### Initial page
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/c78f3272-283e-4e8d-a6ca-daaeb6a20286)

### Product listing on the initial page
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/a50c77e0-b399-44dc-92e0-cb45d8686c0c)

### Registration of a new product
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/9eb1fd92-52e7-4b9a-89bf-8a5062dee789)

# Layout mobile
### Product listing on the initial page
***this page is not being completely shown***

![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/d4caa961-4bad-4822-8a62-4df0fbc34441)

### Further information is available when the product is selected
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/ef0c1b15-bead-4609-a5f4-a0f860ce4c37)

### Product added to the cart
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/48d629fb-32bf-4cfc-8833-8ad8daf0419a)

### Log in form
***If the user tries to make the purchase without being logged in, the user is redirected to log in to the system.***
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/4a70d04a-a442-46c1-bdd1-2f201488bd29)

### Ended order
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/3cf1653f-ed1d-49d5-9b20-43da88b4c09f)

# Administrative area
### Initial page
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/532ae7a1-f33f-4b53-a020-a459d837f80e)

### Product listing on the initial page
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/7b8d7ba0-bf8b-4534-917a-5c3d71de49dd)

## Registration of a new product
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/43e608fe-f14b-4eb3-bde9-88da4278686b)


# Author
Fabrício de Oliveira Pita

https://www.linkedin.com/in/profissional-fabricio-oliveira/
