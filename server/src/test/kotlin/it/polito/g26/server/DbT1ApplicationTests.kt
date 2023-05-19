package it.polito.g26.server

import it.polito.g26.server.authentication.LoginRequest
import it.polito.g26.server.products.*
import it.polito.g26.server.profiles.customer.*
import it.polito.g26.server.profiles.expert.*
import it.polito.g26.server.profiles.manager.*
import it.polito.g26.server.ticketing.tickets.*
import it.polito.g26.server.ticketing.utility.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.text.SimpleDateFormat

@Testcontainers
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class DbT1ApplicationTests {
    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:latest")
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.jpa.hibernate.ddl-auto") {"create-drop"}
        }
    }
    @LocalServerPort
    protected var port: Int = 0
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var expertRepository: ExpertRepository
    @Autowired
    lateinit var managerRepository: ManagerRepository
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var ticketRepository: TicketRepository

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1CustomerGet() {
        val customer = CustomerDTO(null, "Name","Surname", Role.CUSTOMER,"test@test.it", "city", "address")

        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)

        val url = "http://localhost:$port/API/customer/${customer.email}"
        val response = restTemplate.getForEntity(url, CustomerDTO::class.java)


        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customer.name, response.body?.name)
        assertEquals(customer.surname, response.body?.surname)
        assertEquals(customer.email, response.body?.email)
        assertEquals(customer.role, response.body?.role)
        assertEquals(customer.address, response.body?.address)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2CustomerInsert() {
        val customer = CustomerDTO(null, "test@test.it", "Name", Role.CUSTOMER,"Surname", "city", "address")

        val url = "http://localhost:$port/API/customer"
        val request = HttpEntity(customer)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val url2 = "http://localhost:$port/API/customer/${customer.email}"
        val response2 = restTemplate.getForEntity(url2, CustomerDTO::class.java)

        assertNotNull(response2.body)
        assertEquals(customer.name, response2.body?.name)
        assertEquals(customer.surname, response2.body?.surname)
        assertEquals(customer.email, response2.body?.email)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3CustomerUpdate() {
        var customer = CustomerDTO(null,"test@test.it", "Name",Role.CUSTOMER, "Surname", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)

        val urlGet = "http://localhost:$port/API/customer/${customer.email}"
        val responseGet = restTemplate.getForEntity(urlGet, CustomerDTO::class.java)

        val getCustomer = responseGet.body?.toEntity()

        getCustomer?.name = "New name"
        getCustomer?.surname = "New surname"
        getCustomer?.city = "New city"
        getCustomer?.address = "New address"

        if (getCustomer != null) {
            customer = getCustomer.toDTO()
        }

        val url = "http://localhost:$port/API/customer/${customer.email}"
        val request = HttpEntity(customer)
        val responsePut = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, customer.email)

        val responseGet2 = restTemplate.getForEntity(urlGet, CustomerDTO::class.java)

        assertEquals(HttpStatus.ACCEPTED, responsePut.statusCode)


        assertNotNull(responseGet2.body)
        /*
        assertEquals(customer.id, responseGet2.body?.id)
        assertEquals(customer.name, responseGet2.body?.name)
        assertEquals(customer.surname, responseGet2.body?.surname)
        assertEquals(customer.city, responseGet2.body?.city)
        assertEquals(customer.address, responseGet2.body?.address)
        assertEquals(customer.email, responseGet2.body?.email)
        */
        assertEquals(customer, responseGet2.body)

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1ExpertGet() {
        val expert = ExpertDTO(null, "Name","Surname", "Email","Field1, Field2")

        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)

        val url = "http://localhost:$port/API/expert/${expert.email}"
        val response = restTemplate.getForEntity(url, ExpertDTO::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expert.name, response.body?.name)
        assertEquals(expert.surname, response.body?.surname)
        assertEquals(expert.email, response.body?.email)
        assertEquals(expert.fields, response.body?.fields)

    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2ExpertGetByField() {
        val expert = ExpertDTO(null, "Name","Surname","Email","Field1, Field2")
        val field = "field1"

        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)

        val urlGet = "http://localhost:$port/API/expert/${expert.email}"
        val responseGet = restTemplate.getForEntity(urlGet, ExpertDTO::class.java)

        val url = "http://localhost:$port/API/expert/field/${field}"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<ExpertDTO>>() {}, field)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(responseGet.body, response.body?.get(0))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3ExpertInsert() {
        val expert = ExpertDTO(null, "Name","Surname","Email","Field1, Field2")

        val url = "http://localhost:$port/API/expert"
        val request = HttpEntity(expert)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val urlGet = "http://localhost:$port/API/expert/${expert.email}"
        val responseGet = restTemplate.getForEntity(urlGet, ExpertDTO::class.java)

        assertNotNull(responseGet.body)
        assertEquals(expert.name, responseGet.body?.name)
        assertEquals(expert.surname, responseGet.body?.surname)
        assertEquals(expert.fields, responseGet.body?.fields)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test4ExpertUpdate() {
        var expert = ExpertDTO(null, "Name","Surname","Email","Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)

        val url = "http://localhost:$port/API/expert/${expert.email}"
        val responseGet = restTemplate.getForEntity(url, ExpertDTO::class.java)

        val getExpert = responseGet.body?.toEntity()

        getExpert?.name = "New name"
        getExpert?.surname = "New surname"
        getExpert?.fields = "Field3"

        if (getExpert != null) {
            expert = getExpert.toDTO()
        }

        val request = HttpEntity(expert)
        val responsePut = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java)

        val responseGet2 = restTemplate.getForEntity(url, ExpertDTO::class.java)

        assertEquals(HttpStatus.ACCEPTED, responsePut.statusCode)

        assertNotNull(responseGet2.body)

        assertEquals(expert, responseGet2.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1ManagerGet() {
        val manager = ManagerDTO(null,"Name", "Surname","Email","Dep2")
        ManagerController(managerService = ManagerServiceImpl(managerRepository)).insertManager(manager)

        val url = "http://localhost:$port/API/manager/${manager.email}"
        val response = restTemplate.getForEntity(url, ManagerDTO::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(manager.email, response.body?.email)
        assertEquals(manager.name, response.body?.name)
        assertEquals(manager.surname, response.body?.surname)
        assertEquals(manager.department, response.body?.department)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2ManagerInsert() {
        val manager = ManagerDTO(null,"Name", "Surname","Email","Dep2")
        val url = "http://localhost:$port/API/manager"
        val request = HttpEntity(manager)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val urlGet = "http://localhost:$port/API/manager/${manager.email}"
        val responseGet = restTemplate.getForEntity(urlGet, ManagerDTO::class.java)

        assertNotNull(responseGet.body)

        assertEquals(manager.name, responseGet.body?.name)
        assertEquals(manager.surname, responseGet.body?.surname)
        assertEquals(manager.email, responseGet.body?.email)
        assertEquals(manager.department, responseGet.body?.department)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3ManagerUpdate() {
        var manager = ManagerDTO(null,"Name", "Surname","Email","Dep2")
        ManagerController(managerService = ManagerServiceImpl(managerRepository)).insertManager(manager)

        val url = "http://localhost:$port/API/manager/${manager.email}"
        val responseGet = restTemplate.getForEntity(url, ManagerDTO::class.java)

        val getManger = responseGet.body?.toEntity()

        getManger?.name = "New name"
        getManger?.surname = "New surname"
        getManger?.department = "New Department"

        if (getManger != null) {
            manager = getManger.toDTO()
        }

        val request = HttpEntity(manager)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, manager.id)
        val responseGet2 = restTemplate.getForEntity(url, ManagerDTO::class.java)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        assertNotNull(responseGet2.body)
        assertEquals(manager.name, responseGet2.body?.name)
        assertEquals(manager.surname, responseGet2.body?.surname)
        assertEquals(manager.email, responseGet2.body?.email)
        assertEquals(manager.department, responseGet2.body?.department)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1ProductGetAll() {
        val product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        val product2 = ProductDTO(ean = 2, name = "Name2", category = "Category2", brand = "Brand2", price = 10.5)

        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product2)


        val url = "http://localhost:$port/API/products/"
        println(url)
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<ProductDTO>>() {})
        assertEquals(HttpStatus.OK, response.statusCode)
        println(response)
        println(response.body)
        assertEquals(product, response.body?.get(0))
        assertEquals(product2, response.body?.get(1))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2ProductGet() {
        val product = ProductDTO(ean = 2, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)

        val url = "http://localhost:$port/API/products/${product.ean}"
        val response = restTemplate.getForEntity(url, ProductDTO::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(product, response.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3ProductInsert() {
        val product = ProductDTO(ean = 3, name = "Name", category = "Category", brand = "Brand", price = 10.5)

        val url = "http://localhost:$port/API/products/"
        val request = HttpEntity(product)
        val response = restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val urlGet = "http://localhost:$port/API/products/${product.ean}"
        val responseGet = restTemplate.getForEntity(urlGet, ProductDTO::class.java)

        assertNotNull(responseGet.body)
        assertEquals(product.ean, responseGet.body?.ean)
        assertEquals(product.name, responseGet.body?.name)
        assertEquals(product.category, responseGet.body?.category)
        assertEquals(product.brand, responseGet.body?.brand)
        assertEquals(product.price, responseGet.body?.price)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test4ProductUpdate() {
        var product = ProductDTO(ean = 4, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)

        val url = "http://localhost:$port/API/products/${product.ean}"
        val responseGet = restTemplate.getForEntity(url, ProductDTO::class.java)

        val getProduct = responseGet.body?.toEntity()

        getProduct?.name = "New name"
        getProduct?.category = "New category"
        getProduct?.brand = "New brand"
        getProduct?.price = 12.3
        if (getProduct != null) {
            product = getProduct.toDTO()
        }

        val request = HttpEntity(product)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request, Void::class.java, product.ean)
        val responseGet2 = restTemplate.getForEntity(url, ProductDTO::class.java)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        assertNotNull(responseGet2.body)
        assertEquals(product, responseGet2.body)
    }


    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test1TicketGetAll() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        println("!!!!!!CUSTOMER BODY RESPONSE!!!!!!")
        println(responseCustomer.body)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        println("!!!!!!EXPERT BODY RESPONSE!!!!!!")
        println(responseExpert.body)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        println("!!!!!!PRODUCT BODY RESPONSE!!!!!!")
        println(responseProduct.body)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue2",
            description = "Description2",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-06-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)
        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket2)

        val url = "http://localhost:$port/API/tickets"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<TicketDTO>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)

        assertEquals(ticket.expert, response.body?.get(0)?.expert)
        assertEquals(ticket.customer, response.body?.get(0)?.customer)
        assertEquals(ticket.product, response.body?.get(0)?.product)
        assertEquals(ticket.dateOfCreation, response.body?.get(0)?.dateOfCreation)
        assertEquals(ticket.priorityLevel, response.body?.get(0)?.priorityLevel)
        assertEquals(ticket.status, response.body?.get(0)?.status)
        assertEquals(ticket.chats, response.body?.get(0)?.chats)
        assertEquals(ticket.issueType, response.body?.get(0)?.issueType)
        assertEquals(ticket.description, response.body?.get(0)?.description)

        assertEquals(ticket2.expert, response.body?.get(1)?.expert)
        assertEquals(ticket2.customer, response.body?.get(1)?.customer)
        assertEquals(ticket2.product, response.body?.get(1)?.product)
        assertEquals(ticket2.dateOfCreation, response.body?.get(1)?.dateOfCreation)
        assertEquals(ticket2.priorityLevel, response.body?.get(1)?.priorityLevel)
        assertEquals(ticket2.status, response.body?.get(1)?.status)
        assertEquals(ticket2.chats, response.body?.get(1)?.chats)
        assertEquals(ticket2.issueType, response.body?.get(1)?.issueType)
        assertEquals(ticket2.description, response.body?.get(1)?.description)
    }
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test2TicketInsert() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

        val url = "http://localhost:$port/API/ticket"
        val request = HttpEntity(ticket)
        val response=restTemplate.postForEntity(url, request, Void::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)

        val url2 ="http://localhost:$port/API/tickets"
        val response2 = restTemplate.exchange(url2, HttpMethod.GET, null, object : ParameterizedTypeReference<List<TicketDTO>>() {})

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )
        assertNotNull(response2.body)
        assertEquals(ticket2, response2.body?.get(0))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test3TicketGetById() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

        val id=1

        val url = "http://localhost:$port/API/ticket/${id}"
        val response = restTemplate.getForEntity(url, TicketDTO::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(ticket2, response.body)
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test4TicketGetByCustomer() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)
        println(customer.id)

        val url = "http://localhost:$port/API/ticket/customer/${customer.id}"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<TicketDTO>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(ticket2, response.body?.get(0))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test5TicketGetByExpert() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

        val url = "http://localhost:$port/API/ticket/expert/${expert.id}"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<TicketDTO>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(ticket2, response.body?.get(0))
    }


    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test6TicketGetByProduct() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

        val url = "http://localhost:$port/API/ticket/product/${product.ean}"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<TicketDTO>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(ticket2, response.body?.get(0))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test7TicketGetByDate() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

        val url = "http://localhost:$port/API/ticket/date/${"2023-05-10"}"
        val response = restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<TicketDTO>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(ticket2, response.body?.get(0))
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun test8TicketSetPriority() {
        var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
        CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
        val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
        val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
        customer = responseCustomer.body!!

        var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
        ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
        val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
        val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
        expert = responseExpert.body!!

        var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
        ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
        val urlProduct = "http://localhost:$port/API/products/${product.ean}"
        val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
        product = responseProduct.body!!

        val ticket = TicketDTO(
            id = null,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 1,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        val ticket2 = TicketDTO(
            id = 1,
            status = mutableSetOf(),
            chats = mutableSetOf(),
            customer = customer,
            expert = expert,
            product = product,
            issueType = "Issue",
            description = "Description",
            priorityLevel = 2,
            dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
        )

        TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

        val url = "http://localhost:$port/API/ticket/${ticket2.id}?priorityLevel=2"
        val request = HttpEntity(ticket2)
        val response = restTemplate.exchange(url, HttpMethod.PUT, request,Void::class.java )

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)

        val urlGet = "http://localhost:$port/API/ticket/${ticket2.id}"
        val responseGet = restTemplate.getForEntity(urlGet, TicketDTO::class.java)

        assertEquals(ticket2.priorityLevel, responseGet.body?.priorityLevel)
    }

    /* @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
     @Test
     fun test9TicketSetExpert() {
         var customer = CustomerDTO(null, "Name","Surname",Role.CUSTOMER, "email", "city", "address")
         CustomerController(customerService = CustomerServiceImpl(customerRepository)).insertCustomer(customer)
         val urlCustomer = "http://localhost:$port/API/customer/${customer.email}"
         val responseCustomer = restTemplate.getForEntity(urlCustomer,CustomerDTO::class.java)
         customer = responseCustomer.body!!

         var expert = ExpertDTO(null, "Name","Surname", "Email", "Field1, Field2")
         ExpertController(expertService = ExpertServiceImpl(expertRepository)).insertExpert(expert)
         val urlExpert = "http://localhost:$port/API/expert/${expert.email}"
         val responseExpert = restTemplate.getForEntity(urlExpert,ExpertDTO::class.java)
         expert = responseExpert.body!!

         var product = ProductDTO(ean = 1, name = "Name", category = "Category", brand = "Brand", price = 10.5)
         ProductController(productService = ProductServiceImpl(productRepository)).insertDevice(product)
         val urlProduct = "http://localhost:$port/API/products/${product.ean}"
         val responseProduct = restTemplate.getForEntity(urlProduct,ProductDTO::class.java)
         product = responseProduct.body!!

         val ticket = TicketDTO(
             id = null,
             status = mutableSetOf(),
             chats = mutableSetOf(),
             customer = customer,
             expert = expert,
             product = product,
             issueType = "Issue",
             description = "Description",
             priorityLevel = 1,
             dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
         )

         val ticket2 = TicketDTO(
             id = 1,
             status = mutableSetOf(),
             chats = mutableSetOf(),
             customer = customer,
             expert = expert,
             product = product,
             issueType = "Issue",
             description = "Description",
             priorityLevel = 1,
             dateOfCreation = SimpleDateFormat("yyyy-MM-dd").parse("2023-05-10")
         )

         TicketController(ticketService = TicketServiceImpl(ticketRepository)).insertTicket(ticket)

         val url = "http://localhost:0/API/ticket/expert/{id}"
         val builder = UriComponentsBuilder.fromPath(url).queryParam("expertDTO", expert)


         val url2 = builder.buildAndExpand(1).toUriString()
             .let { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }




         val request = HttpEntity(ticket2)
         val response = restTemplate.exchange(url2, HttpMethod.PUT, request,Void::class.java )

         assertEquals(HttpStatus.ACCEPTED, response.statusCode)

         //get the updated
         val urlGet = "http://localhost:$port/API/ticket/${ticket2.id}"
         val responseGet = restTemplate.getForEntity(urlGet, TicketDTO::class.java)

         assertEquals(ticket2.expert, responseGet.body?.expert)
     }*/

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    fun testLogin() {
        val loginRequest = LoginRequest(username = "mario@polito.it", password = "password")

        val url = "http://localhost:$port/login"
        val request = HttpEntity(loginRequest)
        val response = restTemplate.postForEntity(url, request, String::class.java)

        assertEquals("ciao", response.toString())
    }
}