## Jewellery shop
#### [Watch the project on Heroku](https://app-jewellery.herokuapp.com/do?command=passing_by_guest)
> :warning: The first time launching the application (after it falls asleep) on Heroku can take 10-30 seconds, because the initial tariff plan is used!

ROLE | LOGIN | PASSWORD 
---------| --------------|----------------
Client| ilya_user | Aa123  |
Admin| ilya_admin | Aa123 |
#
### Application description
   The application is built on the use of AJAX, which means that all HTTP requests, such as getting, 
   updating and removing data, occur completely asynchronously. Built three independent client
   applications (SPA) using Vue.js for such users as GUEST, CLIENT, ADMIN. Jsp is used as an entry point 
   into vue.js application. Built custom connection pool to get all available connections 
   from database service JawsDB Mysql. The application uses a cloud service (Cloudinary) for placing images.
    
   * **Features**
      - Deploying to Heroku
      - Uploading images to Cloudinary
      - Using database service JawsDB Mysql
      - Vue.js
      - Vuex
      - Ajax
      - Vuetify
      - Servlet
      - Custom connection pool and proxy connections
      - JSP
      - Maven
      - JSTL
      - Log4j2
      - Pagination
      - Double validation
      - XSS attacks protection
      - Sending email via SMTP
      - Localization: EN, RU
      
Command | GUEST’S SCOPE | CLIENT’S SCOPE | ADMIN’S SCOPE
---------| --------------|----------------|---------------
Passing by guest| * |   |
Passing by client|  | * |
Passing by admin|  |  | * |
Change language| * | * | * |
Load all categories| * | * | * |
Load all products by category| * | * | * |
Confirm sign up| * |   |
Sign in| * |   |
Sign up| * |   |
Sign out|   | * | * |
Check login existence| * |   |
Load shopping cart|   | * |
Add products to shopping cart|  | * |
Remove product from shopping cart|  | * |
Create order|  | * |
Load all orders|  | * | * |
Load profile image|   | * | * |
Remove profile image|  | * | * |
Update profile|  | * | * |
Upload profile image|  | * | * |
Change password by email| * |   |
Change password by old password|  | * |
Create category|  |  | * |
Create product|  |  | * |
Load all clients|  |  | * |
Remove category|  |  | * |
Update category name|  |  | * |
Update client status|  |  | * |
Update order status|  |  | * |
Update products category|  |  | * |
Update product info|  |  | * |
Update product status|  |  | * |
Upload product image|  |  | * |


