# About this project
The system should maintain user enrollment, a product and its category. Each user has a name, email, phone number, date of birth and password. The product data are: name, description, price and image. The system should display
a product catalog, which can be filtered by the product name. From this catalog, the user can select a product to see its details and choose whether to add it to the purchase cart. The user can include and remove itens from the
purchase cart, also alter the quantity of each item. Once the user has chosen to finish the order, this order should be saved in the system with the status "waiting payment". The order data are: Instant in which it was saved
and an item list, where each item refers to one product and its quantity in the order. The order status can be: waiting payment, paid, shipped, delivered or canceled. When a user pays for an order, the payment instant may be
registered. Users can be clients or administrators, while every user registered in the system is by default a client. Users who are not identified can register themselves on the system, navigate through the product catalog
and navigate to their purchase cart. Clients can update their data, register orders and view their own orders. Administrators have access to the administrative area, where it is possible to access user, product and categories enrollments.
***This project is still being built***

# Back End
- Java
- Spring Boot
- Spring Data JPA
- Bean validation
- H2 database
- Spring Security 
- OAuth2/JWT

# Database overview
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/9843ad91-b440-4e71-aba6-e0c3e2845d25)

# Use cases
![image](https://github.com/Fabricio-Oliveira-dev/dscommerce/assets/105288563/08f6e272-765e-4a22-8081-bcbc3ff7544b)


# Author
Fabrício de Oliveira Pita

https://www.linkedin.com/in/profissional-fabricio-oliveira/
